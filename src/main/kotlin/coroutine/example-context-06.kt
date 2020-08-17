package coroutine

import kotlinx.coroutines.*

/**
 * 子协程：
 *
 * 当一个协程被其它协程在[CoroutineScope]中启动的时候，
 * 它将通过[CoroutineScope.coroutineContext]来承袭上下文，并且这个新协程的[Job]将会成为父协程作业的子作业。
 * 当一个父协程被取消的时候，所有它的子协程也会被递归的取消。
 * 然而，当使用[GlobalScope]来启动一个协程时，新协程的作业没有父作业；
 * 因为它与这个启动的作用域无关且独立运作。
 */
fun main() = runBlocking<Unit> {
    println("rootJob:${coroutineContext[Job]}")
    // launch a coroutine to process some kind of incoming request
    val request = launch {
        println("root1Job:${coroutineContext[Job]}")
        // it spawns two other jobs, one with GlobalScope
        GlobalScope.launch {
            println("job1:${coroutineContext[Job]}")
            println("job1: I run in GlobalScope and execute independently!")
            delay(1000)
            println("job1: I am not affected by cancellation of the request")
        }
        // and the other inherits the parent context
        launch {
            println("job2:${coroutineContext[Job]}")
            delay(100)
            println("job2: I am a child of the request coroutine")
            delay(1000)
            println("job2: I will not execute this line if my parent request is cancelled")
        }
    }
    delay(500)
    request.cancel() // cancel processing of the request
    delay(1000) // delay a second to see what happens
    println("main: Who has survived request cancellation?")
}