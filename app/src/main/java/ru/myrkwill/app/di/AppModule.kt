package ru.myrkwill.app.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.myrkwill.app.data.api.NewsService
import ru.myrkwill.app.utils.Constants
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun baseUrl() = Constants.BASE_URL

    @Provides
    fun logging() = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

//    @Provides
//    fun okHttpClient() = OkHttpClient.Builder()
//        .addInterceptor(logging())
//        .build()

//    @Provides
//    fun interceptorWithApiKeyAsQueryParameter() = Interceptor {
//        it.request()
//            .newBuilder()
//            .url(it.request().url
//                .newBuilder()
//                .addQueryParameter("apiKey", Constants.API_KEY)
//                .build())
//            .build()
//            .let { request -> it.proceed(request) }
//    }

//    @Provides
//    @Singleton
//    fun provideRetrofit(baseUrl: String): NewsService =
//        Retrofit.Builder()
//            .baseUrl(baseUrl)
//            .addConverterFactory(GsonConverterFactory.create())
//            .client(okHttpClient())
//            .build()
//            .create(NewsService::class.java)

    @Provides
    fun okHttpClientMock(context: Context) = OkHttpClient.Builder()
        .connectTimeout(60L, TimeUnit.SECONDS)
        .readTimeout(60L, TimeUnit.SECONDS)
        .addInterceptor(logging())
        .addInterceptor(MockResponseInterceptor(context))
        .build()

    @Provides
    @Singleton
    fun provideRetrofitMock(baseUrl: String, @ApplicationContext context: Context): NewsService =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClientMock(context))
            .build()
            .create(NewsService::class.java)

}