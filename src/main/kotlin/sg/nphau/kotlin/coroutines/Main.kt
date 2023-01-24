/*
 * Created by nphau on 1/24/23, 11:19 PM
 * Copyright (c) 2023 . All rights reserved.
 * Last modified 1/24/23, 11:19 PM
 */

package sg.nphau.kotlin.coroutines

import kotlinx.coroutines.runBlocking
import sg.nphau.kotlin.coroutines.scopes.ScopeExample

fun main(args: Array<String>) = runBlocking {
    val example = ScopeExample()
    example.doMultiTaskWithSupervisorScope()
    example.doMultiTaskWithoutSupervisorScope()
}