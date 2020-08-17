package coroutine

import kotlinx.coroutines.*

/**
 * 调试协程与线程:
 *
 * 协程可以在一个线程上挂起并在其它线程上恢复。
 * 甚至一个单线程的调度器也是难以弄清楚协程在何时何地正在做什么事情。
 * 使用通常调试应用程序的方法是让线程在每一个日志文件的日志声明中打印线程的名字。
 * 这种特性在日志框架中是普遍受支持的。
 * 但是在使用协程时，单独的线程名称不会给出很多协程上下文信息，
 * 所以 kotlinx.coroutines 包含了调试工具来让它更简单。
 * 使用 -Dkotlinx.coroutines.debug JVM 参数运行程序。
 */
fun log(msg: String) = println("[${Thread.currentThread().name}] $msg")

fun main() = runBlocking<Unit> {
    val a = async {
        log("I'm computing a piece of the answer")
        6
    }
    val b = async {
        log("I'm computing another piece of the answer")
        7
    }
    log("The answer is ${a.await() * b.await()}")
}