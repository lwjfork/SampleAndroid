package com.lwjfork.android

import com.lwjfork.android.model.Response
import com.lwjfork.android.repository.factory.DefaultResourceFactory
import com.lwjfork.mvvm.jetpack.repository.flow.OnlyFetchFromNetwork
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow


class TestFlow {

    private suspend fun <T> createLiveData(data: T, duration: Long): Response<T> {
        delay(duration)
        return Response(data)
    }


    fun testOnlyFetchFromNetWork(): Flow<Response<String>> {
        return object :
            OnlyFetchFromNetwork<String, Response<String>>(DefaultResourceFactory.getInstance()) {

            override suspend fun fetchFromNetwork(cacheData: Response<String>?): Response<String> {
                return createLiveData("网络请求", 2000L)
            }

            override fun isSuccess(data: Response<String>?): Boolean {
                return true
            }
        }.asFlow()
    }


}