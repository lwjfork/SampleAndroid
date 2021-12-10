package com.lwjfork.mvvm.jetpack.repository.livedata.resource

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.lwjfork.mvvm.jetpack.executor.BaseApiExecutor
import com.lwjfork.mvvm.jetpack.model.BaseResponse
import com.lwjfork.mvvm.jetpack.repository.factory.ResourceFactory

abstract class BaseDataFetchResource<ResultType, Response : BaseResponse<ResultType>>(
    protected val apiExecutor: BaseApiExecutor,
    protected val resourceFactory: ResourceFactory
) {

    protected val result = MediatorLiveData<Response>()

    /**
     * 请求网络
     * @param cacheData
     */
    protected open fun fetchFromNetwork(cacheData: LiveData<Response>?) {
        val networkLivaData = createCall()
        cacheData?.let {
            result.addSource(cacheData) { newData ->
                if (isSuccess(newData)) {
                    setValue(resourceFactory.buildCache(newData))
                }
            }
        }

        result.addSource(networkLivaData) { response ->
            result.removeSource(networkLivaData)
            cacheData?.let {
                result.removeSource(cacheData)
            }
            processResponse(cacheData = cacheData?.value, networkData = response)
        }
    }

    protected fun processResponse(cacheData: Response?, networkData: Response) {
        if (isSuccess(networkData)) { // 网络成功
            if (shouldCache(networkData)) {
                // 缓存数据
                apiExecutor.runOnDiskIO {
                    cacheResult(networkData)
                    apiExecutor.runOnMainThread {
                        result.addSource(loadFromCache()!!) {
                            setValue(resourceFactory.buildSuccess(it))
                        }
                    }
                }
            } else {
                setValue(resourceFactory.buildSuccess(networkData))
            }
        } else {
            if (isSuccess(cacheData) && useCacheIfFail()) {
                setValue(resourceFactory.buildSuccess(cacheData!!))
            } else {
                setValue(resourceFactory.buildFail(networkData))
            }
        }

    }

    protected open fun useCacheIfFail(): Boolean = false

    protected abstract fun isSuccess(data: Response?): Boolean


    protected abstract fun loadFromCache(): LiveData<Response>?

    fun asLivedata(): LiveData<Response> {
        execute()
        return result
    }


    protected open fun onStart() {
        setValue(resourceFactory.buildLoading())
    }

    @MainThread
    protected abstract fun createCall(): LiveData<Response>

    @MainThread
    protected fun setValue(newValue: Response) {
        if (result.value != newValue) {
            result.value = newValue
        }
    }

    abstract fun shouldCache(result: Response): Boolean
    abstract fun shouldFetchFromNetwork(cacheData: Response?): Boolean
    abstract fun cacheResult(result: Response?)

    protected open fun execute() {
        onStart()
        val cacheDataLiveData = loadFromCache()
        if (cacheDataLiveData == null) {
            fetchFromNetwork(null)
        } else {
            result.addSource(cacheDataLiveData) { cache ->
                result.removeSource(cacheDataLiveData)
                if (shouldFetchFromNetwork(cache)) {
                    fetchFromNetwork(cacheDataLiveData)
                } else {
                    result.addSource(cacheDataLiveData) { finalResult ->
                        setValue(resourceFactory.buildSuccess(finalResult))
                    }
                }
            }
        }
    }


}