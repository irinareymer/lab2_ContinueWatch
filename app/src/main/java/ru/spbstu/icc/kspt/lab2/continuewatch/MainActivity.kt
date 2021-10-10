package ru.spbstu.icc.kspt.lab2.continuewatch

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var secondsElapsed: Int = 0
    private var sec: Int = 0
    private lateinit var textSecondsElapsed: TextView

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
        secondsElapsed = sec
        textSecondsElapsed.text = getString(R.string.textSeconds, secondsElapsed)
        super.onStart()
        Log.i("LifecycleCallbacks", "Activity onStart()")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        sec = secondsElapsed
        outState.run {
            putInt(SECONDS_COUNT, sec)
        }
        super.onSaveInstanceState(outState)
        Log.i("LifecycleCallbacks", "Activity onSaveInstanceState()")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        savedInstanceState.run {
            sec = getInt(SECONDS_COUNT)
        }
        secondsElapsed = sec
        textSecondsElapsed.text = getString(R.string.textSeconds, secondsElapsed)
        super.onRestoreInstanceState(savedInstanceState)
        Log.i("LifecycleCallbacks", "Activity onRestoreInstanceState()")
    }

}