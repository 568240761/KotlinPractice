package coroutine

import kotlinx.coroutines.*

/**
 * 在不同线程间跳转：
 *
 * 使用 -Dkotlinx.coroutines.debug JVM 参数运行下面的代码。
 * 其中一个使用 runBlocking 来显式指定了一个上下文，
 * 并且另一个使用withContext 函数来改变协程的上下文，而仍然驻留在相同的协程中。
 */
fun main() {
    newSingleThreadContext("Ctx1").use { ctx1 ->
        newSingleThreadContext("Ctx2").use { ctx2 ->
            runBlocking(ctx1) {
                log("Started in ctx1")
                withContext(ctx2) {
                    log("Working in ctx2")
                }
                log("Back to ctx1")
            }
        }
    }
}