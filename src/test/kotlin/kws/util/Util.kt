package kws.util

typealias VF = () -> Unit

fun <R> profile(block: () -> R): R {
    val t1 = System.currentTimeMillis()
    val retVal: R = block()
    val t2 = System.currentTimeMillis()
    println("Delta: " + (t2 - t1))
    return retVal
}