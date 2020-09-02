package inline

inline var name: String
    get() {
        return "jack"
    }
    set(value) {
        println(value)
    }

var age: String
    get() {
        return "jack"
    }
    set(value) {
        println(value)
    }

inline fun age(msg: (age: Int) -> Unit) {
    msg(10)
}

fun agee(msg: (age: Int) -> Unit) {
    msg(20)
}

inline fun ageee(ss: (t: String) -> Unit, noinline msg: (age: Int) -> Unit) {
    ss("jjj")
    msg(30)
}

inline fun ageeee(msg: (age: Int) -> Int): Int {
    return msg(40)
}

fun ageeeee(msg: (age: Int) -> Int): Int {
    return msg(50)
}

inline fun f(crossinline body: () -> Unit) {
    val f = Runnable { body() }
    f.run()
}

fun main() {
    name = "tom"
    age = "age"

    age {
        println("age=$it")
    }

    agee {
        println("age=$it")
    }

    ageee(
        ss = {
            println(it)
        },
        msg = {
            println(it)
        }
    )

    f {
        println("asssss")
        //不允许非局部返回
//        return
    }

    ageeee {
        //允许非局部返回
        return
    }

    val temp = ageeeee {
        //不允许非局部返回
        return@ageeeee it * it
    }

    println(temp)
}