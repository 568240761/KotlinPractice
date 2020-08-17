package coroutine

import kotlinx.coroutines.*

/**
 * 但是如果提取出的函数包含一个在当前作用域中调用的协程构建器的话，该怎么办？
 * 在这种情况下，所提取函数上只有 suspend 修饰符是不够的。
 * 解决方案：
 * 1、为 CoroutineScope 写一个 launchDoWorld扩展方法，但这可能并非总是适用，因为它并没有使 API 更加清晰；
 * 2、将 CoroutineScope 作为 launchDoWorld 函数的参数传递进去，
 * 3、使用CoroutineScope(coroutineContext)，不过这种方法结构上不安全，因为你不能再控制该方法执行的作用域。只有私有 API 才能使用这个构建器。
 */
fun main(args: Array<String>) = runBlocking {
    launchDoWorld2(this)
    println("Hello,")
}

// this is your first suspending function
fun launchDoWorld2(coroutineScope: CoroutineScope) {
    coroutineScope.launch {
        println("World!")
    }
}