package com.example.myapplication

import android.nfc.NfcAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import android.nfc.Tag
import android.nfc.tech.IsoDep

/**
 * Main activity class, which extends the NfcAdapter.ReaderCallback
 */
class MainActivity : AppCompatActivity(), NfcAdapter.ReaderCallback {

    /**
     * Fields for the class
     */
    private lateinit var nfcResponseView: NFCResponseViewUtilities
    private var nfcAdapter: NfcAdapter? = null

    /**
     * Override method that is created when the activity starts on the app
     */
    override fun onCreate(savedInstanceState: Bundle?) {

        //Initializing variables
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        nfcResponseView = NFCResponseViewUtilities(mutableListOf())
        rvInfoBlock.adapter = nfcResponseView
        rvInfoBlock.layoutManager = LinearLayoutManager(this)

        //Setting a listener for the delete button
        btnDelete.setOnClickListener {
            nfcResponseView.deleteInfoBlocks()
        }
    }

    /**
     * Method when the NFC reader resumes reading and is in range of a card
     */
    public override fun onResume() {
        super.onResume()
        nfcAdapter?.enableReaderMode(this, this,
            NfcAdapter.FLAG_READER_NFC_A or NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK,
            null)
    }

    /**
     * Method when the NFC reader is paused
     */
    public override fun onPause() {
        super.onPause()
        nfcAdapter?.disableReaderMode(this)
    }

    /**
     * Method when a tag is discovered
     */
    override fun onTagDiscovered(tag: Tag?) {
        val charset = Charsets.UTF_8

        //Getting information from the tag and the reader transceives a message and gets a response
        val isoDep = IsoDep.get(tag)
        isoDep.connect()
        //Making sure our specific AID is send during transcieve
        val response = isoDep.transceive(Utilities.hexStringToByteArray(
            "00A4040007F0000001234567"))
        runOnUiThread {
            val resText = response.toString(charset)

            //Checking if the card read has the correct AID our reader is meant to read
            if(resText.contains("9000")){
                //Successful reading
                val newText = resText.replace("9000 ", "")
                val infoBlock = InfoBlock(newText)
                nfcResponseView.addInfoBlock(infoBlock)
            } else {
                //Unsuccessful reading for other cards with the specific error
                val infoBlock = InfoBlock("Error "+Utilities.toHex(response))
                nfcResponseView.addInfoBlock(infoBlock)
            }

        }
        isoDep.close()
    }

}