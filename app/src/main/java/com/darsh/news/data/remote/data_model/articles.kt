package com.darsh.news.data.remote.data_model

data class News (
    val articles : Array<Article> ,
    val totalResults: Int,
    val status: String,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as News

        if (!articles.contentEquals(other.articles)) return false
        if (totalResults != other.totalResults) return false
        if (status != other.status) return false

        return true
    }

    override fun hashCode(): Int {
        var result = articles.contentHashCode()
        result = 31 * result + totalResults
        result = 31 * result + status.hashCode()
        return result
    }
}