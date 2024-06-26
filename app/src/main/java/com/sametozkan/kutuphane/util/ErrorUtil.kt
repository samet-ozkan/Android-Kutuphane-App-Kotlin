package com.sametozkan.kutuphane.util

import android.content.Context
import androidx.fragment.app.FragmentManager
import com.sametozkan.kutuphane.R
import com.sametozkan.kutuphane.presentation.dialog.ErrorDialog

object ErrorUtil {

    fun showErrorDialog(responseCode: Int?, exceptionMessage: String?, fragmentManager: FragmentManager, context: Context?) {
        fragmentManager.findFragmentByTag("Hata")?.let {
            return
        }

        exceptionMessage?.let {
            println(it)
        }

        context?.let {
            responseCode?.let {
                when(it){
                    200 -> println("Veri null.")
                    404 -> ErrorDialog(context.getString(R.string.error_not_found)).show(fragmentManager, "Hata")
                    403 -> ErrorDialog(context.getString(R.string.error_access_denied)).show(fragmentManager, "Hata")
                    else -> ErrorDialog(context.getString(R.string.error_generic)).show(fragmentManager, "Hata")
                }
            }
        }

    }
}