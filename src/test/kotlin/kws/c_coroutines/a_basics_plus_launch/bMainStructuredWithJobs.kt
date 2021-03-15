package kws.c_coroutines.a_basics_plus_launch

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


/*
runBlocking creates a CoroutineScope
    launch  creates a CoroutineScope
    launch  creates a CoroutineScope
 */

fun main(args: Array<String>) {

    var job1: Job? = null
    var job2: Job? = null

    runBlocking {
        val thisOuter: CoroutineScope = this

        job1 = launch {
            val thisInner: CoroutineScope = this
            println("Starting job1")
            delay(1000)
        }

        job2 = launch {
            println("Starting job2")
            delay(1000)
        }

        jobStatus("Inside Job1: ", job1)
        jobStatus("Inside Job2: ", job2)

    }   //this block will not finish until all "child" CoroutineScopes are complete

    //both inner launches are complete
    println("runs after both jobs are done")

    jobStatus("After Job1: ", job1)
    jobStatus("After Job2: ", job2)


}

fun jobStatus(label: String, job: Job?) {
    job?.let {
        println("${label}: ${job.isActive}")
    }
}
