package data

/**
 * Created by Lan Yang on 2020/8/24
 */
open class Data

data class TestData1(val age: Int) : Data()

data class TestData(
    val name: String,
    val age: TestData1
)

fun main() {
    val testData1 = TestData(name = "你好", age = TestData1(12))
    println(testData1)
    println(testData1.hashCode())
    println()

    val testData2 = TestData(name = "好", age = TestData1(12))
    println(testData2)
    println(testData2.hashCode())
    println()

    val copy = testData1.copy()
    println(copy == testData1)
    println(copy === testData1)

    println(copy.name == testData1.name)
    println(copy.name === testData1.name)

    println(copy.age == testData1.age)
    println(copy.age === testData1.age)

    /*
    我的总结：
    == 表示内容比较；
    === 表示内存地址比较；
    hashCode() 会随着属性值变化而变化；
    copy() 对实例的属性是浅复制。
    */
}