/*
 * Created by nphau on 29/12/2021, 10:27
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 29/12/2021, 10:13
 */

package sg.nphau.kotlin.coroutines.dispatchers

import kotlinx.coroutines.*

@DelicateCoroutinesApi
fun main() = runBlocking<Unit> {
    launch { // context of the parent, main runBlocking coroutine
        println("main runBlocking      : I'm working in ${Thread.currentThread().name} thread")
    }
    launch(Dispatchers.Unconfined) { // not confined -- will work with main thread
        println("Unconfined            : I'm working in ${Thread.currentThread().name} thread")
    }
    launch(Dispatchers.Default) { // will get dispatched to DefaultDispatcher
        println("Default               : I'm working in ${Thread.currentThread().name} thread")
    }
    launch(newSingleThreadContext("MyThread")) { // will get its own new thread
        println("newSingleThreadContext: I'm working in ${Thread.currentThread().name} thread")
    }
}