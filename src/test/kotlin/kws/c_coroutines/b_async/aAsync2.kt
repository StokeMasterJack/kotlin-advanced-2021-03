package kws.c_coroutines.b_async

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.random.Random

fun main(args: Array<String>) {

    runBlocking {

        val d1: Deferred<Int> = async {
            slow1()

        }
        
        val d2: Deferred<Int> = async {
            slow2()
        }

        val ret1: Int = d1.await()
        val ret2: Int = d2.await()
        val sum = ret1 + ret2

        println("ret1 = ${ret1}")
        println("ret2 = ${ret2}")
        println("sum = ${sum}")


    }   //this block will not finish until all "child" CoroutineScopes are complete


    //both inner launches are complete
    println("runs after both asyncs are done")

}

private suspend fun slow2(): Int {
    println("Starting async2")

    val r1 = Random.nextInt(5)
    val r2 = Random.nextInt(5)
    val ret = r1 + r2

    delay(4000)
    println("Ending async2")

    return ret
}

private suspend fun slow1(): Int {
    println("Starting async1")

    val r1 = Random.nextInt(5)
    val r2 = Random.nextInt(5)
    val ret = r1 + r2

    delay(5000)
    println("Ending async1")

    return ret
}

