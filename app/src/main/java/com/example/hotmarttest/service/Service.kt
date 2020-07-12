package com.example.hotmarttest.service

import com.example.hotmarttest.BuildConfig.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import okhttp3.logging.HttpLoggingInterceptor.Level.HEADERS
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit.MILLISECONDS


class Service {
    private var instance: IService? = null

    fun getInstance(): IService? {
        if (instance == null) createInstance()
        return instance
    }

    private fun createInstance() {
        val authHeader = headers

        /**
         * Log interceptor (just for debug)
         */
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HEADERS
        interceptor.level = BODY
        val builder = OkHttpClient.Builder()
            .connectTimeout(CONNECTION_TIME_OUT, MILLISECONDS)
            .readTimeout(TIME_OUT, MILLISECONDS)
            .writeTimeout(TIME_OUT, MILLISECONDS)
            .addInterceptor(interceptor)
            .addInterceptor { chain ->
                val original: Request = chain.request()
                val requestBuilder: Request.Builder = original.newBuilder()
                if (!original.url().pathSegments().contains("registrations")) {
                    for ((key, value) in authHeader) {
                        if (value != null) {
                            requestBuilder.addHeader(key, value)
                        }
                    }
                }
                val request: Request = requestBuilder.build()
                chain.proceed(request)
            }
        val client: OkHttpClient = builder.build()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build()
        instance = retrofit.create(IService::class.java)
    }

    private val headers: HashMap<String, String?>
        get() {
            val header = HashMap<String, String?>()
            header["Content-Type"] = "application/json"
            return header
        }

    companion object {
        private const val TIME_OUT = 200L * 1000
        private const val CONNECTION_TIME_OUT = 200L * 1000
    }

}