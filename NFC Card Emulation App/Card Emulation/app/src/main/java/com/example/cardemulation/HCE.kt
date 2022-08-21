package com.example.cardemulation

import android.nfc.cardemulation.HostApduService
import android.os.Bundle
import android.util.Log

/**
 * Class extends the HostApduService class so that we can add the service to the background of the app when it runs
 */
class HCE: HostApduService() {
    /**
     * We use a companion object to define class level variables and methods called static variables
     */
    companion object {
        val TAG = "Host Card Emulator"
        val STATUS_FAILED = "Error 6F00"
        val CLA_NOT_SUPPORTED = "Error 6E00"
        val INS_NOT_SUPPORTED = "Error 6D00"
        val AID = "F0000001234567"
        val SELECT_INS = "A4"
        val DEFAULT_CLA = "00"
        val MIN_APDU_LENGTH = 12
    }

    /**
     * Method when the card is deactivated
     */
    override fun onDeactivated(reason: Int) {
        Log.d(TAG, "Deactivated: " + reason)
    }

    /**
     * Method is to process and APDU command.
     * Within this method we check if the command sent by the Card reader fits the requirements for our
     * specific emulated card and will send a response in return
     */
    override fun processCommandApdu(apduCommand: ByteArray?, extras: Bundle?): ByteArray {
        val charset = Charsets.UTF_8

        //No apduCommand sent
        if (apduCommand == null) {
            return STATUS_FAILED.toByteArray(charset)
        }

        //Converting the byte array from the APDU command into a string with the hexadecimal code
        val apduCommandHex = Utilities.toHex(apduCommand)

        //Checking if it fits the minimum size
        if (apduCommandHex.length < MIN_APDU_LENGTH) {
            return STATUS_FAILED.toByteArray(charset)
        }

        //Checking if the CLA is supported
        if (apduCommandHex.substring(0, 2) != DEFAULT_CLA) {
            return CLA_NOT_SUPPORTED.toByteArray(charset)
        }

        //Checking if the INS is supported
        if (apduCommandHex.substring(2, 4) != SELECT_INS) {
            return INS_NOT_SUPPORTED.toByteArray(charset)
        }


        //Checking if the AID is the one the reader expects
        if (apduCommandHex.substring(10, 24) == AID) {

            /*
            Opening text files for customizable NFC card

            val inputStream: InputStream = File("data.txt").inputStream()
            var studentInfo = inputStream.bufferedReader().use { it.readText() }
            */

            //Here we send our custom string with the information for the designated student in the form a string
            //val studentInfo = "9000 Student Number: s4126351 \nName: Athul Raj Nambiar"
            val studentInfo = "9000 Student Number: s3964973 \nName: Suhaib Basir"

            //The string is then converted into a byte array
            return studentInfo.toByteArray(charset)
        } else {
            return STATUS_FAILED.toByteArray(charset)
        }

    }

}