package coroutine

import kotlinx.coroutines.*

/**
 * runBlocking 方法会阻塞当前线程来等待
 */
fun main()  { // this: CoroutineScope
    println("runBlocking begin")
    runBlocking {
        delay(3000L)
        println("runBlocking completed")
    }
    println("runBlocking end")
}