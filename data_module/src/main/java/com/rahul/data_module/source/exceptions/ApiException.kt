package com.rahul.data_module.source.exceptions

import com.google.gson.Gson
import com.rahul.data_module.models.response.ErrorModel
import java.io.IOException

sealed class ApiException(val resCode: Int, open val errorMsg: String?) : IOException() {

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

            var apiException: ApiException = UnknownException()

            if (this == null) return apiException


            if (this.cause?.cause is ApiException) {
                apiException = (this.cause?.cause as ApiException?) ?: apiException
            }

            return apiException

            /*  if (this !is HttpException) return UnknownException()

              return when (val resCode = this.code()) {
                  -1 -> NoInternetConnection
                  500 -> InternalServerException
                  400,404 -> {
                      val errorRes = this.response()?.errorBody()?.string()
                      BadRequestException(errorRes)
                  }
                  else -> {
                      UnknownException(resCode, this.message)
                  }
              }*/


        }


    }

}
