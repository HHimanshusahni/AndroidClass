package com.online.hardwaresensors

import android.content.Context
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sm = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        for (sensor in sm.getSensorList(Sensor.TYPE_ALL)){
            Log.d("SENSOR", """

                ===========================
                name = ${sensor.name}
                type = ${sensor.stringType}
                vendor = ${sensor.vendor}
                ===========================

            """.trimIndent())
        }
        // getDefaultSensor give the best sensor at that time
         val accelSensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
         val  sensorListener= object : SensorEventListener{
             override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

             }

             override fun onSensorChanged(event: SensorEvent?) {
                 event?.apply {
                     Log.d("SENSOR","""\n
                         ax = ${values[0]}
                         ay = ${values[1]}
                         az = ${values[2]}
                     """.trimIndent())

                     val x = values[0]
                     val y = values[1]
                     val z = values[2]
                    val (r,g,b) = values.map { (255*((it*12)/24).toInt()) }
                     frameid.setBackgroundColor(Color.rgb(r,g,b))
                 }

             }

         }
        sm.registerListener(
                sensorListener,
                accelSensor,
                1000*1000000
        )
    }
}
