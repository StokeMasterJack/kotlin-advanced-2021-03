package kws.c_coroutines.b_async

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main(args: Array<String>) = runBlocking {

    val i1: Int = slowThingAsync(1).await()
    val i2: Int = slowThingAsync(2).await()
    val i3: Int = slowThingAsync(3).await()

    check(i1 == 10)
    check(i2 == 20)
    check(i3 == 30)

    println(i1 + i2 + i3)
}


suspend fun slowThing(i: Int): Int {
    delay(1000)
    val r = i * 10
    delay(1000)
    return r
}