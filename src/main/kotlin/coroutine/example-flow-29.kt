package coroutine

import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

/**
 * 流的取消检查
 *
 * 为了方便起见，流构建器对每个发射值执行附加的 ensureActive 检查以进行取消，
 * 这意味着从 flow{...} 发出的繁忙循环是可以取消的。
 */

fun simple29(): Flow<Int> = (1..3).asFlow().onEach { delay(100) }

fun simple29a(): Flow<Int> = (1..5).asFlow()

fun main() = runBlocking<Unit> {

    simple29().collect { value ->
        if (value == 3) cancel()
        println(value)
    }

    println("不检查取消")

    // 但是，出于性能原因，大多数其他流程操作不会自行执行其他取消检查。
    // 例如，如果您使用IntRange.asFlow扩展来编写相同的繁忙循环，并且没有在任何地方暂停，则可能有没有取消检查操作
    simple29a().collect { value ->
        if (value == 3) cancel()
        println(value)
    }

    println("检查取消-currentCoroutineContext.ensureActive()")

    // 如果您的协程繁忙，则必须显式检查取消。
    // 您可以添加 onEach {currentCoroutineContext.ensureActive()}，或者使用cancellable()。
    simple29a().onEach { coroutineContext.ensureActive() }
        .collect { value ->
            if (value == 3) cancel()
            println(value)
        }

    println("检查取消-cancellable())")

    simple29a().cancellable()
        .collect { value ->
            if (value == 3) cancel()
            println(value)
        }
}