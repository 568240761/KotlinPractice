package coroutine

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.system.*

/**
 * 处理最新值：
 *
 * 当发射器和收集器都很慢的时候，合并是加快处理速度的一种方式，它通过删除发射值来实现；
 * 另一种方式是取消缓慢的收集器，并在每次发射新值的时候重新启动它。
 *
 * 有一组与 xxx操作符执行相同基本逻辑的 xxxLatest 操作符，但是在新值产生的时候取消执行其块中的代码。
 * 让我们在先前的示例中尝试更换 conflate 为 collectLatest。
 */
fun foo19(): Flow<Int> = flow {
    for (i in 1..3) {
        delay(100) // 假装我们异步等待了 100 毫秒
        emit(i) // 发射下一个值
    }
}

fun main() = runBlocking<Unit> {
    val time = measureTimeMillis {
        // 由于 collectLatest 的函数体需要花费 300 毫秒，但是新值每 100 秒发射一次，
        // 我们看到该代码块对每个值运行，但是只收集最后一个值
        foo19()
            .collectLatest { value -> // 取消并重新发射最后一个值
                println("Collecting $value")
                delay(300) // 假装我们花费 300 毫秒来处理它
                println("Done $value")
            }
    }
    println("Collected in $time ms")
}