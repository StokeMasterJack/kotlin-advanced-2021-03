package kws.b_contracts

import kotlin.contracts.contract

inline fun <reified T> isType(value: Any): Boolean {
    contract {
        returns(true) implies (value is T)
    }
    return value is T
}


fun main(args: Array<String>) {

    val x: Any = 123
    val d: Any = 123.33
    val y: Any = "3udud"

    if (isType<Int>(x)) {
        println((x as Int) + (x as Int))
        println(x + x)
    }

    if (isType<Double>(d)) {
        println(d + d)
    }


}


/**
 * @throws IllegalArgumentException is value is null
 */
@Throws(IllegalArgumentException::class)
fun daveRequireNotNull(value: Int?) {
    contract {
        returns() implies (value != null)
    }
    if (value == null) {
        throw IllegalArgumentException("value was null")
    }
}

fun sumSimple(xxx: Int?, yyy: Int?): Int {

    return if (xxx != null && yyy != null) {
        xxx + yyy
    } else {
        0
    }
}

fun sum(x: Int?, y: Int?): Int {
    x
    y
    daveRequireNotNull(x)
    daveRequireNotNull(y)
//    return (x as Int) + (y as Int)

    return x + y
}