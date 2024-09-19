package com.saefulrdevs.articleflow.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProgrammingLanguage(
    val name: String,
    val description: String,
    val photo: Int,
    val release: Int,
    val inventor: String,
    val link: String
) : Parcelable
