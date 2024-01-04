package com.example.personalapp

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity


class AppStep : ComponentActivity(), SensorEventListener{




    private var sensorManager: SensorManager? = null
    private var running = false
    private var totalSteps = 0f
    private var previousTotalSteps = 0f

    private var circularProgressBar = findViewById<com.mikhaellopez.circularprogressbar.CircularProgressBar>(R.id.circularProgressBar)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.adimsayar)


        loadData()
        resetSteps()
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

    }

    override fun onResume() {
        super.onResume()
        running = true
        val stepSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)

        if (stepSensor == null){
            Toast.makeText(this,"No sensor detected on this device",Toast.LENGTH_SHORT).show()
        }else{
            sensorManager?.registerListener(this,stepSensor,SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onSensorChanged(event:SensorEvent?) {
        val stepsTaken=findViewById<TextView>(R.id.tv_stepsTaken)
        if(running){
            totalSteps = event!!.values[0]
            val currentSteps = totalSteps.toInt() - previousTotalSteps.toInt()
            stepsTaken.text=("$currentSteps")

            circularProgressBar.apply {
                setProgressWithAnimation(currentSteps.toFloat())
            }


        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }


    private fun resetSteps(){
        val stepsTaken=findViewById<TextView>(R.id.tv_stepsTaken)
        stepsTaken.setOnClickListener{
            Toast.makeText(this, "Long tap to reset steps", Toast.LENGTH_SHORT).show()
        }

        stepsTaken.setOnLongClickListener{
            previousTotalSteps=totalSteps
            stepsTaken.text = 0.toString()
            saveData()

            true
        }
    }

    private fun saveData(){
        val sharedPreferences = getSharedPreferences("myPrefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putFloat("key1",previousTotalSteps)
        editor.apply()
    }

    private fun loadData(){
        val sharedPreferences = getSharedPreferences("myPrefs", MODE_PRIVATE)
        val savedNumber = sharedPreferences.getFloat("key1",0f)
        Log.d("AppStep","$savedNumber")
        previousTotalSteps = savedNumber
    }

}