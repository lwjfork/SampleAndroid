package com.lwjfork.android.repository.callback

import androidx.lifecycle.Observer
import com.lwjfork.android.model.*
import java.lang.IllegalArgumentException

abstract class DataResourceObserver<T> : Observer<Response<T>> {
    final override fun onChanged(t: Response<T>?) {
        when (t) {
            is LoadingResponse<*> -> {
                onStart()
            }
            is FailResponse<T> -> {
                onFail(t as FailResponse<T?>)
            }
            is SuccessResponse<T> -> {
                onSuccess(t)
            }
            is CacheResponse<T> -> {
                onCache(t)
            }
            else -> {
                throw IllegalArgumentException("参数错误")
            }
        }
    }

    abstract fun onStart()
    abstract fun onFail(t: Response<T?>?)
    abstract fun onSuccess(t: Response<T>?)
    open fun onCache(t: Response<T>?) {
        onSuccess(t)
    }
}