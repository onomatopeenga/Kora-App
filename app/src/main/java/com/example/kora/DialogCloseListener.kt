package com.example.kora

import android.content.DialogInterface


interface DialogCloseListener {
    fun handleDialogClose(dialog: DialogInterface?)
}