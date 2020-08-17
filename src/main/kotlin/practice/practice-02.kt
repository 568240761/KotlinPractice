package practice

import kotlinx.coroutines.*
import java.lang.RuntimeException

fun main() = runBlocking<Unit> {

    launch {
        println("launch1")
        delay(1000)
        println("after-launch1")
    }

    launch {
        println("launch2")
        delay(800)
        println("after-launch2")
    }

    launch {
        println("launch3")
        delay(200)
        throw RuntimeException()
    }
}