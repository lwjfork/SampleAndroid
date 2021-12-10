package com.lwjfork.mvvm.jetpack.repository.factory

import com.lwjfork.mvvm.jetpack.model.BaseResponse

interface ResourceFactory {

    fun <ResultType, Result : BaseResponse<ResultType>> buildLoading(): Result
    fun <ResultType, Result : BaseResponse<ResultType>> buildSuccess(originData: Result): Result
    fun <ResultType, Result : BaseResponse<ResultType>> buildCache(originData: Result): Result
    fun <ResultType, Result : BaseResponse<ResultType>> buildFail(originData: Result): Result


}