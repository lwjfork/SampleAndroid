package com.lwjfork.android.repository

import com.lwjfork.android.model.FailResponse
import com.lwjfork.android.model.LoadingResponse
import com.lwjfork.android.model.Response
import com.lwjfork.android.model.SuccessResponse
import com.lwjfork.android.repository.callback.DataResourceFlowObserver
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect

@Suppress("UNCHECKED_CAST")
suspend fun <T> Flow<Response<T>>.collectResult(callBack: DataResourceFlowObserver<T>) {
    this.collect {
        when (it) {
            is LoadingResponse<T> -> callBack.onStart()
            is FailResponse<T> -> callBack.onFail(it as FailResponse<T?>)
            is SuccessResponse<T> -> callBack.onSuccess(it)
        }
    }
}