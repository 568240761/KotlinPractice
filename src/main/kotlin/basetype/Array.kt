package basetype

fun main() {
    val array = arrayOf("jack", "tom")

    //这样写，IDE会提示报错；具体原因可以参考这篇博文[https://kaixue.io/kotlin-generics/]
    //val array1: Array<Any> = array
    val array2: Array<out Any> = array
}