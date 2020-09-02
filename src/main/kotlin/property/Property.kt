package property

val bookName: String
    get() = "halibote"

const val x = 2

val isEmpty: Boolean
    get() = x == 0

//在转换为java文件中，会有sex属性以及两个setSex()和getSex()方法。
var sex: String = "女"

//var number: Int
//    get() = 9
//    set(value) {
//        if (value > 8) field = value
//    }
//上面这种写法会报错，需要初始化一个值；之所以报错，是因为在set()中使用幕后字段field。
//但是下面却不会报错，转换为java文件。
//可以发现下面的写法相当简写了getStringRepresentation()和setStringRepresentation(),
//并且Java文件中并没有stringRepresentation属性。
//估计是因为在set()中调用其他方法。
var stringRepresentation: String
    get() = "jack"
    set(value) {
        setDataFromString(value) // 解析字符串并赋值给其他属性
    }

fun setDataFromString(value: String) {
    println(value)
}

var testString: String
    get() = "tomcat"
    private set(value) {
        setDataFromString(value)
    }


fun main() {
    stringRepresentation = "tom"
    println(stringRepresentation)
}