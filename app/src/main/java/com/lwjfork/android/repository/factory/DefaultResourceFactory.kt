package com.lwjfork.android.repository.factory

import com.lwjfork.android.model.Response
import com.lwjfork.mvvm.jetpack.model.BaseResponse
import com.lwjfork.mvvm.jetpack.repository.factory.ResourceFactory
import java.lang.IllegalArgumentException


class DefaultResourceFactory private constructor(): ResourceFactory {

    companion object {
        private val factory: DefaultResourceFactory by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { DefaultResourceFactory() }

        @JvmStatic
        fun getInstance(): ResourceFactory {
            return factory
        }
    }

    @Suppress("UNCHECKED_CAST")
   override fun <ResultType, Result : BaseResponse<ResultType>> buildLoading(): Result {

       return Response.loading(null) as Result
    }
    @Suppress("UNCHECKED_CAST")
    override fun <ResultType, Result : BaseResponse<ResultType>> buildCache(originData: Result): Result {
        if (originData is Response<*>) {
            return Response.cache(originData.code, originData.msg, originData.data) as Result
        }
        throw IllegalArgumentException("类型错误")
    }
    @Suppress("UNCHECKED_CAST")
    override fun <ResultType, Result : BaseResponse<ResultType>> buildSuccess(originData: Result): Result {
        if (originData is Response<*>) {
            return Response.success(originData.code, originData.msg, originData.data) as Result
        }
        throw IllegalArgumentException("类型错误")
    }
    @Suppress("UNCHECKED_CAST")
    override fun <ResultType, Result : BaseResponse<ResultType>> buildFail(originData: Result): Result {
        if (originData is Response<*>) {
            return Response.fail(originData.code, originData.msg, originData.data) as Result
        }
        throw IllegalArgumentException("类型错误")
    }
}