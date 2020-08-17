package coroutine

import kotlinx.coroutines.*

/**
 * 提取函数重构:
 *
 * 我们来将 launch { …… } 内部的代码块提取到独立的函数中。
 * 当你对这段代码执行“提取函数”重构时，你会得到一个带有 suspend 修饰符的新函数。
 * 那是你的第一个挂起函数。
 * 在协程内部可以像普通函数一样使用挂起函数，
 * 不过其额外特性是，同样可以使用其他挂起函数（如本例中的 delay ）来挂起协程的执行。
 */
fun main() = runBlocking {
    launch { doWorld() }
    println("Hello,")
}

// this is your first suspending function
suspend fun doWorld() {
    delay(1000L)
    println("World!")
}