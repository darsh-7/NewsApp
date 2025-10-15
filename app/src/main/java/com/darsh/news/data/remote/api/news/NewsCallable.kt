package com.darsh.news.data.remote.api.news

import com.darsh.news.data.Constant
import com.darsh.news.data.remote.data_model.News
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

// Assuming this is your API interface
interface NewsCallable {
    /**
     * Fetches top news headlines. Country and category are optional.
     *
     * - If country and category are provided, it fetches specific news.
     *   (e.g., /v2/top-headlines?country=us&category=technology)
     * - If only one is provided, it filters by that parameter.
     *   (e.g., /v2/top-headlines?country=us)
     * - If neither is provided, it fetches general top headlines.
     *   (e.g., /v2/top-headlines)
     *
     * @param country The 2-letter ISO 3166-1 code of the country. Optional.
     * @param category The category of news to fetch. Optional.
     * @return A Retrofit Call object containing news data.
     */
    @GET("v2/top-headlines")
    fun getNews(
        @Query("country") country: String? = null,
        @Query("category") category: String? = null,
        @Query("apiKey") key: String = Constant.key
    ): Call<News>
}
