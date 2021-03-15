package kws.c_coroutines.a_basics_plus_launch

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.random.Random


/*
runBlocking creates a CoroutineScope
    launch  creates a CoroutineScope
    launch  creates a CoroutineScope
 */



fun main(args: Array<String>) {

    runBlocking {

        launch {
            slowThing1()
            slowThing1()
        }

        launch {
            slowThing2()
            slowThing2()
        }


    }   //this block will not finish until all "child" CoroutineScopes are complete

    //both inner launches are complete
    println("runs after both jobs are done")


}

private suspend fun slowThing2() {
    println("Starting job2")
    delay(4000)
    println("Ending job2")
}

private suspend fun slowThing1() {
    println("Starting job1")
    val r = Random.nextInt(5)
    if (r == 2) {
        throw IllegalArgumentException("ddd")
    }
    delay(5000)
    println("Ending job1")
}

