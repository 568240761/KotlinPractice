package scopefunction

data class Test(val name: String, val age: Int)

fun main() {
    val data = Test(name = "jack", age = 10)

    println("let:")
    val age = data.let {
        println(it.name)
        it.age
    }
    println(age)
    println()

    println("also:")
    val copy = data.also {
        println(it.name)
        it.age
    }
    println(copy)
    println()

    println("run:")
    val age1 = data.run {
        println(name)
        age
    }
    println(age1)
    println()

    println("apply:")
    val copy1 = data.apply {
        println(name)
        age
    }
    println(copy1)
    println()

    println("with:")
    val age2 = with(data) {
        println(name)
        age
    }
    println(age2)
    println()

    println("takeIf:")
    data.takeIf { it.age > 12 }?.let {
        println(it.name)
    }
    println()

    println("takeUnless:")
    data.takeUnless { it.age > 12 }?.let {
        println(it.name)
    }
    println()
}