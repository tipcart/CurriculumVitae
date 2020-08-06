package com.wywrot.ewa.curriculumvitae.rest

import com.google.gson.annotations.SerializedName

class BaseInfo(
    @SerializedName("name")
    var name: String? = "",
    @SerializedName("jobTitle")
    var jobTitle: String? = "",
    @SerializedName("phone")
    var phone: String? = "",
    @SerializedName("email")
    var email: String? = "",
    @SerializedName("summaryContent")
    var summaryContent: String? = ""
) {

}