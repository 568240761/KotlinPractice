package coroutine

import kotlinx.coroutines.*

/**
 * 协程作用域:
 *
 * 假设我们的应用程序拥有一个具有生命周期的对象，但这个对象并不是一个协程。
 * 举例来说，我们编写了一个 Android 应用程序并在 Android 的 activity 上下文中启动了一组协程来使用异步操作拉取并更新数据以及执行动画等等。
 * 所有这些协程必须在这个 activity 销毁的时候取消以避免内存泄漏。
 * 当然，我们协程上下文与调度器也可以手动操作上下文与作业，以结合activity的生命周期与它的协程。
 * [kotlinx.coroutines]提供了一个[CoroutineScope],所有的协程构建器都声明在[CoroutineScope]之上的扩展。
 *
 * 我们通过创建一个[CoroutineScope]实例来管理协程的生命周期，并使它与 activity 的生命周期相关联。
 * [CoroutineScope]可以通过 CoroutineScope() 创建或者通过 MainScope() 工厂函数。
 * 前者创建了一个通用作用域，而后者在UI 应用程序上使用[Dispatchers.Main]为默认调度器创建作用域。
 * ```
 * class Activity {
 *      private val mainScope = MainScope()
 *
 *      fun destroy() {
 *          mainScope.cancel()
 *      }
 * }
 * ```
 */
class Activity : CoroutineScope by CoroutineScope(Dispatchers.Default) {

    fun destroy() {
        cancel() // Extension on CoroutineScope
    }
    // to be continued ...

    // class Activity continues
    fun doSomething() {
        // launch ten coroutines for a demo, each working for a different time
        repeat(10) { i ->
            launch {
                log("Coroutine $i")
                delay((i + 1) * 200L) // variable delay 200ms, 400ms, ... etc
                println("Coroutine $i is done")
            }
        }
    }
} // class Activity ends

fun main() = runBlocking<Unit> {
    val activity = Activity()
    activity.doSomething() // run test function
    println("Launched coroutines")
    delay(500L) // delay for half a second
    println("Destroying activity!")
    activity.destroy() // cancels all coroutines
    delay(1000) // visually confirm that they don't work
}