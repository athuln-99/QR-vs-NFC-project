package com.example.cardemulation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.InputStream

/**
 * Class for main Activity
 */
class MainActivity : AppCompatActivity() {
    /*
    //Attribute for the main activity that were used for customizable implementation that was not completed

    private var sNumber = "null"
    private var sName = "null"
    private var state = false
    private var mainHCE =  HCE()

     */

    /**
     * Method runs when activity is created
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*
        //This section of code was an attempt to allow for a customizable NFC card

        //Adding a submit button listener
        submitBtn.setOnClickListener{
            val sNum = studentNumber.text.toString()
            val sNam = studentName.text.toString()

            //Variables to check criteria
            var checkNum =  false
            var checkNam =  false

            //Checking if the student number is only digits and of size 7
            var numeric = true
            numeric = sNum.matches("-?\\d+(\\.\\d+)?".toRegex())
            if(numeric && sNum.length == 7){
                sNumber = sNum
                // We can transmit now
                checkNum = true
            } else {
                errorMessage.text = "Warning! The student number must be only digits and 7 digits long."
            }

            //Checking if the number is not empty
            if(sNam.isNotEmpty()){
                sName = sNam
                checkNam = true
            } else {
                errorMessage.text = errorMessage.text.toString()+ "\nWarning! The student name field is empty."
            }

            //Able to transmit
            if(checkNum && checkNam){
                state = true
            val inputStream: InputStream = File("data.txt").inputStream()
                errorMessage.text = inputStream.bufferedReader().use { it.readText() }
            }
        }


        //Adding a transmit button listener
        transmitBtn.setOnClickListener{
            errorMessage.text = null

            val fileName = "data.txt"
            var file = File(fileName)
            // create a new file
            file.writeText("9000 Student Number: $sNumber Name: $sName")
            //startService(Intent(this, HCE::class.java))
        }

         */
    }
}