package kws.c_coroutines.a_basics_plus_launch

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking



fun main(args: Array<String>) {

    runBlocking {

        val job1: Job = GlobalScope.launch {
            println("Starting job1")
            delay(1000)
        }

        val job2: Job = GlobalScope.launch {
            println("Starting job2")
            delay(1000)
        }


//        job1.join()   //block until job1 is done
//        job2.join()   //block until job1 is done

        println("runs after both jobs are done")


    }   //this block will not finish until all "child" CoroutineScopes are complete


    println("main-end")
}
