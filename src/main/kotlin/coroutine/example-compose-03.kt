package coroutine

import kotlinx.coroutines.*
import kotlin.system.*

/**
 * 惰性启动的 async:
 *
 * 可选的，[async] 可以通过将 start 参数设置为 [CoroutineStart.LAZY] 而变为惰性的。
 * 在这个模式下，只有结果通过 await 获取的时候协程才会启动，或者在 Job 的 start 函数调用的时候。
 */
fun main() = runBlocking<Unit> {
    val time = measureTimeMillis {
        val one = async(start = CoroutineStart.LAZY) { doSomethingUsefulOne() }
        val two = async(start = CoroutineStart.LAZY) { doSomethingUsefulTwo() }
        // some computation
        one.start() // start the first one
        two.start() // start the second one

        //注意，如果我们只是在 println 中调用 await，而没有在单独的协程中调用 start，这将会导
        //致顺序行为，直到 await 启动该协程 执行并等待至它结束，这并不是惰性的预期用例。
        println("The answer is ${one.await() + two.await()}")
    }
    println("Completed in $time ms")
}