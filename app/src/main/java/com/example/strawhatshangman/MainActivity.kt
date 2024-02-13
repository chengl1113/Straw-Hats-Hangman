package com.example.strawhatshangman

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.util.Log

class MainActivity : AppCompatActivity() {

    private val letterButtons: MutableList<Button> = mutableListOf()
    @SuppressLint("DiscouragedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Add all buttons to letterButtons
        for (i in 65..90) { // Change the range as needed
            val buttonId = resources.getIdentifier("LETTER_"+i.toChar(),"id", packageName)
            val button = findViewById<Button>(buttonId)
            letterButtons.add(button)
        }

        val letterButtonClickListener = View.OnClickListener { view ->
            val buttonPressed: Button = view as Button
            buttonPressed.animate().alpha(0f).withEndAction {
                buttonPressed.isEnabled = false
            }
        }

        letterButtons.map { button -> button.setOnClickListener(letterButtonClickListener) }
    }
}