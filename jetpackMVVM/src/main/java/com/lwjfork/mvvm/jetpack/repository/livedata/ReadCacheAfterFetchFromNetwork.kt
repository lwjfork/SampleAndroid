package com.lwjfork.mvvm.jetpack.repository.livedata

import com.lwjfork.mvvm.jetpack.executor.BaseApiExecutor
import com.lwjfork.mvvm.jetpack.executor.DefaultApiExecutor
import com.lwjfork.mvvm.jetpack.model.BaseResponse
import com.lwjfork.mvvm.jetpack.repository.livedata.resource.BaseDataFetchResource
import com.lwjfork.mvvm.jetpack.repository.factory.ResourceFactory

/**
 *  先读取本地，再读取网络
 *    网络请求成功后将数据缓存到本地
 */
abstract class ReadCacheAfterFetchFromNetwork<ResultType, Response : BaseResponse<ResultType>>(
    executor: BaseApiExecutor = DefaultApiExecutor.getInstance(),
    resourceFactory: ResourceFactory
) : BaseDataFetchResource<ResultType, Response>(executor, resourceFactory)