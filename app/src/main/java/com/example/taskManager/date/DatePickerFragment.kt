package com.example.taskManager.date

import android.app.Activity
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatDialogFragment
import java.text.SimpleDateFormat
import java.util.*


class DatePickerFragment : AppCompatDialogFragment(), OnDateSetListener {
    private val c: Calendar = Calendar.getInstance()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog { // Set the current date as the default date

        val c: Calendar = Calendar.getInstance()
        val year: Int = c.get(Calendar.YEAR)
        val month: Int = c.get(Calendar.MONTH)
        val day: Int = c.get(Calendar.DAY_OF_MONTH)
        // Return a new instance of DatePickerDialog
        return DatePickerDialog(activity!!, this@DatePickerFragment, year, month, day)
    }

    // called when a date has been selected
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, day: Int) {
        c.set(Calendar.YEAR, year)
        c.set(Calendar.MONTH, month)
        c.set(Calendar.DAY_OF_MONTH, day)
        val selectedDate: String =
            SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH).format(c.getTime())
        Log.d(TAG, "onDateSet: $selectedDate")
        // send date back to the target fragment
        targetFragment!!.onActivityResult(
            targetRequestCode,
            Activity.RESULT_OK,
            Intent().putExtra("selectedDate", selectedDate)
        )
    }

    companion object {
        private const val TAG = "DatePickerFragment"
    }
}