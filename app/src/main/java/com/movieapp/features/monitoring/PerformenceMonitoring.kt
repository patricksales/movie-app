package com.movieapp.features.monitoring

import com.google.firebase.Firebase
import com.google.firebase.perf.performance
import kotlinx.coroutines.runBlocking

object PerformanceMonitoring {

    fun traceMovieLoading(block: suspend () -> Unit) {
        val trace = try {
            Firebase.performance.newTrace("load_movies")
        } catch (e: Throwable) {
            null
        }
        try {
            trace?.start()
        } catch (_: Throwable) {}

        try {
            runBlocking { block() }
        } finally {
            try {
                trace?.javaClass?.getMethod("stop")?.invoke(trace)
            } catch (_: Throwable) {}
        }
    }

    fun traceNetworkRequest(name: String): Any? {
        return try {
            Firebase.performance.newTrace(name).apply { start() }
        } catch (e: Throwable) {
            null
        }
    }
}