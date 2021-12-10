package com.lwjfork.mvvm.jetpack.repository.flow

import com.lwjfork.mvvm.jetpack.model.BaseResponse
import com.lwjfork.mvvm.jetpack.repository.flow.resource.BaseDataFetchResource
import com.lwjfork.mvvm.jetpack.repository.factory.ResourceFactory

abstract class ReadCacheAfterFetchFromNetwork<ResultType, Response : BaseResponse<ResultType>>(
    resourceFactory: ResourceFactory
) :
    BaseDataFetchResource<ResultType, Response>(resourceFactory) {
}