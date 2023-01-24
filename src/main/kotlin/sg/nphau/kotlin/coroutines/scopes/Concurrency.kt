/*
 * Created by nphau on 29/12/2021, 10:27
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 29/12/2021, 10:08
 */

package sg.nphau.kotlin.coroutines.scopes

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

fun main() = runBlocking {
    launchOnly()
    launchWithJoin()
    launchWithDeferred()
    simpleOrderOfRunning()
}

private suspend fun launchOnly() = coroutineScope {
    println("--> [1] This is a example for launch only")
    val job = launch(Dispatchers.Default) {
        delay(100L)
        println("Kotlin Coroutine")
    }
    println("Hello")
}

private suspend fun launchWithJoin() = coroutineScope {
    println("--> [2] This is a example for launch with Join")
    val job = launch(Dispatchers.Default) {
        delay(100L)
        println("Kotlin Coroutine")
    }
    job.join()
    println("Hello")
}


private suspend fun launchWithDeferred() = coroutineScope {
    println("--> [3] This is a example for Deferred")
    val time = measureTimeMillis {
        val task1 = async {
            delay(150L)
            println("Task 1 was done successfully.")
            25
        }
        val task2 = async {
            delay(150L)
            println("Task 2 was done successfully.")
        }

        task1.await()
        task2.join()
    }
    println("Task 1 & Task 2 was done with $time millis.")
}


private suspend fun simpleOrderOfRunning() = coroutineScope {

    println("--> [4] This is a example of Ordered Coroutines")

    launch {
        delay(200L)
        println("Task from runBlocking")
    }

    // Creates a coroutine scope
    coroutineScope {
        launch {
            delay(500L)
            println("Task from nested launch")
        }

        delay(100L)
        // This line will be printed before the nested launch
        println("Task from coroutine scope")
    }

    // This line is not printed until the nested launch completes
    println("Coroutine scope is over")
}
