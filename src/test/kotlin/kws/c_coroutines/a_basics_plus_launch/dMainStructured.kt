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
            println("Starting job1")
            val r = Random.nextInt(5)
            if (r == 2) {
                throw IllegalArgumentException("ddd")
            }
            delay(5000)
            println("Ending job1")
        }

        launch {
            println("Starting job2")
            delay(4000)
            println("Ending job2")
        }


    }   //this block will not finish until all "child" CoroutineScopes are complete

    //both inner launches are complete
    println("runs after both jobs are done")



}

