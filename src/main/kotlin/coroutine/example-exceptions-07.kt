package coroutine

import kotlinx.coroutines.*

/**
 * 监督协程中的异常
 *
 * 常规的作业和监督作业之间的另一个重要区别是异常处理。
 * 监督协程中的每一个子作业应该通过异常处理机制处理自身的异常。
 * 这种差异来自于子作业的执行失败不会传播给它的父作业的事实。
 *
 * 这意味着在 supervisorScope 内部直接启动的协程确实使用了设置在它们作用域内的 CoroutineExceptionHandler，
 * 与父协程的方式相同 （参见 CoroutineExceptionHandler 小节以获知更多细节）。
 */
fun main() = runBlocking {
    val handler = CoroutineExceptionHandler { _, exception ->
        println("CoroutineExceptionHandler got $exception")
    }
    supervisorScope {
        launch(handler) {
            println("The child throws an exception")
            throw AssertionError()
        }
        println("The scope is completing")
    }
    println("The scope is completed")
}