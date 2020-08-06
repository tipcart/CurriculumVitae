package com.wywrot.ewa.curriculumvitae.data

import android.annotation.SuppressLint
import java.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
object DateUtils {
    private const val date_str = "dd.MM.yyyy"

    val dateFormat = SimpleDateFormat(date_str)
}