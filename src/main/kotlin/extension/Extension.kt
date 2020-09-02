package extension

open class Base {}

class Derived : Base() {}

open class BaseCaller {
    open fun Base.printFunctionInfo() {
        println("Base extension function in BaseCaller")
    }

    open fun Derived.printFunctionInfo() {
        println("Derived extension function in BaseCaller")
    }

    fun call(b: Base) {
        b.printFunctionInfo() // 调用扩展函数
    }
}

class DerivedCaller : BaseCaller() {
    override fun Base.printFunctionInfo() {
        println("Base extension function in DerivedCaller")
    }

    override fun Derived.printFunctionInfo() {
        println("Derived extension function in DerivedCaller")
    }
}

fun main() {
    BaseCaller().call(Base())   // “Base extension function in BaseCaller”
    BaseCaller().call(Derived()) // “Base extension function in BaseCaller”
    DerivedCaller().call(Base())  // “Base extension function in DerivedCaller”——分发接收者虚拟解析
    DerivedCaller().call(Derived())  // “Base extension function in DerivedCaller”——扩展接收者静态解析
    //在这里程序并没有打印"Derived extension function in BaseCaller"，是因为扩展接收者类型是静态。
    //这怎么解释呢？
    //在类BaseCaller中call()的参数类型为Base，所以即使传入Derived类型的对象，也只会调用Base.printFunctionInfo()。
    //这就是静态，只根据声明的类型决定，并不根据实际类型决定。
}