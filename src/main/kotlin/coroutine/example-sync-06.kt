package coroutine

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.actor
import kotlin.system.measureTimeMillis

/**
 * Actors
 *
 * 一个 actor 是由协程、 被限制并封装到该协程中的状态以及一个与其它协程通信的通道组合而成的一个实体。
 * 一个简单的 actor 可以简单的写成一个函数， 但是一个拥有复杂状态的 actor 更适合由类来表示。
 *
 * 有一个 actor 协程构建器，它可以方便地将 actor 的邮箱通道组合到其作用域中（用来接收消息）、
 * 组合发送 channel 与结果集对象，这样对 actor 的单个引用就可以作为其句柄持有。
 *
 * 使用 actor 的第一步是定义一个 actor 要处理的消息类。
 * Kotlin 的密封类很适合这种场景。
 * 我们使用 IncCounter 消息（用来递增计数器）和 GetCounter 消息（用来获取值）来定义 CounterMsg 密封类。
 * 后者需要发送回复。CompletableDeferred 通信原语表示未来可知（可传达）的单个值， 这里被用于此目的。
 *
 * actor 本身执行时所处上下文（就正确性而言）无关紧要。
 * 一个 actor 是一个协程，而一个协程是按顺序执行的，因此将状态限制到特定协程可以解决共享可变状态的问题。
 * 实际上，actor 可以修改自己的私有状态， 但只能通过消息互相影响（避免任何锁定）。
 * actor 在高负载下比锁更有效，因为在这种情况下它总是有工作要做，而且根本不需要切换到不同的上下文。
 */

suspend fun massiveRun6(action: suspend () -> Unit) {
    val n = 100  // number of coroutines to launch
    val k = 1000 // times an action is repeated by each coroutine
    val time = measureTimeMillis {
        coroutineScope { // scope for coroutines
            repeat(n) {
                launch {
                    repeat(k) { action() }
                }
            }
        }
    }
    println("Completed ${n * k} actions in $time ms")
}

// 计数器 Actor 的各种类型
sealed class CounterMsg
object IncCounter : CounterMsg() // 递增计数器的单向消息
class GetCounter(val response: CompletableDeferred<Int>) : CounterMsg() // 携带回复的请求

// 这个函数启动一个新的计数器 actor
fun CoroutineScope.counterActor() = actor<CounterMsg> {
    var counter = 0 //  actor 状态
    for (msg in channel) { // 即将到来消息的迭代器
        when (msg) {
            is IncCounter -> counter++
            is GetCounter -> msg.response.complete(counter)
        }
    }
}

fun main() = runBlocking<Unit> {
    val counter = counterActor() // 创建该 actor
    withContext(Dispatchers.Default) {
        massiveRun6 {
            counter.send(IncCounter)
        }
    }
    // 发送一条消息以用来从一个 actor 中获取计数值
    val response = CompletableDeferred<Int>()
    counter.send(GetCounter(response))
    println("Counter = ${response.await()}")
    counter.close() // 关闭该actor
}