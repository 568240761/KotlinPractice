package coroutine

import kotlinx.coroutines.*

/**
 * 父协程的职责：
 *
 * 一个父协程总是等待所有的子协程执行结束。
 * 父协程并不显式的跟踪所有子协程的启动，
 * 并且不必使用[Job.join]在最后的时候等待它们。
 */
fun main() = runBlocking<Unit> {
    // launch a coroutine to process some kind of incoming request
    val request = launch {
        repeat(3) { i -> // launch a few children jobs
            launch  {
                delay((i + 1) * 200L) // variable delay 200ms, 400ms, 600ms
                println("Coroutine $i is done")
            }
        }
        println("request: I'm done and I don't explicitly join my children that are still active")
    }
    request.join() // wait for completion of the request, including all its children
    println("Now processing of the request is complete")
}