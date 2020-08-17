package coroutine

/**
 * 异步流
 * 挂起函数可以异步的返回单个值，但是该如何异步返回多个计算好的值呢？
 * 这正是 Kotlin 流（Flow）的用武之地。
 *
 * 表示多个值：
 *
 * 在 Kotlin 中可以使用集合来表示多个值。
 */

fun foo1(): List<Int> = listOf(1, 2, 3)

fun main() {
    foo1().forEach { value -> println(value) }
}