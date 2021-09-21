package com.soumen.ktortestapp.apihandler

data class ApiCallback<out T>(val status: ApiStatus
                              , val data: T?
                              , val message: String?) {
    companion object {

        fun<T> onSuccess(data: T): ApiCallback<T> = ApiCallback(status = ApiStatus.SUCCESS
            , data = data, message = null)

        fun<T> onFailure(data: T?, message: String? = "Something went wrong"): ApiCallback<T> = ApiCallback(status = ApiStatus.FAILURE
            , data = data, message = message)

        fun<T> onLoading(data: T): ApiCallback<T> = ApiCallback(status = ApiStatus.LOADING
            , data = null, message = null)
    }
}