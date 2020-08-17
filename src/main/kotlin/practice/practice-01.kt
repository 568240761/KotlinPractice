package practice

import kotlinx.coroutines.*

fun main() = runBlocking{

    println("main:${Thread.currentThread().id}")

    val job = GlobalScope.launch {
        println("thread-1:${Thread.currentThread().id}")
        coroutineScope {
            println("thread-1#1:${Thread.currentThread().id}")
        }
        GlobalScope.launch {
            println("thread-1#2:${Thread.currentThread().id}")
        }
    }

    GlobalScope.launch {
        println("thread-2:${Thread.currentThread().id}")
    }

    GlobalScope.launch {
        println("thread-3:${Thread.currentThread().id}")
    }

    println("quick")
    job.join()
    println("finish")
}