package ru.spbstu.icc.kspt.lab2.continuewatch

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity2 : AppCompatActivity() {
    private var secondsElapsed: Int = 0
    private var sec: Int = 0
    private lateinit var textSecondsElapsed: TextView
    private lateinit var sharedPref: SharedPreferences

    companion object{
        const val SECONDS_COUNT = "secondsElapsed"
    }

    private var backgroundThread = Thread {
        while (true) {
            Thread.sleep(1000)
            textSecondsElapsed.post {
                textSecondsElapsed.text = getString(R.string.textSeconds, secondsElapsed++)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textSecondsElapsed = findViewById(R.id.textSecondsElapsed)
        backgroundThread.start()
        Log.i("LifecycleCallbacks", "Activity onCreate()")
    }

    override fun onStart(){
        sharedPref = this.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)

        sec = sharedPref.getInt(SECONDS_COUNT, sec)
        secondsElapsed = sec

        textSecondsElapsed.text = getString(R.string.textSeconds, secondsElapsed)
        super.onStart()
        Log.i("LifecycleCallbacks", "Activity onStart()")
    }

    override fun onStop() {
        sharedPref = this.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)

        sharedPref.edit().putInt(SECONDS_COUNT, secondsElapsed).apply()
        super.onStop()
        Log.i("LifecycleCallbacks", "Activity onStop()")
    }
}