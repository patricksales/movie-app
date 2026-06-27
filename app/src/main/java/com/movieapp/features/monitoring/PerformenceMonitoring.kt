package com.movieapp.features.monitoring

import com.google.firebase.Firebase
import com.google.firebase.perf.metrics.Trace
import com.google.firebase.perf.performance
import kotlinx.coroutines.runBlocking

object PerformanceMonitoring {

    fun traceMovieLoading(block: suspend () -> Unit) {
        val trace = Firebase.performance.newTrace("load_movies")
        trace.start()
        try {
            runBlocking { block() }
        } finally {
            trace.stop()
        }
    }

    fun traceNetworkRequest(name: String): Trace {
        return Firebase.performance.newTrace(name).apply { start() }
    }
}