package com.example.combankapp.util

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import android.text.format.DateFormat
import com.example.combankapp.R
import java.util.Calendar
import kotlin.math.abs


object Utils {
    private var format = "yyyy-MM-dd"
    private var space = "  "
    fun parseStringToDate(dateString: String?): CharSequence? {
        val dateFormat: SimpleDateFormat = SimpleDateFormat(format, Locale.getDefault())
        try {
            return DateFormat.format("EEEE", dateFormat.parse(dateString)).toString().substring(0,3)
                .plus(space
                .plus(DateFormat.format("MMM", dateFormat.parse(dateString)))
                .plus(space)
                .plus(DateFormat.format("dd", dateFormat.parse(dateString)))
                    .plus(space)
                .plus(dateString?.let { daysBetweenTodayAndDate(it).toString() + " days ago"}))
        } catch (e: ParseException) {
            e.printStackTrace() // Handle parsing exception as needed
            return null
        }
    }


    private fun daysBetweenTodayAndDate(dateString: String): Long {
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        val date = sdf.parse(dateString)
        val today = Calendar.getInstance()

        val diff = date.time - today.timeInMillis
        return abs(diff / (1000 * 60 * 60 * 24))
    }

   fun getCategoryImage(category: String): Int {
       return when (category){
           "travel" -> R.drawable.icon_category_travel
           "entertainment" -> R.drawable.icon_category_entertainment
           "shopping" -> R.drawable.icon_category_shopping
           "education" -> R.drawable.icon_category_education
           "cash" -> R.drawable.icon_category_cash
           "cards" -> R.drawable.icon_category_cards
           "transportation" -> R.drawable.icon_category_transportation
           "category" -> R.drawable.icon_category_categories
           else -> return  R.drawable.icon_category_cash
       }


    }
}