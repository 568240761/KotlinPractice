package coroutine

import kotlinx.coroutines.*

/**
 * coroutineScope 只是挂起，会释放底层线程用于其他用途
 */
fun main()  { // this: CoroutineScope
    println("coroutineScope begin")
    GlobalScope.launch {
        coroutineScope() {
            delay(1000L)
            println("coroutineScope completed")
        }
    }
    println("coroutineScope end")
    Thread.sleep(2000L)
}