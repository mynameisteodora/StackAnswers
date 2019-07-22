package com.example.stackanswers.network

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize
import java.util.*

enum class ItemType(value: String) { QUESTION("questions"), ANSWER("answer") }

@JsonClass(generateAdapter = true)
data class StackAnswerSearchExcerpt(
    @Json(name = "has_more") val has_more: Boolean,
    @field:Json(name = "items") val items: List<Question>,
    @Json(name = "quota_max") val quota_max: Int,
    @Json(name = "quota_remaining") val quota_remaining: Int,
    @Json(name = "total") val total: Int?
)

@JsonClass(generateAdapter = true)
data class StackAnswerCollection(
    @Json(name = "has_more") val has_more: Boolean,
    @field:Json(name = "items") val items: List<Answer>,
    @Json(name = "quota_max") val quota_max: Int,
    @Json(name = "quota_remaining") val quota_remaining: Int,
    @Json(name = "total") val total: Int?
)


@JsonClass(generateAdapter = true)
@Parcelize
data class Question(
    @Json(name = "accepted_answer_id") val accepted_answer_id: Int?,
    @Json(name = "is_answered") val is_answered: Boolean,
    @Json(name = "body") val body: String,
    @Json(name = "question_id") val question_id: Int,
    @Json(name = "title") val title: String,
    @Json(name = "score") val score: Int
) : Parcelable

@JsonClass(generateAdapter = true)
data class Answer(
    @Json(name = "answer_id") val answer_id: Int,
    @Json(name = "body") val body: String,
    @Json(name = "score") val score: Int
)