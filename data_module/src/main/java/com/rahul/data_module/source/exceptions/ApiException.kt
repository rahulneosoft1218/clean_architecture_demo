package com.rahul.data_module.source.exceptions

import com.google.gson.Gson
import com.rahul.data_module.models.response.ErrorModel
import retrofit2.HttpException

sealed class ApiException(val resCode: Int, open val errorMsg: String?) : Throwable() {

    override val message: String?
        get() = errorMsg

    object NoInternetConnection : ApiException(-1, "No Internet")
    object InternalServerException : ApiException(500, "Internal Server Error")
    data class UnknownException(var code: Int = 999, val error: String? = "Unknown Exception") :
        ApiException(code, error)

    data class BadRequestException(val error: String?) : ApiException(400, error) {
        override val errorMsg: String?
            get() = getErrorModelMsg()

        private fun getErrorModelMsg(): String? {
            return try {
                val errorModel: ErrorModel? = Gson().fromJson(error, ErrorModel::class.java)
                return errorModel?.error
            } catch (e: Exception) {
                e.printStackTrace()
                error
            }
        }
    }


    companion object {

        fun Throwable?.mapToApiException(): ApiException {

            if (this == null) return UnknownException()

            if (this !is HttpException) return UnknownException()

            return when (val resCode = this.code()) {
                500 -> InternalServerException
                400 -> BadRequestException(this.message)
                else -> {
                    UnknownException(resCode, this.message)
                }
            }


        }


    }

}
