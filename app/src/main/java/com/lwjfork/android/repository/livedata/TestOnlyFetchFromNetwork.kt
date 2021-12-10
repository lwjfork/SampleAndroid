package com.lwjfork.android.repository.livedata

import com.lwjfork.android.model.Response
import com.lwjfork.android.repository.executor.DefaultApiExecutor
import com.lwjfork.android.repository.factory.DefaultResourceFactory
import com.lwjfork.mvvm.jetpack.executor.BaseApiExecutor
import com.lwjfork.mvvm.jetpack.repository.livedata.OnlyFetchFromNetwork
import com.lwjfork.mvvm.jetpack.repository.factory.ResourceFactory


abstract class TestOnlyFetchFromNetwork<ResultType>(
    executor: BaseApiExecutor = DefaultApiExecutor.getInstance(),
    resourceFactory: ResourceFactory = DefaultResourceFactory.getInstance()
) : OnlyFetchFromNetwork<ResultType, Response<ResultType>>(
    executor,
    resourceFactory
) {

    override fun isSuccess(data: Response<ResultType>?): Boolean {

        return true
    }

    override fun useCacheIfFail(): Boolean {
        return false
    }
}