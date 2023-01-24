/*
 * Created by nphau on 29/12/2021, 10:27
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 29/12/2021, 10:26
 */

package sg.nphau.kotlin.coroutines.scopes

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope

fun main() {
    exampleScopeCancellation()
    // exampleSupervisorScope()
    // exampleScopeCancellation()
}

private suspend fun sampleAsyncTask(iteration: Int): Int {
    delay(3000L / iteration)
    println("[Coroutines] Executed iteration: $iteration")
    if (iteration % 2 == 0) {
        throw IllegalStateException()
    }
    return iteration
}

// All tasks after first exception will be cancelled
private fun exampleDefaultScope() = runBlocking {
    coroutineScope {
        launch { sampleAsyncTask(1) }
        launch { sampleAsyncTask(2) }
        launch { sampleAsyncTask(3) }
    }
}

// All tasks will be executed despite the exceptions
private fun exampleSupervisorScope() = runBlocking {
    supervisorScope {
        coroutineScope {
            launch { sampleAsyncTask(1) }
            launch { sampleAsyncTask(2) }
            launch { sampleAsyncTask(3) }
        }
    }
}

// Scope will be cancelled after method with iteration 3 will be executed. Method with iteration 1 won't be executed
private fun exampleScopeCancellation() = runBlocking {
    val scope = CoroutineScope(Dispatchers.IO)
    scope.launch {
        sampleAsyncTask(1)
    }
    scope.launch {
        sampleAsyncTask(3)
        scope.cancel()
    }
    scope.launch { sampleAsyncTask(5) }
    delay(1500)
}


suspend fun doMultiTaskWithoutSupervisorScope() {
    // Run in parallel
    // If task 1 failed, then cancel others
    coroutineScope {
        launch {
            // Task1
            println("Task 1")
            throw RuntimeException()
        }
        launch {
            // Task2
            println("Task 2")
        }
    }
}

suspend fun doMultiTaskWithSupervisorScope() {
    // Run in parallel
    // If task 1 failed, then continue with others
    supervisorScope {
        launch {
            // Task1
            println("Task 1")
            throw RuntimeException()
        }
        launch {
            // Task2
            println("Task 2")
        }
    }
}