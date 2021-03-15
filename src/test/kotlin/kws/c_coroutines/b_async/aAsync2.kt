package kws.c_coroutines.b_async

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext
import kotlin.random.Random

fun main(args: Array<String>) {

    runBlocking {

        val d1: Deferred<Int> = slow1Times3Async(Dispatchers.Default)
        val d2: Deferred<Int> = slow2Times3Async(Dispatchers.Default)

        val ret1: Int = d1.await()
        val ret2: Int = d2.await()
        val sum = ret1 + ret2

        //update the UI in the Main thread (the UI thread)
        withContext(Dispatchers.Main) {
            println("sum = ${sum}")
        }

    }   //this block will not finish until all "child" CoroutineScopes are complete


    //both inner launches are complete
    println("runs after both asyncs are done")

}

//
//fun CoroutineScope.slow2Times3AsyncNewScope(): Deferred<Int> = async {
//    slow2Times3()
//}
//
//
//
//fun CoroutineScope.slow1Times3AsyncNewScope(): Deferred<Int> = async {
//    slow1Times3()
//}

fun CoroutineScope.slow1Times3Async(context: CoroutineContext): Deferred<Int> = async(context) {
    coroutineScope {
        val ra = async { slow1() }
        val rb = async { slow1() }
        val rc = async { slow1() }
        ra.await() + rb.await() + rc.await()
    }
}

fun CoroutineScope.slow2Times3Async(context: CoroutineContext): Deferred<Int> = async(context) {
    coroutineScope {
        val ra = async { slow2() }
        val rb = async { slow2() }
        val rc = async { slow2() }
        ra.await() + rb.await() + rc.await()
    }
}

//private suspend fun slow1Times3(): Int = coroutineScope {
//    val ra = async { slow1() }
//    val rb = async { slow1() }
//    val rc = async { slow1() }
//    ra.await() + rb.await() + rc.await()
//}


//private suspend fun slow2Times3(): Int = coroutineScope {
//    val ra = async { slow2() }
//    val rb = async { slow2() }
//    val rc = async { slow2() }
//
//    ra.await() + rb.await() + rc.await()
//}


private suspend fun slow2(): Int {
    println("Starting slow2")

    val r1 = Random.nextInt(5)
    val r2 = Random.nextInt(5)
    val ret = r1 + r2

    delay(4000)
    println("Ending slow2")

    return ret
}

private suspend fun slow1(): Int {
    println("Starting slow1")

    val r1 = Random.nextInt(5)
    val r2 = Random.nextInt(5)
    val ret = r1 + r2

    delay(5000)
    println("Ending slow1")

    return ret
}

interface MyScope : CoroutineScope {

    fun dispatcher(): CoroutineDispatcher {
        val dDefault = Dispatchers.Default  //primary goto
        val dMain = Dispatchers.Main        //for updating the ui
        val dIO = Dispatchers.IO            //io intensive work
        return dDefault
    }

    override val coroutineContext: CoroutineContext get() = dispatcher()
}

class MyUI : MyScope {


    suspend fun start(): Unit = coroutineScope {

        val d1 = async {
            33
        }
        val d2 = async {
            44
        }

        val i1 = d1.await()
        val i2 = d2.await()

        withContext(Dispatchers.Main) {
            //update UI
            println(i1)
            println(i2)
        }


    }

}

