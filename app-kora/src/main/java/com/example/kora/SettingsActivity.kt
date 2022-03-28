package com.example.kora

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import com.example.kora.databinding.ActivitySettingsBinding


class SettingsActivity : AppCompatActivity() {
    private lateinit var settingsBinding: ActivitySettingsBinding

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        settingsBinding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(settingsBinding.root)

        sharedPreferences = getSharedPreferences("night",0)
        val booleanValue : Boolean = sharedPreferences.getBoolean("night_mode",true)
        if (booleanValue){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            settingsBinding.nightswitch.isChecked = true

        }
        settingsBinding.nightswitch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
                settingsBinding.nightswitch.isChecked = true
                val editor = sharedPreferences.edit()
                editor.putBoolean("night_mode", true)
                editor.commit()
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                settingsBinding.nightswitch.isChecked = false
                val editor = sharedPreferences.edit()
                editor.putBoolean("night_mode", false)
                editor.commit()
            }
        }


    }
}