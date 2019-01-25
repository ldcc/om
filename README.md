# om

A lightweight SQL library written over JDBC driver for JVM language. 

```kotlin
private val USER_BASE = ".e_user"
private val uDml = DML(User::class.java, USER_BASE)

/**
 * find a single by an very simple way
 */
private fun getSingle() {
	val user = uDml.selectSingle {
		id = 4
	}
	println(user)
}

/**
 * update via the field as you defined
 */
private fun update() {
	uDml.update {
		id = 6
		uProvince = "奥地利"
		uPassword = "Aa1111"
	}
}
```

More sample step to https://github.com/ldcc/om/blob/master/sample/src/Main.kt/.
