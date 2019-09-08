package com.example.fundament.extensions

import android.app.AlertDialog
import android.content.Context
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.fundament.R

fun Context.toast(message: String, longToast: Boolean = true) {
    Toast.makeText(this, message, if(longToast) Toast.LENGTH_LONG else Toast.LENGTH_SHORT).show()
}

fun Context.alert(title: String = "Ошибка", message: String = "Что-то пошло не так", cancelable: Boolean = true){
    val dialog = AlertDialog.Builder(this)
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton(android.R.string.yes) { dialog, _ ->
            dialog.dismiss()
        }
        .setCancelable(cancelable)
        .create()

    dialog.setOnShowListener {
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setBackgroundColor(ContextCompat.getColor(this, R.color.colorTransparent))
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setBackgroundColor(ContextCompat.getColor(this, R.color.colorTransparent))
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(this, R.color.colorAccent))
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(this, R.color.colorAccent))
    }
    dialog.show()
}

fun Context.alertWithActions(
    title: String,
    message: String,
    cancelable: Boolean = true,
    positiveButtonCallback: () -> Unit,
    negativeButtonCallback: () -> Unit){

    val dialog = AlertDialog.Builder(this)
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton(android.R.string.yes) { dialog, _ ->
            positiveButtonCallback()
            dialog.dismiss()
        }
        .setNegativeButton(android.R.string.no) { dialog, _ ->
            negativeButtonCallback()
            dialog.dismiss()
        }
        .setCancelable(cancelable)
        .create()

    dialog.setOnShowListener {
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setBackgroundColor(ContextCompat.getColor(this, R.color.colorTransparent))
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setBackgroundColor(ContextCompat.getColor(this, R.color.colorTransparent))
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(this, R.color.colorAccent))
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(this, R.color.colorAccent))
    }
    dialog.show()
}