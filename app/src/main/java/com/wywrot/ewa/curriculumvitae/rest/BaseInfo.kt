package com.wywrot.ewa.curriculumvitae.rest

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "base_info")
class BaseInfo(
    @PrimaryKey
    @SerializedName("email")
    var email: String = "",
    @SerializedName("name")
    var name: String? = "",
    @SerializedName("jobTitle")
    var jobTitle: String? = "",
    @SerializedName("phone")
    var phone: String? = "",
    @SerializedName("summaryContent")
    var summaryContent: String? = ""
)