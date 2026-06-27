//package com.movieapp.monitoring
//
//import com.google.firebase.Firebase
//import com.google.firebase.perf.metrics.Trace
//import com.google.firebase.perf.performance
//import kotlinx.coroutines.runBlocking
//
//object PerformanceMonitoring {
//
//    // Monitorar carregamento de filmes
//    fun traceMovieLoading(block: suspend () -> Unit) {
//        val trace = Firebase.performance.newTrace("load_movies")
//        trace.start()
//        try {
//            runBlocking {
//                block()
//            }
//        } finally {
//            trace.stop()
//        }
//    }
//
//    // Monitorar requisições HTTP
//    fun traceNetworkRequest(name: String): Trace {
//        return Firebase.performance.newTrace(name).apply {
//            start()
//        }
//    }
//}