package basetype

fun main() {
    val a = 100
    //可空类型会进行数字装箱
    val a1: Int? = a
    val a2: Int? = a
    //这返回true，是因为Integer对值在-128到127之间的数进行了缓存，所以a1与a2都指向相同的内存地址。
    println(a1 === a2)

    val b = 10000
    val b1: Int? = b
    val b2: Int? = b
    //这返回false，是因为b不是在-128到127之间的数字，所以b1与b2都指向不相同的内存地址。
    println(b1 === b2)

}