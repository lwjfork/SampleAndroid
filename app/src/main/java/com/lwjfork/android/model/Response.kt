package com.lwjfork.android.model

import com.lwjfork.mvvm.jetpack.model.BaseResponse

open class Response<Data>(data: Data?) : BaseResponse<Data>(data) {

    var code: Int? = null
    var msg: String? = null

    constructor(code: Int?, msg: String?, data: Data?) : this(data) {
        this.code = code
        this.msg = msg
    }


    companion object {
        @JvmStatic
        fun <Data> loading(data: Data?): Response<Data> {
            return LoadingResponse(data)
        }

        @JvmStatic
        fun <Data> success(code: Int?, msg: String?, data: Data?): Response<Data> {
            return SuccessResponse(code, msg, data)
        }

        @JvmStatic
        fun <Data> cache(code: Int?, msg: String?, data: Data?): Response<Data> {
            return CacheResponse(code, msg, data)
        }

        @JvmStatic
        fun <Data> fail(code: Int?, msg: String?, data: Data?): Response<Data> {
            return FailResponse(code, msg, data)
        }
    }
}

class LoadingResponse<Data>(data: Data?) : Response<Data>(data)
class FailResponse<Data>(code: Int?, msg: String?, data: Data?) : Response<Data>(code, msg, data)
class SuccessResponse<Data>(code: Int?, msg: String?, data: Data?) : Response<Data>(code, msg, data)
class CacheResponse<Data>(code: Int?, msg: String?, data: Data?) : Response<Data>(code, msg, data)