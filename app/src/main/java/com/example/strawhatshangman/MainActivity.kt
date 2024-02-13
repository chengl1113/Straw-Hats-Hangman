package com.example.strawhatshangman

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.util.Log

class MainActivity : AppCompatActivity() {

    private val letterButtons: MutableList<Button> = mutableListOf()
    private val wordBank: Map<String, List<String>> = mapOf(
        "fruit" to listOf("apple", "banana", "orange", "mango"),
        "drinks" to listOf("pepsi", "sprite", "fanta", "coke"),
        "food" to listOf("pizza", "hotdog", "burger", "sandwich"),
        "cars" to listOf("audi", "toyota", "mercedes", "lexus")
    )
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

        // OnClickListener for all the buttons
        val letterButtonClickListener = View.OnClickListener { view ->
            val buttonPressed: Button = view as Button
            buttonPressed.animate().alpha(0f).withEndAction {
                buttonPressed.isEnabled = false
            }
        }

        // Map all letter buttons to letterButtonClickListener
        letterButtons.map { button -> button.setOnClickListener(letterButtonClickListener) }
    }


}