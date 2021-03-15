package kws.b_contracts

import kws.util.VF


/*
break and do-on-your-own until:  11:10 AM
do-on-your-own:
    modify daveProfile so it works with return values:
    val ret = daveProfile { slow(2) }
 */
fun slow(x: Int): Int {
    Thread.sleep(1000)
    println("Slow")
    return x + x
}

fun prof1() {
    val t1 = System.currentTimeMillis()
    val ret = slow(2)
    println("ret: $ret")
    val t2 = System.currentTimeMillis()
    println("Delta: " + (t2 - t1))
}

fun prof2() {
    val ret = daveProfile { slow(2) }
    println("ret: $ret")
}

fun main(args: Array<String>) {
    prof1()
    prof2()
}

// in util.kt:
//   typealias VF = () -> Unit
fun daveProfileOriginal(block: VF /* ()->Unit */) {
    val t1 = System.currentTimeMillis()
    block()
    val t2 = System.currentTimeMillis()
    println("Delta: " + (t2 - t1))
}

fun <R> daveProfile(block: () -> R): R {
    val t1 = System.currentTimeMillis()
    val retVal: R = block()
    val t2 = System.currentTimeMillis()
    println("Delta: " + (t2 - t1))
    return retVal
}