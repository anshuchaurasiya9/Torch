package com.anshu.torchapp

import android.content.Context
import android.hardware.camera2.CameraManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import android.widget.ToggleButton
import androidx.annotation.RequiresApi

class MainActivity : AppCompatActivity() {
    lateinit var toggleBtn: ToggleButton
    lateinit var torchStatusTV: TextView

    // on below line we are creating
    // a variable for camera manager.
    lateinit var cameraManager: CameraManager

    // one below line we are
    // creating a string for camera ID
    lateinit var cameraID: String

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // on below line we are initializing our
        // toggle button and torch status text view.
        toggleBtn = findViewById(R.id.idBtnSwitch)
        torchStatusTV = findViewById(R.id.idTVTorchStatus)

        // on below line we are initializing our camera manager.
        cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        try {
            // O means back camera unit,
            // 1 means front camera unit
            // on below line we are getting camera id
            // for back camera as we will be using
            // torch for back camera
            cameraID = cameraManager.cameraIdList[0]
        } catch (e: Exception) {
            // on below line we are handling exception.
            e.printStackTrace()
        }

        // on below line we are adding on
        // click listener for toggle button.
        toggleBtn.setOnClickListener {
            // on below line we are checking
            // if the toggle button is checked.
            if (toggleBtn.isChecked) {
                try {
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                        // if button is checked we are simply
                        // initializing our camera manager and
                        // setting torch mode for back camera
                        // as true to switch on torch
                        cameraManager.setTorchMode(cameraID, true)

                        // on the below line we are simply displaying
                        // a toast message for torch on.
                        Toast.makeText(this@MainActivity, "Torch turned on..", Toast.LENGTH_LONG)
                            .show()

                        // on below line we are setting text
                        // to our text view as torch on.
                        torchStatusTV.setText("Torch On")
                    }
                } catch (e: Exception) {
                    // on below line we are handling exception.
                    e.printStackTrace()
                }
            } else {
                // this condition will be called
                // when toggle button is off.
                try {
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                        // if button is unchecked this method will be called.
                        // In this method we will initializing our camera
                        // manager with camera id and setting torch to off.
                        cameraManager.setTorchMode(cameraID, false)

                        // on below line we are simply displaying a toast message.
                        Toast.makeText(this@MainActivity, "Torch turned off..", Toast.LENGTH_SHORT)
                            .show()

                        // on below line we are setting text to text view.
                        torchStatusTV.setText("Torch Off")
                    }
                    // on below line we are handling exception
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    // on below line we are switching the torch off
    // when the application is killed in between.
    override fun finish() {
        super.finish()
        // this method will be called
        // when the application closes.
        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                // in this method we will be setting camera manager
                // torch mode to false to switch off the camera.
                cameraManager.setTorchMode(cameraID, false)
            }
            // on below line we are displaying a toast message as torch off.
            Toast.makeText(this@MainActivity, "Torch turned off..", Toast.LENGTH_SHORT)
                .show()
            // on below line we are handling exception.
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}