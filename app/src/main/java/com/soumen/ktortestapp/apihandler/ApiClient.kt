package com.soumen.ktortestapp.apihandler

import android.util.Log
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.features.observer.*
import io.ktor.client.request.*
import io.ktor.http.*
import com.soumen.ktortestapp.BuildConfig

object ApiClient {

    private const val CONNECTTIMEOUT = 10_000
    private const val SOCKETTIMEOUT = 10_000
    private var ktorHttpClient: HttpClient? = null

    public fun getInstance(): HttpClient {
        if (ktorHttpClient == null) createKtorClient()
        return ktorHttpClient!!
    }

    private fun createKtorClient() {
        ktorHttpClient = HttpClient(Android) {
            install(JsonFeature) {
                serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
                engine {
                    connectTimeout = CONNECTTIMEOUT
                    socketTimeout = SOCKETTIMEOUT
                }
            }
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.d("Logger Ktor =>", message)
                    }
                }
                if (BuildConfig.DEBUG) level = LogLevel.ALL
            }
            install(ResponseObserver) {
                onResponse { response ->
                    Log.d("HTTP status:", "${response.status.value}")
                }
            }
            install(DefaultRequest) {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }
        }
    }
}