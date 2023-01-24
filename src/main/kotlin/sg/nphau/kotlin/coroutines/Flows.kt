/*
 * Created by nphau on 29/12/2021, 10:49
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 29/12/2021, 10:49
 */

package sg.nphau.kotlin.coroutines

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.runBlocking

fun main() {
    testSimpleFlowOperators()
}


fun simpleFlow(): Flow<Int> = flow {
    for (i in 0 until 3) {
        emit(i)
        delay(100)
        emit(i)
    }
}.flowOn(Dispatchers.IO)

fun simpleFlowWithException(): Flow<Int> = flow {
    for (i in 0 until 3) {
        emit(i)
        delay(100)
        emit(i)
        if (i == 1) throw Error()
    }
}.flowOn(Dispatchers.IO)

// This will output 'started', 0 0 1 1 'finished' 'exception' if catch is not used -> process will crash and exception will be passed upstream
fun testSimpleFlowOperators() = runBlocking {
    simpleFlowWithException()
        .onStart {
            println("BasicFlow has started its work")
        }
        .onEach {
            println("BasicFlow emitted value: $it")
        }
        .onCompletion {
            println("BasicFlow has finished its work")
        }
        .catch {
            println("BasicFlow has an exception $it")
        }
        .launchIn(this)
}
