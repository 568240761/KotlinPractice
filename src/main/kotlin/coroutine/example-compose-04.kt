package coroutine

import kotlinx.coroutines.*
import kotlin.system.*

/**
 * async风格的函数:
 *
 * 我们可以定义异步风格的函数来异步的调用 [doSomethingUsefulOne] 和 [doSomethingUsefulTwo]
 * 并使用 async 协程建造器并带有一个显式的 [GlobalScope] 引用。
 * 我们给这样的函数的名称中加上“……Async”后缀来突出表明；
 * 事实上，它们只做异步计算并且需要使用延期的值来获得结果。
 *
 * 注意，这些“……Async”函数不是挂起函数，它们可以在任何地方使用。
 * 然而，它们总是在调用它们的代码中意味着异步（这里的意思是 并发 ）执行。
 *
 * 如果 val one = somethingUsefulOneAsync() 这一行和 one.await() 表达式这里在代码中有逻辑错误，
 * 并且程序抛出了异常以及程序在操作的过程中中止，将会发生什么？
 * 通常情况下，一个全局的异常处理者会捕获这个异常，将异常打印成日记并报告给开发者，该程序将会继续执行其它操作。
 * 这里我们的 [somethingUsefulOneAsync] 仍然在后台执行，尽管如此，启动它的那次操作也会被终止，这个程序将不会进行结构化并发。
 */
// note that we don't have `runBlocking` to the right of `main` in this example
fun main() {
    val time = measureTimeMillis {
        // we can initiate async actions outside of a coroutine
        val one = somethingUsefulOneAsync()
        val two = somethingUsefulTwoAsync()
        // but waiting for a result must involve either suspending or blocking.
        // here we use `runBlocking { ... }` to block the main thread while waiting for the result
        runBlocking {
            println("The answer is ${one.await() + two.await()}")
        }
    }
    println("Completed in $time ms")
}

//这种写法不推荐，因为它没发进行结构化并发
fun somethingUsefulOneAsync() = GlobalScope.async {
    doSomethingUsefulOne()
}

fun somethingUsefulTwoAsync() = GlobalScope.async {
    doSomethingUsefulTwo()
}