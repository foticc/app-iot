package com.foticc.iot.api.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class UserPassword(
    val userName: String,
    val password: String
) : Parcelable
