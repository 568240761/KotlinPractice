package coroutine

import kotlinx.coroutines.*

/**
 * CoroutineExceptionHandler
 *
 * 可以自定义将未捕获的异常打印到控制台的默认行为。
 * 根协程上的CoroutineExceptionHandler上下文元素可用作该根协程及其可能进行自定义异常处理的所有子级的通用捕获块。
 * 它类似于Thread.uncaughtExceptionHandler。
 * 您无法从CoroutineExceptionHandler中的异常中恢复。
 * 当调用处理程序时，协程已经完成，并带有相应的异常。
 * 通常，处理程序用于记录异常，显示某种错误消息，终止和/或重新启动应用程序。
 *
 * 仅在未捕获的异常（未以任何其他方式处理的异常）上调用CoroutineExceptionHandler。
 * 特别是，所有子协程（在另一个Job上下文中创建的协程）将对其异常的处理委托给其父协程，
 * 该协程也委托给父协程，依此类推，直到根为止，因此从不使用在其上下文中安装的CoroutineExceptionHandler 。
 * 除此之外，异步生成器始终捕获所有异常并将它们表示在生成的Deferred对象中，因此其CoroutineExceptionHandler也无效。
 *
 * 在监督范围内运行的协程不会将异常传播到其父级，并且会从此规则中排除。
 */
fun main() = runBlocking {
    val handler = CoroutineExceptionHandler { _, exception ->
        println("CoroutineExceptionHandler got $exception")
    }
    val job = GlobalScope.launch(handler) { // root coroutine, running in GlobalScope
        throw AssertionError()
    }
    val deferred = GlobalScope.async(handler) { // also root, but async instead of launch
        throw ArithmeticException() // 没有打印任何东西，依赖用户去调用 deferred.await()
    }
    joinAll(job, deferred)
}