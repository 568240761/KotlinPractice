package nested

open class Outer {
    private val bar: Int = 1
    class Nested {
        fun foo() = 2
    }
}