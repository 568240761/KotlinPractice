package coroutine

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

/**
 * 限长操作符：
 *
 * 限长过渡操作符（例如 take）在流触及相应限制的时候会将它的执行取消。
 * 协程中的取消操作总是通过抛出异常来执行，
 * 这样所有的资源管理函数（如 try {...} finally {...} 块）会在取消的情况下正常运行。
 */
fun numbers(): Flow<Int> = flow {
    try {
        emit(1)
        emit(2)
        println("This line will not execute")
        emit(3)
    } finally {
        println("Finally in numbers")
    }
}

fun main() = runBlocking<Unit> {
    numbers()
        .take(2) // 只获取前两个
        .collect { value -> println(value) }
}