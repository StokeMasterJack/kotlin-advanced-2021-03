package kws.c_coroutines.b_async

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

/*
3 to 3:30:  break + doOnYourOwn
 */
fun main(args: Array<String>) = runBlocking {

    val d1: Deferred<Int> = slowThingAsync(1)
    val d2: Deferred<Int> = slowThingAsync(2)
    val d3: Deferred<Int> = slowThingAsync(3)

    val i1: Int = d1.await()
    val i2: Int = d2.await()
    val i3: Int = d3.await()

    check(i1 == 10)
    check(i2 == 20)
    check(i3 == 30)

    println(i1 + i2 + i3)
}

fun CoroutineScope.slowThingAsync(i: Int): Deferred<Int> = async(Dispatchers.IO) {
    slowThing(i)
}

suspend fun slowThing(i: Int): Int {
    println("Start slowThing($i)")
    val ms = 1000
    delay(ms.toLong())
    val r = i * 10
    delay(ms.toLong())
    println("End slowThing($i)")
    return r
}