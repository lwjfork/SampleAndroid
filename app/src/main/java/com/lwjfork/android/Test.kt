package com.lwjfork.android

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lwjfork.android.model.Response
import com.lwjfork.android.repository.livedata.TestOnlyFetchFromNetwork
import com.lwjfork.android.repository.livedata.TestReadCacheAfterFetchFromNetwork
import com.lwjfork.mvvm.jetpack.executor.DefaultApiExecutor

class Test {


    private val apiExecutor = DefaultApiExecutor.getInstance()
    private fun <T> createLiveData(data: T, duration: Long): LiveData<Response<T>> {
        val livedata = MutableLiveData<Response<T>>()
        apiExecutor.runOnNetWorkIO {
            Thread.sleep(duration)
            livedata.postValue(Response(data))
        }
        return livedata
    }


    fun testOnlyFetchFromNetWork(): LiveData<Response<String>> {
        return object : TestOnlyFetchFromNetwork<String>() {
            override fun onStart() {
                super.onStart()
                result.value = Response.loading(null)
            }

            override fun createCall(): LiveData<Response<String>> {
                return createLiveData("创建一个网络请求", 2000L)
            }
        }.asLivedata()
    }







    fun firstReadCacheAndFetch(): LiveData<Response<String>> {
        return object :
            TestReadCacheAfterFetchFromNetwork<String>() {
            var cachedLiveData: MutableLiveData<Response<String>>? = null
            override fun createCall(): LiveData<Response<String>> {
                return createLiveData("创建一个网络请求", 3000L)
            }

            override fun shouldFetchFromNetwork(cacheData: Response<String>?): Boolean {
                return true
            }

            override fun loadFromCache(): LiveData<Response<String>>? {
                if (cachedLiveData == null) {
                    return createLiveData("读取第一次缓存", 2000L)
                } else {
                    return cachedLiveData!!
                }
            }
            override fun shouldCache(result: Response<String>): Boolean  = true

            override fun cacheResult(result: Response<String>?) {
                cachedLiveData = MutableLiveData<Response<String>>(Response(result?.data + "并缓存数据"))
            }


        }.asLivedata()
    }


}