package com.wywrot.ewa.curriculumvitae.rest

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "experience")
class Experience(
    @PrimaryKey
    @SerializedName("startTimestamp")
    var startTimestamp: Long? = 0,
    @SerializedName("endTimestamp")
    var endTimestamp: Long? = 0,
    @SerializedName("companyName")
    var companyName: String? = "",
    @SerializedName("companyIcon")
    var companyIcon: String? = "",
    @SerializedName("jobTitle")
    var jobTitle: String? = "",
    @SerializedName("developedApps")
    var developedApps: List<String>? = emptyList()
)

class ExperiencesList(
    @SerializedName("experiencesList")
    var experiencesList: List<Experience>? = emptyList()
)