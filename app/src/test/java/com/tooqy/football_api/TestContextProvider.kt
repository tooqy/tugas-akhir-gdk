package com.dicoding.kotlinacademy

import com.tooqy.football_api.CoroutineContextProvider
import kotlinx.coroutines.experimental.Unconfined
import kotlin.coroutines.experimental.CoroutineContext

/**
 * Created by root on 3/1/18.
 */
class TestContextProvider : CoroutineContextProvider() {
    override val main: CoroutineContext = Unconfined
}