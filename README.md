# Sword

使用 kotlin compiler plugin 实现，目前仅作用于 Kotlin 函数，为 Kotlin 函数提供代理实现。

## 集成

`def version = 0.0.1`

## 使用

### Api

```kotlin
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.BINARY)
annotation class Proxy(
    /**
     * 是否启用, 默认True
     */
    val enable: Boolean = true,

    /**
     * [InvocationHandler]实现类的全限定名, 实现类必须有无参构造方法
     *
     * e.g. com.example.ProxyTestInvocationHandler
     */
    val handler: String = "",
)

interface InvocationHandler {

    fun invoke(className: String, methodName: String, args: Array<Any?>): Any?
}
```

**向 `Proxy` 注解中的 `handler` 参数注入 `InvocationHandler` 实现类的全限定名(Fully Qualified Name)，其实现类必须有无参构造方法**。

```kotlin
class Test {

    @Proxy(
        enable = BuildConfig.isDebug,
        handler = "com.guodong.android.sword.app.GetTextNoArgInvocationHandler"
    )
    fun getTextNoArg() = "guodongAndroid"

    @Proxy(
        enable = BuildConfig.isDebug,
        handler = "com.guodong.android.sword.app.GetTextArgInvocationHandler"
    )
    fun getTextArg(
        b: Byte,
        z: Boolean,
        c: Char,
        s: Short,
        i: Int,
        l: Long,
        f: Float,
        d: Double,
        str: String,
        ia: IntArray,
        sa: Array<String>,
        ls: List<String>,
        lls: List<List<String>>,
        map: Map<String, String>,
        user: User,
        callback: Callback,
    ): User {
        return User("guodongAndroid-Release", 28)
    }
}
```

```kotlin
class GetTextArgInvocationHandler : InvocationHandler {

    private val TAG = GetTextArgInvocationHandler::class.java.simpleName

    override fun invoke(className: String, methodName: String, args: Array<Any?>): Any? {
        Log.e(TAG, "invoke: className = $className, methodName = $methodName, args(${args.size}) = ${args.joinToString()}")
        return User("guodongAndroid-Debug", 18)
    }
}

class GetTextNoArgInvocationHandler : InvocationHandler {

    private val TAG = GetTextNoArgInvocationHandler::class.java.simpleName

    override fun invoke(className: String, methodName: String, args: Array<Any?>): Any? {
        Log.e(TAG, "invoke: className = $className, methodName = $methodName, args(${args.size}) = ${args.joinToString()}")
        return "guodongAndroid-Debug"
    }
}
```

