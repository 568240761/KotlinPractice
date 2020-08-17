package coroutine

import kotlinx.coroutines.*

/**
 * 监督
 *
 * 正如我们之前研究的那样，取消是在协程的整个层次结构中传播的双向关系。
 *
 * 让我们看一下需要单向取消的情况。
 * 此类需求的一个良好示例是在其作用域内定义作业的 UI 组件。
 * 如果任何一个 UI 的子作业执行失败了，它并不总是有必要取消（有效地杀死）整个 UI 组件，
 * 但是如果 UI 组件被销毁了（并且它的作业也被取消了），由于它的结果不再被需要了，它有必要使所有的子作业执行失败。
 *
 * 另一个例子是服务进程孵化了一些子作业并且需要 监督 它们的执行，追踪它们的故障并在这些子作业执行失败的时候重启。
 *
 * 监督作业
 * SupervisorJob可以用于这些目的。它类似于常规的 Job，唯一的不同是：SupervisorJob 的取消只会向下传播。
 */
fun main() = runBlocking {
    val supervisor = SupervisorJob()
    with(CoroutineScope(coroutineContext + supervisor)) {

        // 启动第一个子作业——这个示例将会忽略它的异常（不要在实践中这么做！）
        val firstChild = launch(CoroutineExceptionHandler { _, _ ->  }) {
            println("The first child is failing")
            throw AssertionError("The first child is cancelled")
        }

        // 启动第二个子作业
        val secondChild = launch {
            firstChild.join()
            // 取消了第一个子作业且没有传播给第二个子作业
            println("The first child is cancelled: ${firstChild.isCancelled}, but the second one is still active")

            try {
                delay(Long.MAX_VALUE)
            } finally {
                // 但是取消了监督的传播
                println("The second child is cancelled because the supervisor was cancelled")
            }

        }

        // 等待直到第一个子作业失败且执行完成
        firstChild.join()
        println("Cancelling the supervisor")
        supervisor.cancel()
        secondChild.join()
    }
}