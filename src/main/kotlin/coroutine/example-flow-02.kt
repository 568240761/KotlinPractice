package coroutine

/**
 * 序列：
 *
 * 如果使用一些消耗 CPU 资源的阻塞代码计算数字（每次计算需要 100 毫秒），
 * 那么我们可以使用 Sequence 来表示数字。
 */

fun foo2(): Sequence<Int> = sequence { // 序列构建器
    for (i in 1..3) {
        Thread.sleep(1000) // 假装我们正在计算
        yield(i) // 产生下一个值
    }
}

fun main() {
    //在打印每个数字之前等待 1000 毫秒
    foo2().forEach { value -> println(value) }
}