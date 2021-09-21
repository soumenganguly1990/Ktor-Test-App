package com.soumen.ktortestapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import com.soumen.ktortestapp.apihandler.ApiCallback
import com.soumen.ktortestapp.apihandler.ApiRepository
import com.soumen.ktortestapp.entities.UserResponse

class UserViewModel : ViewModel() {

    private val apiRepository: ApiRepository by lazy { ApiRepository() }

    fun getAllUsers(queryParamName: String, pageNumber: Int) = channelFlow {
        send(ApiCallback.onLoading(data = null))
        viewModelScope.launch {
            apiRepository.getRequest<UserResponse>(queryParamName, pageNumber)?.apply {
                send(ApiCallback.onSuccess(data = this?.second))
                close()
            }
        }
        awaitClose()
    }
        .flowOn(Dispatchers.IO)
        .catch { e ->
            emit(ApiCallback.onFailure(data = null, message = e.localizedMessage))
        }
}