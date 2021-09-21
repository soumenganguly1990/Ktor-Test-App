package com.soumen.ktortestapp.apihandler

import io.ktor.client.request.*
import io.ktor.http.*
import kotlin.Exception

class ApiRepository {

    suspend inline fun <reified T : Any> getRequest(
        queryParamName: String,
        queryParamValue: Int = 1
    ): Pair<Exception?, T?> {
        try {
            return Pair(null, ApiClient.getInstance()?.request<T> {
                method = HttpMethod.Get
                queryParamName?.let { parameter(queryParamName, queryParamValue) }
                url(ApiEndpoints.BASE_URL + ApiEndpoints.USERS)
            })
        } catch (e: Exception) { return Pair(e, null) }
    }

    suspend fun postRequest(body: Any? = null): Any? =
        ApiClient.getInstance()?.request {
            method = HttpMethod.Post
            url("")
        }
}