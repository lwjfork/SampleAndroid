package com.lwjfork.android.repository.callback

import com.lwjfork.android.model.Response

abstract class DataResourceFlowObserver<T> : Function<Response<T>> {



    abstract fun onStart()
    abstract fun onFail(t: Response<T?>?)
    abstract fun onSuccess(t: Response<T>?)
    open fun onCache(t: Response<T>?) {
        onSuccess(t)
    }

}