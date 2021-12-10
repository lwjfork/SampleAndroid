package com.lwjfork.mvvm.jetpack.repository.flow

import com.lwjfork.mvvm.jetpack.model.BaseResponse
import com.lwjfork.mvvm.jetpack.repository.flow.resource.BaseDataFetchResource
import com.lwjfork.mvvm.jetpack.repository.factory.ResourceFactory

abstract class OnlyFetchFromNetwork<ResultType, Response : BaseResponse<ResultType>>(resourceFactory: ResourceFactory) :
    BaseDataFetchResource<ResultType, Response>(resourceFactory) {


    final override fun shouldCache(result: Response): Boolean = false

    final override fun cacheResult(result: Response?) {}

    override fun shouldFetchFromNetwork(cacheData: Response?): Boolean = true

    override suspend fun loadFromCached(): Response? {
        return null
    }
}