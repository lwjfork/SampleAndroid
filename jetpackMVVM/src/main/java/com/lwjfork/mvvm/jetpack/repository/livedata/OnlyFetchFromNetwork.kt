package com.lwjfork.mvvm.jetpack.repository.livedata

import androidx.lifecycle.LiveData
import com.lwjfork.mvvm.jetpack.executor.BaseApiExecutor
import com.lwjfork.mvvm.jetpack.executor.DefaultApiExecutor
import com.lwjfork.mvvm.jetpack.model.BaseResponse
import com.lwjfork.mvvm.jetpack.repository.livedata.resource.BaseDataFetchResource
import com.lwjfork.mvvm.jetpack.repository.factory.ResourceFactory

/**
 * 只读取网络请求
 */
abstract class OnlyFetchFromNetwork<ResultType, Response : BaseResponse<ResultType>>(
    executor: BaseApiExecutor = DefaultApiExecutor.getInstance(),
    resourceFactory: ResourceFactory
) : BaseDataFetchResource<ResultType, Response>(executor, resourceFactory) {

    final override fun shouldCache(result: Response): Boolean = false

    final override fun cacheResult(result: Response?) {}

    override fun shouldFetchFromNetwork(cacheData: Response?): Boolean = true

    final override fun loadFromCache(): LiveData<Response>? {
        return null
    }


}