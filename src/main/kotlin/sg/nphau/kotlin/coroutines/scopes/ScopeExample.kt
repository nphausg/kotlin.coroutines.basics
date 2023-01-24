/*
 * Created by nphau on 1/24/23, 11:31 PM
 * Copyright (c) 2023 . All rights reserved.
 * Last modified 1/24/23, 11:31 PM
 */

package sg.nphau.kotlin.coroutines.scopes

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

internal class ScopeExample {

    private val scope = CoroutineScope(Job())

    fun doLongRunTask() {
        scope.launch {
            // New coroutine
            // You can call suspend function
        }
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

    fun cleanUp() {
        scope.cancel()
    }


}