package com.example.stackanswers.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

// set the base URL for the search URLs
private const val BASE_URL = "https://api.stackexchange.com/2.2/"
private const val SEARCH_METHOD = "search/advanced"
private const val SEARCH_SITE = "stackoverflow"
private const val FILTER_QUESTION_BODY_ON = "!9Z(-wwYGT"
private const val FILTER_ANSWER_BODY_ON = "!9Z(-wzu0T"
private const val FILTER_QUESTION_BODY_MARKDOWN = "!9Z(-wwK4f"
private const val FILTER_ANSWER_BODY_MARKDOWN = "!9Z(-wzftf"
private const val SORTING_CRITERIA = "relevance"
const val ANSWER_URL = "/answers/"
private const val CLIENT_KEY = "62PZ8TzM3yuxwwGS5fwx9Q(("

// add interceptor for debugging
private val interceptor = HttpLoggingInterceptor()

private val client = OkHttpClient.Builder()
    .addNetworkInterceptor(HttpLoggingInterceptor())
    .build()

// build the Moshi object
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

// build the Retrofit object
private val retrofit = Retrofit.Builder()
    .client(client)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface StackAnswerApiService {
    @GET(SEARCH_METHOD)
    fun getQuestions(
        @Query("q") question: String,
        @Query("site") site: String = SEARCH_SITE,
        @Query("filter") filter: String = FILTER_QUESTION_BODY_ON,
        @Query("accepted") accepted_answer: Boolean = true,
        @Query("sort") sort: String = SORTING_CRITERIA):
            Deferred<StackAnswerSearchExcerpt>

    // dynamically change the request
    @GET
    fun getAnswer(
        @Url answer_id: String,
        @Query("filter") filter: String = FILTER_ANSWER_BODY_ON,
        @Query("site") site: String = SEARCH_SITE):
            Deferred<StackAnswerCollection>
}


// expose the Retrofit service
object StackAnswerApi {
    val retrofitService: StackAnswerApiService by lazy {
        retrofit.create(StackAnswerApiService::class.java)
    }
}