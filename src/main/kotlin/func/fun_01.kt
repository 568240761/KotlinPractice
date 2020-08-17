package func

fun test(a: () -> String): (String) -> String {
    println(a())
    return ::str
}

fun pa(): String {
    return "pa"
}

fun str(str: String): String {
    return str
}

fun main(){
    val i = test(::pa)
    println(i("lll"))
}