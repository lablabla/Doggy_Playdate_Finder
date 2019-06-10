package com.lablabla.doggyplaydatefinder.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User (var UID: String = "",
    var DisplayName: String? = "",
    var Email: String? = "") : Parcelable