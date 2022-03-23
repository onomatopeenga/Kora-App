package com.example.kora

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*


class CalendarActivity : AppCompatActivity() {

    private lateinit var web: WebView
    private lateinit var fab: FloatingActionButton

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar_v2)
        setupViews()
        web.settings.javaScriptEnabled = true
        web.settings.useWideViewPort = true
        web.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                if (url != null) view?.loadUrl(url)
                return true
            }
        }
        web.loadUrl("https://calendar.google.com/")
        fab.setOnClickListener { addCalendarEvent() }
    }

    private fun setupViews() {
        web = findViewById(R.id.webCalendar)
        fab = findViewById(R.id.fabAddEvent)
    }

    private fun addCalendarEvent() {
        val calendarEvent: Calendar = Calendar.getInstance()
        val i = Intent(Intent.ACTION_EDIT)
        i.type = "vnd.android.cursor.item/event"
        i.putExtra("beginTime", calendarEvent.timeInMillis)
        i.putExtra("allDay", true)
        i.putExtra("rule", "FREQ=YEARLY")
        i.putExtra("endTime", calendarEvent.timeInMillis + 60 * 60 * 1000)
        i.putExtra("title", "Calendar Event")
        startActivity(i)
    }
}