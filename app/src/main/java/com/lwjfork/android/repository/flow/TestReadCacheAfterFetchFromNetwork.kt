package com.lwjfork.android.repository.flow

import com.lwjfork.android.repository.factory.DefaultResourceFactory
import com.lwjfork.android.model.Response
import com.lwjfork.mvvm.jetpack.repository.factory.ResourceFactory
import com.lwjfork.mvvm.jetpack.repository.flow.ReadCacheAfterFetchFromNetwork


abstract class TestReadCacheAfterFetchFromNetwork<ResultType>(
    resourceFactory: ResourceFactory = DefaultResourceFactory.getInstance()
) : ReadCacheAfterFetchFromNetwork<ResultType, Response<ResultType>>(
    resourceFactory
) {
    override fun isSuccess(data: Response<ResultType>?): Boolean {
        return true
    }

    override fun useCacheIfFail(): Boolean {
        return false
    }
}