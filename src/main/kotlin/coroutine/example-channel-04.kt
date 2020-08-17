package coroutine

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*

/**
 * 管道
 *
 * 管道是一种在一个协程的流中开始生产可能无穷多个元素的模式，
 * 并且另一个或多个协程开始消费这些流，做一些操作，并生产了一些额外的结果。
 */
fun main() = runBlocking {
    val numbers = produceNumbers() // produces integers from 1 and on
    val squares = square(numbers) // squares integers
    for (i in 1..5) println(squares.receive()) // print first five
    println("Done!") // we are done
    coroutineContext.cancelChildren() // cancel children coroutines
}

fun CoroutineScope.produceNumbers() = produce {
    var x = 1
    while (true) send(x++) // infinite stream of integers starting from 1
}

fun CoroutineScope.square(numbers: ReceiveChannel<Int>): ReceiveChannel<Int> = produce {
    for (x in numbers) send(x * x)
}