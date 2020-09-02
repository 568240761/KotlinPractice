# Kotlin笔记
以下笔记的 Kotlin 的版本为1.3.72，该笔记主要是记录自己不熟悉的部分，并不完整。如果想学习 Kotlin ，可以去浏览[官方文档](https://www.kotlincn.net/docs/reference/)。

## 基本类型
### 数字
- 整数；Byte(8)、Short(16)、Int(32)、Long(64)；所有以未超出 Int 最大值的整型值初始化的变量都会推断为 Int 类型。如果初始值超过了其最大值，那么推断为 Long 类型。 如需显式指定 Long 型值，请在该值后追加 L 后缀。
- 浮点数；Float(32)、Double(64)；对于以小数初始化的变量，编译器会推断为 Double 类型。

Kotlin 中的数字没有隐式拓宽转换。 例如，具有 Double 参数的函数只能对 Double 值调用，而不能对 Float、 Int 或者其他数字值调用。

字面常量：
- 十进制: 123
- 十六进制: 0x0F
- 二进制: 0b00001011

Kotlin不支持八进制。

整数间的除法总是返回整数，会丢弃任何小数部分。如需返回浮点类型，请将其中的一个参数显式转换为浮点类型。

当需要一个可空的引用（如 Int?）或泛型，数字会被装箱，数字装箱不一定保留同一性，保留了相等性。我对同一性的理解是指向同一块内存地址，具体可浏览包basetype下的Number.kt文件。

位运算：
对于位运算，没有特殊字符来表示，而只可用中缀方式调用具名函数，例如:
```kotlin
val x = (1 shl 2) and 0x000FF000
```
这是完整的位运算列表（只用于 Int 与 Long）：
- shl(bits) – 有符号左移
- shr(bits) – 有符号右移
- ushr(bits) – 无符号右移
- and(bits) – 位与
- or(bits) – 位或
- xor(bits) – 位异或
- inv() – 位非
### 字符
- Char；不能直接当作数字。

字符字面值用单引号括起来: '1'。 特殊字符可以用反斜杠转义。 支持这几个转义序列：\t、 \b、\n、\r、\'、\"、\\ 与 \$。 编码其他字符要用 Unicode 转义序列语法：'\uFF00'。

当需要可空引用时，字符也会被装箱。装箱操作不会保留同一性。

### 布尔
- Boolean；它有两个值：true 与 false。

若需要可空引用布尔会被装箱。

内置的布尔运算有：
- ||（短路逻辑或）
- &&（短路逻辑与）
- !（逻辑非）

### 数组
- Array；可以使用库函数 arrayOf() 来创建一个数组并传递元素值给它，这样 arrayOf(1, 2, 3) 创建了 array [1, 2, 3]。 或者，库函数 arrayOfNulls() 可以用于创建一个指定大小的、所有元素都为空的数组。

Kotlin 中数组是不型变的（invariant）。这意味着 Kotlin 不让我们把 Array<String> 赋值给 Array<Any>，以防止可能的运行时失败，但是你可以使用 Array<out Any>，具体浏览包basetype下的Array.kt文件。

原生类型数组：

Kotlin 也有无装箱开销的专门的类来表示原生类型数组: ByteArray、 ShortArray、IntArray 等等。这些类与 Array 并没有继承关系，但是它们有同样的方法属性集。它们也都有相应的工厂方法:
```kotlin
val x: IntArray = intArrayOf(1, 2, 3)
x[0] = x[1] + x[2]

// 大小为 5、值为 [0, 0, 0, 0, 0] 的整型数组
val arr = IntArray(5)

// 例如：用常量初始化数组中的值
// 大小为 5、值为 [42, 42, 42, 42, 42] 的整型数组
val arr = IntArray(5) { 42 }

// 例如：使用 lambda 表达式初始化数组中的值
// 大小为 5、值为 [0, 1, 2, 3, 4] 的整型数组（值初始化为其索引值）
var arr = IntArray(5) { it * 1 }
```
### 无符号整型
无符号类型自 Kotlin 1.3 起才可用，并且目前处于 Beta 版。

Kotlin 为无符号以下类型：
- kotlin.UByte: 无符号 8 比特整数，范围是 0 到 255
- kotlin.UShort: 无符号 16 比特整数，范围是 0 到 65535
- kotlin.UInt: 无符号 32 比特整数，范围是 0 到 2^32 - 1
- kotlin.ULong: 无符号 64 比特整数，范围是 0 到 2^64 - 1
- kotlin.UByteArray: 无符号字节数组
- kotlin.UShortArray: 无符号短整型数组
- kotlin.UIntArray: 无符号整型数组
- kotlin.ULongArray: 无符号长整型数组

为使无符号整型更易于使用，Kotlin 提供了用后缀标记整型字面值来表示指定无符号类型。后缀 u 与 U 将字面值标记为无符号。确切类型会根据预期类型确定。如果没有提供预期的类型，会根据字面值大小选择 UInt 或者 ULong。
```kotlin
val b: UByte = 1u  // UByte，已提供预期类型
val s: UShort = 1u // UShort，已提供预期类型
val l: ULong = 1u  // ULong，已提供预期类型

val a1 = 42u // UInt：未提供预期类型，常量适于 UInt
val a2 = 0xFFFF_FFFF_FFFFu // ULong：未提供预期类型，常量不适于 UInt

val a = 1UL // ULong，即使未提供预期类型并且常量适于 UInt
```
### 字符串
字符串用 String 类型表示。字符串是不可变的。 字符串的元素——字符可以使用索引运算符访问: s[i]。 

可以用 for 循环迭代字符串:
```kotlin
for (c in str) {
    println(c)
}
```
原始字符串 使用三个引号（"""）分界符括起来，内部没有转义并且可以包含换行以及任何其他字符:
```kotlin
val text = """
    for (c in "foo")
        print(c)
"""
```

## 标签
在 Kotlin 中任何表达式都可以用标签（label）来标记。 标签的格式为标识符后跟 @ 符号，例如：abc@。

可以用标签限制 break 或者continue：
```kotlin
loop@ for (i in 1..100) {
    for (j in 1..100) {
        if (j>10) break@loop
    }
}
```
标签限制的 break 跳转到刚好位于该标签指定的循环后面的执行点。 continue 继续标签指定的循环的下一次迭代。

Kotlin 有函数字面量、局部函数和对象表达式，因此 Kotlin 的函数可以被嵌套。 标签限制的 return 允许我们从外层函数返回。 最重要的一个用途就是从 lambda 表达式中返回，回想一下我们这么写的时候：
```kotlin
fun foo() {
    listOf(1, 2, 3, 4, 5).forEach {
        if (it == 3) return // 非局部直接返回到 foo() 的调用者
        print(it)
    }
    println("this point is unreachable")
}
```
如果我们需要从 lambda 表达式中返回，我们必须给它加标签并用以限制 return。
```kotlin
fun foo() {
    listOf(1, 2, 3, 4, 5).forEach lit@{
        if (it == 3) return@lit // 局部返回到该 lambda 表达式的调用者，即 forEach 循环
        print(it)
    }
    print(" done with explicit label")
}
```
现在，它只会从 lambda 表达式中返回，通常情况下使用隐式标签更方便。 该标签与接受该 lambda 的函数同名。
```kotlin
fun foo() {
    listOf(1, 2, 3, 4, 5).forEach {
        if (it == 3) return@forEach // 局部返回到该 lambda 表达式的调用者，即 forEach 循环
        print(it)
    }
    print(" done with implicit label")
}
```
或者，我们用一个匿名函数替代 lambda 表达式。 匿名函数内部的 return 语句将从该匿名函数自身返回。
```kotlin
fun foo() {
    listOf(1, 2, 3, 4, 5).forEach(fun(value: Int) {
        if (value == 3) return  // 局部返回到匿名函数的调用者，即 forEach 循环
        print(value)
    })
    print(" done with anonymous function")
}
```
## 类
在 Kotlin 中的一个类可以有一个主构造函数以及一个或多个次构造函数。主构造函数不能包含任何的代码，初始化的代码可以放到以 init 关键字作为前缀的初始化块中。如果构造函数有注解或可见性修饰符，这个 constructor 关键字是必需的，并且这些修饰符在它前面：
```kotlin
class Customer @Inject private constructor(name: String){}
```
如果类有一个主构造函数，每个次构造函数需要委托给主构造函数， 可以直接委托或者通过别的次构造函数间接委托。委托到同一个类的另一个构造函数用 this 关键字即可：
```kotlin
class Person(val name: String) {
    private var children: MutableList<Person> = mutableListOf()
    constructor(name: String, parent: Person) : this(name) {
        parent.children.add(this)
    }
}
```
请注意，初始化块中的代码实际上会成为主构造函数的一部分。委托给主构造函数会作为次构造函数的第一条语句，因此所有初始化块与属性初始化器中的代码都会在次构造函数体之前执行。即使该类没有主构造函数，这种委托仍会隐式发生，并且仍会执行初始化块：
```kotlin
class Constructors {
    init {
        println("Init block")
    }

    constructor(i: Int) {
        println("Constructor")
    }
}
```
注意：在 JVM 上，如果主构造函数的所有的参数都有默认值，编译器会生成 一个额外的无参构造函数，它将使用默认值。这使得 Kotlin 更易于使用像 Jackson 或者 JPA 这样的通过无参构造函数创建类的实例的库。

## 继承
Kotlin 类是最终（final）的：它们不能被继承。 要使一个类可继承，请用 open 关键字标记它。

### 覆盖方法
Kotlin 力求清晰显式，因此，Kotlin 对于可覆盖的成员（我们称之为开放）以及覆盖后的成员需要显式修饰符：
```kotlin
open class Shape {
    open fun draw() {  }
    fun fill() {  }
}

class Circle() : Shape() {
    override fun draw() {  }
}
```
Circle.draw() 函数上必须加上 override 修饰符。如果没写，编译器将会报错；如果函数没有标注 open 如 Shape.fill()，那么子类中不允许定义相同签名的函数。将 open 修饰符添加到 final 类（即没有 open 的类）的成员上不起作用。

标记为 override 的成员本身是开放的，也就是说，它可以在子类中覆盖。如果你想禁止再次覆盖，使用 final 关键字：
```kotlin
open class Shape {
    open fun draw() {  }
    fun fill() {  }
}

class Circle() : Shape() {
    override fun draw() {  }
}

open class Rectangle() : Shape() {
    final override fun draw() {  }
}
```
### 覆盖属性
属性覆盖与方法覆盖类似；在超类中声明然后在派生类中重新声明的属性必须以 override 开头，并且它们必须具有兼容的类型。 每个声明的属性可以由具有初始化器的属性或者具有 get 方法的属性覆盖。
```kotlin
open class Shape {
    open val vertexCount: Int = 0
}

class Rectangle : Shape() {
    override val vertexCount = 4
}
```
可以用一个 var 属性覆盖一个 val 属性，但反之则不行。也可以在主构造函数中使用 override 关键字作为属性声明的一部分。
```kotlin
interface Shape {
    val vertexCount: Int
}

class Rectangle(override val vertexCount: Int = 4) : Shape // 总是有 4 个顶点

class Polygon : Shape {
    override var vertexCount: Int = 0  // 以后可以设置为任何数
}
```
注意：在构造派生类的新实例的过程中，第一步完成其基类的初始化，因此发生在派生类的初始化逻辑运行之前。这意味着，基类构造函数执行时，派生类中声明或覆盖的属性都还没有初始化。如果在基类初始化逻辑中（直接或通过另一个覆盖的 open 成员的实现间接）使用了任何一个这种属性，那么都可能导致不正确的行为或运行时故障。设计一个基类时，应该避免在构造函数、属性初始化器以及 init 块中使用 open 成员。

在一个内部类中访问外部类的超类，可以通过由外部类名限定的 super 关键字来实现：super@Outer：
```kotlin
open class Rectangle {
    open val borderColor: String = "red"
    open fun draw() { }
}

class FilledRectangle: Rectangle() {
    override fun draw() { }
    override val borderColor: String get() = "black"
    
    inner class Filler {
        private fun fill() { }
        fun drawAndFill() {
            super@FilledRectangle.draw() // 调用 Rectangle 的 draw() 实现
            fill()
            println("Drawn a filled rectangle with color ${super@FilledRectangle.borderColor}") // 使用 Rectangle 所实现的 borderColor 的 get()
        }
    }
}
```
### 覆盖规则
如果一个类从它的直接超类继承相同成员的多个实现， 它必须覆盖这个成员并提供其自己的实现（也许用继承来的其中之一）。 为了表示采用从哪个超类型继承的实现，我们使用由尖括号中超类型名限定的 super，如 super<Base>：
```kotlin
open class Rectangle {
    open fun draw() { }
}

interface Polygon {
    fun draw() { } // 接口成员默认就是“open”的
}

class Square() : Rectangle(), Polygon {
    // 编译器要求覆盖 draw()：
    override fun draw() {
        super<Rectangle>.draw() // 调用 Rectangle.draw()
        super<Polygon>.draw() // 调用 Polygon.draw()
    }
}
```
可以同时继承 Rectangle 与 Polygon， 但是二者都有各自的 draw() 实现，所以我们必须在 Square 中覆盖 draw()， 并提供其自身的实现以消除歧义。

## 属性与字段
声明一个属性的完整语法是
```
var <propertyName>[: <PropertyType>] [= <property_initializer>]
    [<getter>]
    [<setter>]
```    
```kotlin
val isEmpty: Boolean
    get() = this.size == 0

var stringRepresentation: String
    get() = this.toString()
    set(value) {//按照惯例，setter 参数的名称是 value，但是如果你喜欢你可以选择一个不同的名称。
        setDataFromString(value) // 解析字符串并赋值给其他属性
    }

//如果需要改变一个访问器的可见性或者对其注解，但是不需要改变默认的实现，可以定义访问器而不定义其实现:
var setterVisibility: String = "abc"
    private set // 此 setter 是私有的并且有默认实现

var setterWithAnnotation: Any? = null
    @Inject set // 用 Inject 注解此 setter
```
### 幕后字段
在 Kotlin 类中不能直接声明字段。然而，当一个属性需要一个幕后字段时，Kotlin 会自动提供。这个幕后字段可以使用field标识符在访问器中引用：
```kotlin
var counter = 0 // 注意：这个初始器直接为幕后字段赋值
    set(value) {
        if (value >= 0) field = value
    }
```
field 标识符只能用在属性的访问器内。
### 幕后属性
如果你的需求不符合这套“隐式的幕后字段”方案，那么总可以使用幕后属性:
```kotlin
private var _table: Map<String, Int>? = null
val table: Map<String, Int>
    get() {
        if (_table == null) {
            _table = HashMap() // 类型参数已推断出
        }
        return _table ?: throw AssertionError("Set to null by another thread")
    }
```
对于 JVM 平台：通过默认 getter 和 setter 访问私有属性会被优化， 所以本例不会引入函数调用开销。
### 编译期常量
如果只读属性的值在编译期是已知的，那么可以使用 const 修饰符将其标记为编译期常量。 这种属性需要满足以下要求：
- 位于顶层或者是 object 声明 或 companion object 的一个成员
- 以 String 或原生类型值初始化
- 没有自定义 getter
### 延迟初始化属性与变量
用 lateinit 修饰符标记属性,延迟初始化。
```kotlin
lateinit var subject: String
```
注意：在初始化前访问一个 lateinit 属性会抛出一个特定异常，该异常明确标识该属性被访问及它没有初始化的事实。所以使用前需要先检测，使用该属性引用上的 .isInitialized。
### 委托属性
简单地理解为可以把属性值委托给某个方法返回。

语法是： val/var <属性名>: <类型> by <表达式>。在 by 后面的表达式是该委托， 因为属性对应的 get()（与 set()）会被委托给它的 getValue() 与 setValue() 方法。 属性的委托不必实现任何的接口，但是需要提供一个 getValue() 函数（与 setValue()——对于 var 属性）。 例如:
```kotlin
class Example {
    var p: String by Delegate()
}

class Delegate {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return "$thisRef, thank you for delegating '${property.name}' to me!"
    }
 
    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        println("$value has been assigned to '${property.name}' in $thisRef.")
    }
}
```
如果想要自定义委托对象，可以查看文档中的[属性委托要求](https://www.kotlincn.net/docs/reference/delegated-properties.html)

Kotlin 标准库为几种有用的委托提供了工厂方法:
- 延迟属性 Lazy
- 可观察属性 Observable
- 将属性储存在映射中
#### 延迟属性 Lazy
lazy() 是接受一个 lambda 并返回一个 Lazy <T> 实例的函数，返回的实例可以作为实现延迟属性的委托；第一次调用 get() 会执行已传递给 lazy() 的 lambda 表达式并记录结果， 后续调用 get() 只是返回记录的结果。
```kotlin
val lazyValue: String by lazy {
    println("computed!")
    "Hello"
}

fun main() {
    println(lazyValue)
    println(lazyValue)
}
```
默认情况下，对于 lazy 属性的求值是同步锁的（synchronized）：该值只在一个线程中计算，并且所有线程会看到相同的值。如果初始化委托的同步锁不是必需的，这样多个线程可以同时执行，那么将 LazyThreadSafetyMode.PUBLICATION 作为参数传递给 lazy() 函数。 而如果你确定初始化将总是发生在与属性使用位于相同的线程， 那么可以使用 LazyThreadSafetyMode.NONE 模式：它不会有任何线程安全的保证以及相关的开销。
#### 可观察属性 Observable
Delegates.observable() 接受两个参数：初始值与修改时处理程序（handler）。 每当我们给属性赋值时会调用该处理程序（在赋值后执行）。它有三个参数：被赋值的属性、旧值与新值：
```kotlin
class User {
    var name: String by Delegates.observable("<no name>") {
        prop, old, new ->
        println("$old -> $new")
    }
}

fun main() {
    val user = User()
    user.name = "first"
    user.name = "second"
}
```
如果你想截获赋值并“否决”它们，那么使用 vetoable() 取代 observable()。 在属性被赋新值生效之前会调用传递给 vetoable 的处理程序。
#### 将属性储存在映射中
在一个映射（map）里存储属性的值。
```kotlin
//构造函数接受一个映射参数
class User(val map: Map<String, Any?>) {
    val name: String by map
    val age: Int     by map
}

val user = User(mapOf(
    "name" to "John Doe",
    "age"  to 25
))

//委托属性会从这个映射中取值
println(user.name) // Prints "John Doe"
println(user.age)  // Prints 25

//适用于 var 属性，把只读的 Map 换成 MutableMap 
class MutableUser(val map: MutableMap<String, Any?>) {
    var name: String by map
    var age: Int     by map
}
```
### 局部委托属性
可以将局部变量声明为委托属性。
```kotlin
fun example(computeFoo: () -> Foo) {
    val memoizedFoo by lazy(computeFoo)

    if (someCondition && memoizedFoo.isValid()) {
        memoizedFoo.doSomething()
    }
}
```
## 接口
Kotlin 的接口可以既包含抽象方法的声明也包含实现。与抽象类不同的是，接口无法保存状态。它可以有属性但必须声明为抽象或提供访问器实现。在接口中声明的属性不能有幕后字段（backing field），因此接口中声明的访问器不能引用它们。
```kotlin
interface MyInterface {
    val prop: Int // 抽象的
    val propertyWithImplementation: String
        get() = "foo"

    fun bar()
    fun foo() {
      print(prop)
    }
}

class Child : MyInterface {
    override val prop: Int = 29
    override fun bar() {
        // 方法体
    }
}
```
一个接口可以从其他接口派生，从而既提供基类型成员的实现也声明新的函数与属性。很自然地，实现这样接口的类只需定义所缺少的实现：
```kotlin
interface Named {
    val name: String
}

interface Person : Named {
    val firstName: String
    val lastName: String
    
    override val name: String get() = "$firstName $lastName"
}

data class Employee(
    // 不必实现“name”
    override val firstName: String,
    override val lastName: String,
    val position: Position
) : Person
```
## 扩展
一个类定义有一个成员函数与一个扩展函数，而这两个函数又有相同的接收者类型、 相同的名字，并且都适用给定的参数，这种情况总是取成员函数。
```kotlin
class Example {
    fun printFunctionType() { println("Class method") }
}

fun Example.printFunctionType() { println("Extension function") }

Example().printFunctionType()

//扩展函数重载同样名字但不同签名成员函数也完全可以
fun Example.printFunctionType(i: Int) { println("Extension function") }

Example().printFunctionType(1)
```
### 可空接收者
可以为可空的接收者类型定义扩展。这样的扩展可以在对象变量上调用，即使其值为 null，并且可以在函数体内检测 this == null，这能让你在没有检测 null 的时候调用 Kotlin 中的toString()：检测发生在扩展函数的内部。
```kotlin
fun Any?.toString(): String {
    if (this == null) return "null"
    // 空检测之后，“this”会自动转换为非空类型，所以下面的 toString()
    // 解析为 Any 类的成员函数
    return toString()
}
```
### 扩展属性
由于扩展没有实际的将成员插入类中，因此对扩展属性来说幕后字段是无效的。这就是为什么扩展属性不能有初始化器。他们的行为只能由显式提供的 getters/setters 定义。
### 伴生对象的扩展
如果一个类定义有一个伴生对象 ，你也可以为伴生对象定义扩展函数与属性。就像伴生对象的常规成员一样， 可以只使用类名作为限定符来调用伴生对象的扩展成员：
```kotlin
class MyClass {
    companion object { }  // 将被称为 "Companion"
}

fun MyClass.Companion.printCompanion() { println("companion") }

fun main() {
    MyClass.printCompanion()
}
```
### 扩展声明为成员
在一个类内部可以为另一个类声明扩展。在这样的扩展内部，有多个隐式接收者 —— 其中的对象成员可以无需通过限定符访问。扩展声明所在的类的实例称为分发接收者，扩展方法调用所在的接收者类型的实例称为扩展接收者。
```kotlin
class Host(val hostname: String) {
    fun printHostname() { print(hostname) }
}

class Connection(val host: Host, val port: Int) {
     fun printPort() { print(port) }

     fun Host.printConnectionString() {
         printHostname()   // 调用 Host.printHostname()
         print(":")
         printPort()   // 调用 Connection.printPort()
     }

     fun connect() {
         /*……*/
         host.printConnectionString()   // 调用扩展函数
     }
}

fun main() {
    Connection(Host("kotl.in"), 443).connect()
    //Host("kotl.in").printConnectionString(443)  // 错误，该扩展函数在 Connection 外不可用
}
```
对于分发接收者与扩展接收者的成员名字冲突的情况，扩展接收者优先。要引用分发接收者的成员你可以使用限定的 this 语法。
```kotlin
class Connection {
    fun Host.getConnectionString() {
        toString()         // 调用 Host.toString()
        this@Connection.toString()  // 调用 Connection.toString()
    }
}
```
声明为成员的扩展可以声明为 open 并在子类中覆盖。这意味着这些函数的分发对于分发接收者类型是虚拟的，但对于扩展接收者类型是静态的。
```kotlin
open class Base { }

class Derived : Base() { }

open class BaseCaller {
    open fun Base.printFunctionInfo() {
        println("Base extension function in BaseCaller")
    }

    open fun Derived.printFunctionInfo() {
        println("Derived extension function in BaseCaller")
    }

    fun call(b: Base) {
        b.printFunctionInfo()   // 调用扩展函数
    }
}

class DerivedCaller: BaseCaller() {
    override fun Base.printFunctionInfo() {
        println("Base extension function in DerivedCaller")
    }

    override fun Derived.printFunctionInfo() {
        println("Derived extension function in DerivedCaller")
    }
}

fun main() {
    BaseCaller().call(Base())   // “Base extension function in BaseCaller”
    DerivedCaller().call(Base())  // “Base extension function in DerivedCaller”——分发接收者虚拟解析
    DerivedCaller().call(Derived())  // “Base extension function in DerivedCaller”——扩展接收者静态解析
}
```
如果对打印的结果不能理解，具体原因可以浏览包extension下的Extension.kt文件。
### 扩展的可见性:
- 在文件顶层声明的扩展可以访问同一文件中的其他 private 顶层声明；
- 如果扩展是在其接收者类型外部声明的，那么该扩展不能访问接收者的 private 成员。
## 数据类
所有数据类自动具有以下功能：
- 所有属性的 getters （对于 var 定义的还有 setters）；
- componentN() 函数 按声明顺序对应于所有属性；
- equals()；先比较是否为同一个实例；如果不同，再比较其相应的属性值是否相同。
- hashCode()；根据属性值变化而变化。
- toString()；调用该方法会得到所有属性值拼起来的字符串。
- copy()；会新创建一个数据类对象，但是其属性值是浅拷贝。

数据类必须满足以下要求：
- 主构造函数需要至少有一个参数；
- 主构造函数的所有参数需要标记为 val 或 var；
- 数据类不能是抽象、开放、密封或者内部的；
- （在1.1之前）数据类只能实现接口。

数据类需要注意的继承规则：
- 如果在数据类体中有显式实现 equals()、 hashCode() 或者 toString()，或者这些函数在父类中有 final 实现，那么不会生成这些函数，而会使用现有函数；
- 如果超类型具有 open 的 componentN() 函数并且返回兼容的类型， 那么会为数据类生成相应的函数，并覆盖超类的实现。如果超类型的这些函数由于签名不兼容或者是 final 而导致无法覆盖，那么会报错；
- 从一个已具有 copy(……) 函数且签名匹配的类型派生一个数据类在 Kotlin 1.2 中已弃用，并且在 Kotlin 1.3 中已禁用。
- 不允许为 componentN() 以及 copy() 函数提供显式实现。

### 在类体中声明的属性
注意，对于那些自动生成的函数，编译器只使用在主构造函数内部定义的属性。如需在生成的实现中排除一个属性，请将其声明在类体中：
```kotlin
data class Person(val name: String) {
    var age: Int = 0
}
```
在 toString()、 equals()、 hashCode() 以及 copy() 的实现中只会用到 name 属性，并且只有一个 component 函数 component1()。虽然两个 Person 对象可以有不同的年龄，但它们会视为相等。

相关示例代码可浏览包data下的kt文件。

## 密封类
密封类用来表示受限的类继承结构：当一个值为有限几种的类型、而不能有任何其他类型时。在某种意义上，他们是枚举类的扩展：枚举类型的值集合也是受限的，但每个枚举常量只存在一个实例，而密封类的一个子类可以有可包含状态的多个实例。

声明一个密封类，需要在类名前面添加 sealed 修饰符。虽然密封类也可以有子类，但是所有子类都必须在与密封类自身相同的文件中声明。
```kotlin
sealed class Expr
data class Const(val number: Double) : Expr()
data class Sum(val e1: Expr, val e2: Expr) : Expr()
object NotANumber : Expr()
```
密封类是抽象的，它不能直接实例化并可以有抽象（abstract）成员，不允许有非-private 构造函数（其构造函数默认为 private）。

扩展密封类子类的类（间接继承者）可以放在任何位置，而无需在同一个文件中。

## 泛型

## 嵌套类
类可以嵌套在其他类中：
```kotlin
class Outer {
    private val bar: Int = 1
    class Nested {
        fun foo() = 2
    }
}

val demo = Outer.Nested().foo() // == 2
```
可浏览包nested下的Nested.kt文件。
## 内部类
标记为 inner 的嵌套类能够访问其外部类的成员，内部类会带有一个对外部类的对象的引用。
```kotlin
class Outer {
    private val bar: Int = 1
    inner class Inner {
        fun foo() = bar
    }
}

val demo = Outer().Inner().foo() // == 1
```
可浏览包inner下的Inner.kt文件。
## 匿名内部类
```kotlin
window.addMouseListener(object : MouseAdapter() {

    override fun mouseClicked(e: MouseEvent) {}

    override fun mouseEntered(e: MouseEvent) {}
})
```
对于 JVM 平台, 如果对象是函数式 Java 接口（即具有单个抽象方法的 Java 接口）的实例， 你可以使用带接口类型前缀的lambda表达式创建它：
```kotlin
val listener = ActionListener { println("clicked") }
```
## 枚举类
枚举类的最基本的用法是实现类型安全的枚举：
```kotlin
enum class Direction {
    NORTH, SOUTH, WEST, EAST
}
```
每个枚举常量都是一个对象，枚举常量用逗号分隔。
### 初始化
因为每一个枚举都是枚举类的实例，所以他们可以是这样初始化过的：
```kotlin
enum class Color(val rgb: Int) {
        RED(0xFF0000),
        GREEN(0x00FF00),
        BLUE(0x0000FF)
}
```
### 匿名类
枚举常量还可以声明其带有相应方法以及覆盖了基类方法的匿名类。
```kotlin
enum class ProtocolState {
    WAITING {
        override fun signal() = TALKING
    },

    TALKING {
        override fun signal() = WAITING
    };

    abstract fun signal(): ProtocolState
}
```
如果枚举类定义任何成员，那么使用分号将成员定义中的枚举常量定义分隔开。
### 实现接口
一个枚举类可以实现接口（但不能从类继承），可以为所有条目提供统一的接口成员实现，也可以在相应匿名类中为每个条目提供各自的实现。只需将接口添加到枚举类声明中即可，如下所示：
```kotlin
enum class IntArithmetics : BinaryOperator<Int>, IntBinaryOperator {
    PLUS {
        override fun apply(t: Int, u: Int): Int = t + u
    },
    TIMES {
        override fun apply(t: Int, u: Int): Int = t * u
    };

    override fun applyAsInt(t: Int, u: Int) = apply(t, u)
}
```
### 枚举常量
Kotlin 中的枚举类也有合成方法允许列出定义的枚举常量以及通过名称获取枚举常量。这些方法的签名如下（假设枚举类的名称是 EnumClass）：
```kotlin
EnumClass.valueOf(value: String): EnumClass
EnumClass.values(): Array<EnumClass>
```
如果指定的名称与类中定义的任何枚举常量均不匹配，valueOf() 方法将抛出 IllegalArgumentException 异常。

自 Kotlin 1.1 起，可以使用 enumValues<T>() 与 enumValueOf<T>() 函数以泛型的方式访问枚举类中的常量 ：
```kotlin
enum class RGB { RED, GREEN, BLUE }

inline fun <reified T : Enum<T>> printAllValues() {
    print(enumValues<T>().joinToString { it.name })
}

printAllValues<RGB>() // 输出 RED, GREEN, BLUE
```

每个枚举常量都具有在枚举类声明中获取其名称与位置的属性：
```kotlin
val name: String
val ordinal: Int
```
枚举常量还实现了 Comparable 接口， 其中自然顺序是它们在枚举类中定义的顺序。

## 对象表达式
如果超类型有一个构造函数，则必须传递适当的构造函数参数给它。 多个超类型可以由跟在冒号后面的逗号分隔的列表指定：
```kotlin
open class A(x: Int) {
    public open val y: Int = x
}

interface B { }

val ab: A = object : A(1), B {
    override val y = 15
}
```
如果我们只需要“一个对象而已”，并不需要特殊超类型，那么我们可以简单地写：
```kotlin
fun foo() {
    val adHoc = object {
        var x: Int = 0
        var y: Int = 0
    }
    print(adHoc.x + adHoc.y)
}
```
请注意，匿名对象可以用作只在本地和私有作用域中声明的类型。如果你使用匿名对象作为公有函数的返回类型或者用作公有属性的类型，那么该函数或属性的实际类型会是匿名对象声明的超类型，如果你没有声明任何超类型，就会是 Any。在匿名对象中添加的成员将无法访问。
```kotlin
class C {
    // 私有函数，所以其返回类型是匿名对象类型
    private fun foo() = object {
        val x: String = "x"
    }

    // 公有函数，所以其返回类型是 Any
    fun publicFoo() = object {
        val x: String = "x"
    }

    fun bar() {
        val x1 = foo().x        // 没问题
        val x2 = publicFoo().x  // 错误：未能解析的引用“x”
    }
}
```
## 对象声明
在 object 关键字后跟一个名称，这称为对象声明，这也是 Kotlin中的单例模式。

对象声明的初始化过程是线程安全的并且在首次访问时进行。

这些对象可以有超类型：
```kotlin
object DefaultListener : MouseAdapter() {
    override fun mouseClicked(e: MouseEvent) { …… }

    override fun mouseEntered(e: MouseEvent) { …… }
}
```
注意：对象声明不能在局部作用域（即直接嵌套在函数内部），但是它们可以嵌套到其他对象声明或非内部类中。
## 伴生对象
类内部的对象声明可以用 companion 关键字标记：
```kotlin
class MyClass {
    companion object Factory {
        fun create(): MyClass = MyClass()
    }
}

//该伴生对象的成员可通过只使用类名作为限定符来调用
val instance = MyClass.create()

//可以省略伴生对象的名称，在这种情况下将使用名称 Companion
class MyClass {
    companion object { }
}

//其自身所用的类的名称（不是另一个名称的限定符）可用作对该类的伴生对象的引用
class MyClass1 {
    companion object Named { }
}

val x = MyClass1

class MyClass2 {
    companion object { }
}

val y = MyClass2
```
请注意，即使伴生对象的成员看起来像其他语言的静态成员，在运行时他们仍然是真实对象的实例成员，而且，例如还可以实现接口。

当然，在 JVM 平台，如果使用 @JvmStatic 注解，你可以将伴生对象的成员生成为真正的静态方法和字段。

## 对象表达式、对象声明和伴生对象之间的语义差异
- 对象表达式是在使用他们的地方立即执行（及初始化）的；
- 对象声明是在第一次被访问到时延迟初始化的；
- 伴生对象的初始化是在相应的类被加载（解析）时，与 Java 静态初始化器的语义相匹配。

## 类型别名
类型别名为现有类型提供替代名称。
```kotlin
typealias NodeSet = Set<Network.Node>

typealias FileTable<K> = MutableMap<K, MutableList<File>>

typealias MyHandler = (Int, String, Any) -> Unit

typealias Predicate<T> = (T) -> Boolean
```
## 功能接口
只有一种抽象方法的接口称为功能接口，或单一抽象方法（SAM）接口。功能接口可以有几个非抽象成员，但只有一个抽象成员。 要在Kotlin中声明功能接口，请使用fun修饰符。
```kotlin
fun interface IntPredicate {
   fun accept(i: Int): Boolean
}

//传统的写法
val isEven = object : IntPredicate {
   override fun accept(i: Int): Boolean {
       return i % 2 == 0
   }
}

//SAM转换
val isEven = IntPredicate { it % 2 == 0 }
```
## 功能接口与类型别名的区别
- 类型别名只是现有类型的名称，它们不会创建新类型，而功能接口却会创建新类型；
- 类型别名只能有一个成员，而功能接口可以有多个非抽象成员和一个抽象成员；
- 功能接口还可以实现和扩展其他接口。
## 内联类
内联类仅在 Kotlin 1.3 之后版本可用，目前处于 Alpha 版。
## 委托
委托模式已经证明是实现继承的一个很好的替代方式， 而 Kotlin 可以零样板代码地原生支持它。 Derived 类可以通过将其所有公有成员都委托给指定对象来实现一个接口 Base：
```kotlin
interface Base {
    fun print()
}

class BaseImpl(val x: Int) : Base {
    override fun print() { print(x) }
}

class Derived(b: Base) : Base by b

fun main() {
    val b = BaseImpl(10)
    Derived(b).print()
}
```
Derived 的超类型列表中的 by-子句表示 b 将会在 Derived 中内部存储， 并且编译器将生成转发给 b 的所有 Base 的方法。

覆盖由委托实现的接口成员，编译器会使用 override 覆盖的实现而不是委托对象中的。
```kotlin
interface Base {
    fun printMessage()
    fun printMessageLine()
}

class BaseImpl(val x: Int) : Base {
    override fun printMessage() { print(x) }
    override fun printMessageLine() { println(x) }
}

class Derived(b: Base) : Base by b {
    override fun printMessage() { print("abc") }
}

fun main() {
    val b = BaseImpl(10)
    Derived(b).printMessage()//日志打印abc
    Derived(b).printMessageLine()//日志打印10
}
```
请注意，以这种方式重写的成员不会在委托对象的成员中调用 ，委托对象的成员只能访问其自身对接口成员实现：
```kotlin
interface Base {
    val message: String
    fun print()
}

class BaseImpl(val x: Int) : Base {
    override val message = "BaseImpl: x = $x"
    override fun print() { println(message) }
}

class Derived(b: Base) : Base by b {
    // 在 b 的 `print` 实现中不会访问到这个属性
    override val message = "Message of Derived"
}

fun main() {
    val b = BaseImpl(10)
    val derived = Derived(b)
    derived.print()
    println(derived.message)
}
```
## 函数
### 可变数量的参数
函数的参数（通常是最后一个）可以用 vararg 修饰符标记：
```kotlin
fun <T> asList(vararg ts: T): List<T> {
    val result = ArrayList<T>()
    for (t in ts) // ts is an Array
        result.add(t)
    return result
}
```
当我们调用 vararg-函数时，我们可以一个接一个地传参，例如 asList(1, 2, 3)。如果我们已经有一个数组并希望将其内容传给该函数，我们使用伸展（spread）操作符（在数组前面加 *）：
```kotlin
val a = arrayOf(1, 2, 3)
val list = asList(-1, 0, *a, 4)
```
### 中缀表示法
标有 infix 关键字的函数也可以使用中缀表示法（忽略该调用的点与圆括号）调用。中缀函数必须满足以下要求：
- 它们必须是成员函数或扩展函数；
- 它们必须只有一个参数；
- 其参数不得接受可变数量的参数且不能有默认值。
```kotlin
infix fun Int.shl(x: Int): Int {}

// 用中缀表示法调用该函数
1 shl 2

// 等同于这样
1.shl(2)
```
中缀函数调用的优先级低于算术操作符、类型转换以及 rangeTo 操作符。 以下表达式是等价的：
- 1 shl 2 + 3 等价于 1 shl (2 + 3)
- 0 until n * 2 等价于 0 until (n * 2)
- xs union ys as Set<String> 等价于 xs union (ys as Set<String>)

中缀函数调用的优先级高于布尔操作符 && 与 ||、is- 与 in- 检测以及其他一些操作符。这些表达式也是等价的：
- a && b xor c 等价于 a && (b xor c)
- a xor b in c 等价于 (a xor b) in c

注意，中缀函数总是要求指定接收者与参数。当使用中缀表示法在当前接收者上调用方法时，需要显式使用 this；不能像常规方法调用那样省略。这是确保非模糊解析所必需的。
```kotlin
class MyStringCollection {
    infix fun add(s: String) {}
    
    fun build() {
        this add "abc"   // 正确
        add("abc")       // 正确
        //add "abc"        // 错误：必须指定接收者
    }
}
```
### 尾递归函数
Kotlin 支持一种称为尾递归的函数式编程风格。 这允许一些通常用循环写的算法改用递归函数来写，而无堆栈溢出的风险。 当一个函数用 tailrec 修饰符标记并满足所需的形式时，编译器会优化该递归，留下一个快速而高效的基于循环的版本：
```kotlin
val eps = 1E-10 // "good enough", could be 10^-15

tailrec fun findFixPoint(x: Double = 1.0): Double
        = if (Math.abs(x - Math.cos(x)) < eps) x else findFixPoint(Math.cos(x))

//用传统的方式编写
private fun findFixPoint(): Double {
    var x = 1.0
    while (true) {
        val y = Math.cos(x)
        if (Math.abs(x - y) < eps) return x
        x = Math.cos(x)
    }
}
```
注意，要符合 tailrec 修饰符的条件的话，函数必须将其自身调用作为它执行的最后一个操作。在递归调用后有更多代码时，不能使用尾递归，并且不能用在 try/catch/finally 块中。目前在 Kotlin for JVM 与 Kotlin/Native 中支持尾递归。
### 函数类型
Kotlin 使用类似 (Int) -> String 的一系列函数类型来处理函数的声明： val onClick: () -> Unit = ……。

这些类型具有与函数签名相对应的特殊表示法，即它们的参数和返回值：
- 所有函数类型都有一个圆括号括起来的参数类型列表以及一个返回类型：(A, B) -> C 表示接受类型分别为 A 与 B 两个参数并返回一个 C 类型值的函数类型。 参数类型列表可以为空，如 () -> A。Unit 返回类型不可省略。
- 函数类型可以有一个额外的接收者类型，它在表示法中的点之前指定： 类型 A.(B) -> C 表示可以在 A 的接收者对象上以一个 B 类型参数来调用并返回一个 C 类型值的函数。 带有接收者的函数字面值通常与这些类型一起使用。
- 挂起函数属于特殊种类的函数类型，它的表示法中有一个 suspend 修饰符 ，例如 suspend () -> Unit 或者 suspend A.(B) -> C。

如需将函数类型指定为可空，请使用圆括号：((Int, Int) -> Int)?。

函数类型可以使用圆括号进行接合：(Int) -> ((Int) -> Unit)。箭头表示法是右结合的，(Int) -> (Int) -> Unit 与前述示例等价，但不等于 ((Int) -> (Int)) -> Unit。

还可以通过使用类型别名给函数类型起一个别称：
```kotlin
typealias ClickHandler = (Button, ClickEvent) -> Unit
```
#### Lambda 表达式
lambda 表达式与匿名函数是“函数字面值”，即未声明的函数， 但立即做为表达式传递。

lambda 表达式的完整语法形式如下：
```kotlin
{ x: Int, y: Int -> x + y }
```
lambda 表达式总是括在花括号中， 完整语法形式的参数声明放在花括号内，并有可选的类型标注， 函数体跟在一个 -> 符号之后。如果推断出的该 lambda 的返回类型不是 Unit，那么该 lambda 主体中的最后一个（或可能是单个） 表达式会视为返回值。

如果函数的最后一个参数是函数，那么作为相应参数传入的 lambda 表达式可以放在圆括号之外。

#### 匿名函数
lambda 表达式语法缺少的一个东西是指定函数的返回类型的能力。在大多数情况下，这是不必要的。因为返回类型可以自动推断出来。然而，如果确实需要显式指定，可以使用这种语法。
```kotlin
fun(x: Int, y: Int): Int = x + y
```
匿名函数看起来非常像一个常规函数声明，除了其名称省略了。

#### 带有接收者的函数字面值
带有接收者的函数类型，例如 A.(B) -> C，可以用特殊形式的函数字面值实例化。

在这样的函数字面值内部，传给调用的接收者对象成为隐式的this，以便访问接收者对象的成员而无需任何额外的限定符，亦可使用 this 表达式访问接收者对象。

这种行为与扩展函数类似，扩展函数也允许在函数体内部访问接收者对象的成员。

这里有一个带有接收者的函数字面值及其类型的示例，其中在接收者对象上调用了 plus ：
```kotlin
val sum: Int.(Int) -> Int = { other -> plus(other) }
```
匿名函数语法允许你直接指定函数字面值的接收者类型。 如果你需要使用带接收者的函数类型声明一个变量，并在之后使用它，这将非常有用。
```kotlin
val sum = fun Int.(other: Int): Int = this + other
```
当接收者类型可以从上下文推断时，lambda 表达式可以用作带接收者的函数字面值。
```kotlin
class HTML {
    fun body() { }
}

fun html(init: HTML.() -> Unit): HTML {
    val html = HTML()  // 创建接收者对象
    html.init()        // 将该接收者对象传给该 lambda
    return html
}

html {       // 带接收者的 lambda 由此开始
    body()   // 调用该接收者对象的一个方法
}
```
#### 函数类型实例化
有几种方法可以获得函数类型的实例：
- 使用函数字面值的代码块，采用以下形式之一：
    -lambda 表达式: { a, b -> a + b },
    -匿名函数: fun(s: String): Int { return s.toIntOrNull() ?: 0 }
  带有接收者的函数字面值可用作带有接收者的函数类型的值。
- 使用已有声明的可调用引用：
    -顶层、局部、成员、扩展函数：::isOdd、 String::toInt，
    -顶层、成员、扩展属性：List<Int>::size，
    -构造函数：::Regex
  这包括指向特定实例成员的绑定的可调用引用：foo::toString。
- 使用实现函数类型接口的自定义类的实例：
```kotlin
class IntTransformer: (Int) -> Int {
    override operator fun invoke(x: Int): Int = TODO()
}

val intFunction: (Int) -> Int = IntTransformer()
```

带与不带接收者的函数类型非字面值可以互换，其中接收者可以替代第一个参数，反之亦然：
```kotlin
val repeatFun: String.(Int) -> String = { times -> this.repeat(times) }
val twoParameters: (String, Int) -> String = repeatFun // OK

fun runTransformation(f: (String, Int) -> String): String {
    return f("hello", 3)
}
val result = runTransformation(repeatFun) // OK
```
请注意，默认情况下推断出的是没有接收者的函数类型，即使变量是通过扩展函数引用来初始化的。 如需改变这点，请显式指定变量类型。
#### 函数类型实例调用
函数类型的值可以通过其 invoke(……) 操作符调用：f.invoke(x) 或者直接 f(x)。

如果该值具有接收者类型，那么应该将接收者对象作为第一个参数传递。 调用带有接收者的函数类型值的另一个方式是在其前面加上接收者对象， 就好比该值是一个扩展函数：1.foo(2)。
```kotlin
val stringPlus: (String, String) -> String = String::plus
val intPlus: Int.(Int) -> Int = Int::plus

println(stringPlus.invoke("<-", "->"))
println(stringPlus("Hello, ", "world!")) 

println(intPlus.invoke(1, 1))
println(intPlus(1, 2))
println(2.intPlus(3)) // 类扩展调用
```
### 高阶函数
高阶函数是将函数用作参数或返回值的函数。
```kotlin
fun <T, R> Collection<T>.fold(
    initial: R, 
    combine: (acc: R, nextElement: T) -> R
): R {
    var accumulator: R = initial
    for (element: T in this) {
        accumulator = combine(accumulator, element)
    }
    return accumulator
}
```
在上述代码中，参数 combine 具有函数类型 (R, T) -> R，因此 fold 接受一个函数作为参数， 该函数接受类型分别为 R 与 T 的两个参数并返回一个 R 类型的值。 在 for-循环内部调用该函数，然后将其返回值赋值给 accumulator。

为了调用 fold，需要传给它一个函数类型的实例作为参数。
```kotlin
val items = listOf(1, 2, 3, 4, 5)

// Lambdas 表达式是花括号括起来的代码块。
items.fold(0, { 
    // 如果一个 lambda 表达式有参数，前面是参数，后跟“->”
    acc: Int, i: Int -> 
    print("acc = $acc, i = $i, ") 
    val result = acc + i
    println("result = $result")
    // lambda 表达式中的最后一个表达式是返回值：
    result
})

// lambda 表达式的参数类型是可选的，如果能够推断出来的话：
val joinedToString = items.fold("Elements:", { acc, i -> acc + " " + i })

// 函数引用也可以用于高阶函数调用：
val product = items.fold(1, Int::times)
```
### 内联函数
使用高阶函数会带来一些运行时的效率损失：每一个函数都是一个对象，并且会捕获一个闭包。 即那些在函数体内会访问到的变量，内存分配（对于函数对象和类）和虚拟调用会引入运行时间开销。在许多情况下通过内联化 lambda 表达式可以消除这类的开销，为了让编译器这么做，我们需要使用 inline 修饰符标记函数。

如果希望只内联一部分传给内联函数的 lambda 表达式参数，那么可以用 noinline 修饰符标记不希望内联的函数参数：
```kotlin
inline fun foo(inlined: () -> Unit, noinline notInlined: () -> Unit) {}
```
可以内联的 lambda 表达式只能在内联函数内部调用或者作为可内联的参数传递，但是 noinline 的可以以任何我们喜欢的方式操作：存储在字段中、传送它等等。

#### 非局部返回
在 Kotlin 中，我们只能对具名或匿名函数使用正常的、非限定的 return 来退出。 这意味着要退出一个 lambda 表达式，我们必须使用一个标签，并且在 lambda 表达式内部禁止使用裸 return，因为 lambda 表达式不能使包含它的函数返回：
```kotlin
fun foo() {
    ordinaryFunction {
        return // 错误：不能使 `foo` 在此处返回
    }
}
```
但是如果 lambda 表达式传给的函数是内联的，该 return 也可以内联，所以它是允许的：
```kotlin
fun foo() {
    inlined {
        return // OK：该 lambda 表达式是内联的
    }
}
```
这种返回（位于 lambda 表达式中，但退出包含它的函数）称为非局部返回。 我们习惯了在循环中用这种结构，其内联函数通常包含：
```kotlin
fun hasZeros(ints: List<Int>): Boolean {
    ints.forEach {
        if (it == 0) return true // 从 hasZeros 返回
    }
    return false
}
```
注意，一些内联函数可能调用传给它们的不是直接来自函数体、而是来自另一个执行上下文的 lambda 表达式参数，例如来自局部对象或嵌套函数。在这种情况下，该 lambda 表达式中也不允许非局部返回。为了标识这种情况，该 lambda 表达式参数需要用 crossinline 修饰符标记:
```kotlin
inline fun f(crossinline body: () -> Unit) {
    val f = object: Runnable {
        override fun run() = body()
    }
}
```
#### 具体化的泛型类型
有时候我们需要访问一个作为参数传给我们的一个类型：
```kotlin
fun <T> TreeNode.findParentOfType(clazz: Class<T>): T? {
    var p = parent
    while (p != null && !clazz.isInstance(p)) {
        p = p.parent
    }
    @Suppress("UNCHECKED_CAST")
    return p as T?
}

//在这里我们向上遍历一棵树并且检测每个节点是不是特定的类型。 这都没有问题，但是调用处不是很优雅
treeNode.findParentOfType(MyTreeNode::class.java)
```
内联函数支持具体化的泛型类型，于是我们可以这样写：
```kotlin
inline fun <reified T> TreeNode.findParentOfType(): T? {
    var p = parent
    while (p != null && p !is T) {
        p = p.parent
    }
    return p as T?
}

treeNode.findParentOfType<MyTreeNode>()
```
使用 reified 修饰符来限定泛型类型，现在可以在函数内部访问它了， 几乎就像是一个普通的类一样。由于函数是内联的，不需要反射，正常的操作符如 !is 和 as 现在都能用了。
#### 内联属性（自 1.1 起）
inline 修饰符可用于没有幕后字段的属性的访问器。
```kotlin
val foo: Foo
    inline get() = Foo()

var bar: Bar
    get() = {}
    inline set(v) {}
//也可以标注整个属性，将它的两个访问器都标记为内联
inline var bar: Bar
    get() = {}
    set(v) {}
```
在调用处，内联访问器如同内联函数一样内联。
#### 公有 API 内联函数的限制
当一个内联函数是 public 或 protected 而不是 private 或 internal 声明时，就会认为它是一个模块级的公有 API。可以在其他模块中调用它，并且也可以在调用处内联这样的调用。

声明一个内联函数，但是调用它的模块在它修改后并没有重新编译，这带来了一些由模块变更时导致的二进制兼容的风险。

为了消除这种由非公有 API 变更引入的不兼容的风险，公有 API 内联函数体内不允许使用非公有声明，即，不允许使用 private 与 internal 声明。

一个 internal 声明可以由 @PublishedApi 标注，这会允许它在公有 API 内联函数中使用。当一个 internal 内联函数标记有 @PublishedApi 时，也会像公有函数一样检测其函数体。
## 协程
协程，其目的就是用来简化异步编程。
### 协程基础
相关示例代码可浏览包coroutine下的*-basic-*.kt文件。
### 取消与超时
相关示例代码可浏览包coroutine下的*-cancel-*.kt文件。
### 组合挂起函数
相关示例代码可浏览包coroutine下的*-compose-*.kt文件。
### 协程上下文与调度器
相关示例代码可浏览包coroutine下的*-context-*.kt文件。
### 异步流
相关示例代码可浏览包coroutine下的*-flow-*.kt文件。
### 通道
相关示例代码可浏览包coroutine下的*-channel-*.kt文件。
### 异常处理
相关示例代码可浏览包coroutine下的*-exceptions-*.kt文件。
### 共享的可变状态与并发
相关示例代码可浏览包coroutine下的*-sync-*.kt文件。

## 解构声明
把一个对象解构成很多变量。例如：
```kotlin
data class User(val name: String = "", val age: Int = 0)
val jane = User("Jane", 35)
val (name, age) = jane
println("$name, $age years of age") // 输出 "Jane, 35 years of age"
```
一个解构声明会被编译成以下代码：
```kotlin
val name = jane.component1()
val age = jane.component2()
```
解构声明也可以用在 for-循环中：
```kotlin
for ((a, b) in collection) { 

}
```
变量 a 和 b 的值取自对集合中的元素上调用 component1() 和 component2() 的返回值。

遍历一个映射（map）:
```kotlin
for ((key, value) in map) {
   // 使用该 key、value 做些事情
}
```
在解构声明中你不需要某个变量，那么可以用下划线取代其名称：
```kotlin
val (_, status) = getResult()
//以这种方式跳过的组件，不会调用相应的 componentN() 操作符函数。
```
可以对 lambda 表达式参数使用解构声明语法。如果 lambda 表达式具有 Pair 类型（或者 Map.Entry 或任何其他具有相应 componentN 函数的类型）的参数，那么可以通过将它们放在括号中来引入多个新参数来取代单个新参数：
```kotlin
map.mapValues { entry -> "${entry.value}!" }
map.mapValues { (key, value) -> "$value!" }
```
可以指定整个解构的参数的类型或者分别指定特定组件的类型：
```kotlin
map.mapValues { (_, value): Map.Entry<Int, String> -> "$value!" }
map.mapValues { (_, value: String) -> "$value!" }
```
## 作用域函数
其目的是在对象的上下文中执行代码块。

当对一个对象调用这样的函数并提供一个 lambda 表达式时，它会形成一个临时作用域；在此作用域中，可以访问该对象而无需其名称。这些函数称为作用域函数。

共有以下五种：
- let
- run
- with
- apply
- also

上面五个函数的主要区别：
- 引用上下文对象的方式不同。run、with 以及 apply 通过关键字 this 引用上下文对象；let 及 also 将上下文对象作为 lambda 表达式参数，如果没有指定参数名，对象可以用隐式默认名称 it 访问。
- 返回值不同。apply 及 also 返回上下文对象；let、run 及 with 返回 lambda 表达式结果。

除了作用域函数外，标准库还有两个好用的函数 takeIf 及 takeUnless，它们与作用域函数一起特别有用。

- takeIf：若为true，则 takeIf 返回此对象。否则返回 null。
- takeUnless： 若为true，则 takeUnless 返回null。否则返回 此对象。

当在 takeIf 及 takeUnless 之后链式调用其他函数，不要忘记执行空检查或安全调用（?.），因为他们的返回值是可为空的。

相关示例代码可浏览包scopefunction下的kt文件。