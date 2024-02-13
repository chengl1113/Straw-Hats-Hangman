package com.example.strawhatshangman

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.util.Log
import android.widget.TextView
import android.widget.Toast

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
                val letter = buttonPressed.text.toString()
                buttonPressed.isEnabled = false
            }
        }

        // Map all letter buttons to letterButtonClickListener
        letterButtons.map { button -> button.setOnClickListener(letterButtonClickListener) }

        val hintTextView: TextView = findViewById(R.id.hint_text_view)
        val hintButton: Button = findViewById(R.id.hint_button)
        var hintClicks: Int = 0
        var incorrect: Int = 0
        hintButton.setOnClickListener { view: View ->
            hintClicks++
            if (hintClicks > 3 || incorrect == 5) {
                Toast.makeText(this, R.string.hint_unavailable, Toast.LENGTH_SHORT).show()
            } else {
                when (hintClicks) {
                    1 -> {hintTextView.setText("fruit")}
                    2 -> {removeHalfOfLetters()}
                    3 -> {showAllVowels()}
                }
            }
        }
    }

    private fun removeHalfOfLetters() {
        Toast.makeText(this, "Removing half of letters", Toast.LENGTH_SHORT).show()
    }

    private fun showAllVowels() {
        Toast.makeText(this, "Showing all vowels", Toast.LENGTH_SHORT).show()
    }


}