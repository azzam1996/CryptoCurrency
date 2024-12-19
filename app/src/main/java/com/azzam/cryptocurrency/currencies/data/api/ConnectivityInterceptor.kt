package com.azzam.cryptocurrency.currencies.data.api

import android.content.Context
import com.azzam.cryptocurrency.currencies.data.utils.isOnline
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class ConnectivityInterceptor(context: Context) : Interceptor {
    private val mContext: Context =context

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isOnline(mContext)) {
            throw NoConnectivityException()
        }
        val builder: Request.Builder = chain.request().newBuilder()
        return chain.proceed(builder.build())
    }
}

class NoConnectivityException : IOException() {
    override val message: String
        get() = "No Internet"
}
