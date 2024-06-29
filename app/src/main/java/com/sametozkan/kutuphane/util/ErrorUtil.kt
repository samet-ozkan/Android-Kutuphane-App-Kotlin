package com.sametozkan.kutuphane.util

import android.content.Context
import androidx.fragment.app.FragmentManager
import com.sametozkan.kutuphane.R
import com.sametozkan.kutuphane.presentation.dialog.ErrorDialog

object ErrorUtil {

    fun showErrorDialog(
        responseCode: Int?,
        exception: Exception,
        fragmentManager: FragmentManager,
        context: Context?
    ) {
        fragmentManager.findFragmentByTag("Hata")?.let {
            return
        }

        exception.message?.let {
            println(it)
        }

        context?.let {
            when (exception) {
                is MyException.AllFieldsMustBeFilledException ->
                    ErrorDialog(context.getString(R.string.error_fill_all_fields)).show(
                        fragmentManager,
                        "Hata"
                    )

                is MyException.InvalidCredentialsException ->
                    ErrorDialog(context.getString(R.string.wrong_email_or_password)).show(
                        fragmentManager,
                        "Hata"
                    )

                is MyException.ConflictException ->
                    ErrorDialog(context.getString(R.string.error_conflict)).show(fragmentManager, "Hata")

                is MyException.InvalidVerificationCodeException ->
                    ErrorDialog(context.getString(R.string.error_invalid_verification_code)).show(fragmentManager, "Hata");

                is MyException.PasswordMismatchException ->
                    ErrorDialog(context.getString(R.string.error_password_mismatch)).show(fragmentManager, "Hata")
                else -> responseCode?.let {
                    when (it) {
                        200 -> println("Veri null.")
                        404 -> ErrorDialog(context.getString(R.string.error_not_found)).show(
                            fragmentManager,
                            "Hata"
                        )

                        403 -> ErrorDialog(context.getString(R.string.error_access_denied)).show(
                            fragmentManager,
                            "Hata"
                        )

                    }
                } ?: kotlin.run {
                    ErrorDialog(context.getString(R.string.error_generic)).show(
                        fragmentManager,
                        "Hata"
                    )
                }
            }

        }
    }
}