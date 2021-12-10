package com.lwjfork.mvvm.jetpack.repository.flow.resource

import com.lwjfork.mvvm.jetpack.model.BaseResponse
import com.lwjfork.mvvm.jetpack.repository.factory.ResourceFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow

abstract class BaseDataFetchResource<ResultType, Response : BaseResponse<ResultType>>(protected val resourceFactory: ResourceFactory) {

    /**
     * 读取缓存
     *   1、 当返回为空时表示没有缓存，需要从网络请求
     *   2、 当返回不为空时，则交由 shouldFetchFromNetwork 来判断是否要进行网络请求
     */
    protected abstract suspend fun loadFromCached(): Response?
    protected abstract suspend fun fetchFromNetwork(cacheData: Response?): Response
    protected open fun isCacheValid(cacheData: Response?): Boolean = true
    protected abstract fun shouldFetchFromNetwork(cacheData: Response?): Boolean
     fun asFlow(): Flow<Response> = flow {
        onStart(this)
        val cacheData = loadFromCached()
        if (cacheData == null) {
            val networkData = fetchFromNetwork(null)
            processResponse(null, networkData, this)
        } else {
            // 缓存可能无效，直接进行网络请求
            if (shouldFetchFromNetwork(cacheData)) {
                if (isSuccess(cacheData)) { // 缓存有效则先发射缓存
                    // 先发送缓存数据
                    emit(resourceFactory.buildCache(cacheData))
                }
                val networkData = fetchFromNetwork(cacheData)
                processResponse(cacheData, networkData, this)
            } else {
                // 缓存有效，直接将缓存发射出去
                if (isSuccess(cacheData)) {
                    val successResponse = resourceFactory.buildSuccess(cacheData)
                    emit(successResponse)
                } else {
                    //  此处可能不可达
                    val failResponse = resourceFactory.buildFail(cacheData)
                    emit(failResponse)
                }
            }
        }
    }

    protected open fun useCacheIfFail(): Boolean = false
    protected abstract fun shouldCache(result: Response): Boolean
    protected abstract fun cacheResult(result: Response?)
    protected suspend fun onStart(collector: FlowCollector<Response>) {
        val loading: Response = resourceFactory.buildLoading()
        collector.emit(loading)
    }

    private suspend fun processResponse(
        cacheData: Response?,
        networkData: Response,
        collector: FlowCollector<Response>
    ) {
        if (isSuccess(networkData)) {
            if (shouldCache(networkData)) {
                cacheResult(networkData)
                val reCacheData = loadFromCached()
                collector.emit(resourceFactory.buildSuccess(reCacheData!!))
            } else {
                collector.emit(resourceFactory.buildSuccess(networkData))
            }
        } else {
            if (isSuccess(cacheData) && useCacheIfFail()) {
                collector.emit(resourceFactory.buildSuccess(cacheData!!))
            } else {
                collector.emit(resourceFactory.buildFail(networkData))
            }
        }
    }


    protected abstract fun isSuccess(data: Response?): Boolean

}