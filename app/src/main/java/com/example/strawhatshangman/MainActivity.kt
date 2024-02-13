package com.example.strawhatshangman

import android.annotation.SuppressLint
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private val letterButtons: MutableList<Button> = mutableListOf()
    private val wordBank: Set<Pair<String, String>> = setOf(
        "FRUIT" to "APPLE",
        "FRUIT" to "BANANA",
        "FRUIT" to "ORANGE",
        "FRUIT" to "MANGO",
        "DRINKS" to "PEPSI",
        "DRINKS" to "SPRITE",
        "DRINKS" to "FANTA",
        "DRINKS" to "COKE",
        "FOOD" to "PIZZA",
        "FOOD" to "HOTDOG",
        "FOOD" to "BURGER",
        "FOOD" to "SANDWICH",
        "CARS" to "AUDI",
        "CARS" to "TOYOTA",
        "CARS" to "MERCEDES",
        "CARS" to "LEXUS"
    )

    @SuppressLint("DiscouragedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Grab the picture
        val gameImage: ImageView = findViewById(R.id.game_image)

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
                letterButtons.remove(buttonPressed)
            }
        }

        // Map all letter buttons to letterButtonClickListener
        letterButtons.map { button -> button.setOnClickListener(letterButtonClickListener) }

        val randomElement: Pair<String, String> = wordBank.toList().random()

        // Use this variable to reveal when user wants hint
        val category : String = randomElement.first

        // Use this variable to compare with user input(button clicks)
        val word : String = randomElement.second

        Toast.makeText(this, word, Toast.LENGTH_SHORT).show()

        // Generate the boxes for the letters
        for (char in word) {
            val textView = TextView(this)
            textView.text = ""
            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
            )
            layoutParams.weight = 1f
            textView.layoutParams = layoutParams
            textView.textSize = 36f
            textView.paintFlags = textView.paintFlags or Paint.UNDERLINE_TEXT_FLAG
            textView.setBackgroundResource(R.drawable.bottom_border)
            val wordReveal : LinearLayout = findViewById(R.id.word_reveal)
            wordReveal.addView(textView)
        }

        val hintTextView: TextView = findViewById(R.id.hint_text_view)
        hintTextView.textSize
        val hintButton: Button = findViewById(R.id.hint_button)
        var hintClicks: Int = 0
        var incorrect: Int = 0
        hintButton.setOnClickListener { view: View ->
            hintClicks++
            if (hintClicks > 3 || incorrect == 5) {
                Toast.makeText(this, R.string.hint_unavailable, Toast.LENGTH_SHORT).show()
            }
            else {
                incorrect++
                when (hintClicks) {
                    1 -> { hintTextView.setText(category)}
                    2 -> {removeHalfOfLetters(letterButtons, word)}
                    3 -> {showAllVowels()}
                }
                incrementDrawing(gameImage, incorrect)
            }
        }
    }

    private fun removeHalfOfLetters(letterButtons: MutableList<Button>, word: String) {
        val lettersLeft = letterButtons.size
        // Get a random letter that is left, remove and disable it
        val lettersToRemove = lettersLeft / 2
        var lettersRemoved = 0
        while (lettersRemoved < lettersToRemove) {
            val curButton: Button = letterButtons.random()
            if (curButton.text.toString() !in word){
                curButton.animate().alpha(0f).withEndAction {
                    curButton.isEnabled = false
                    letterButtons.remove(curButton)
                }
                lettersRemoved++
            }

        }

        Toast.makeText(this, "Removing half of letters", Toast.LENGTH_SHORT).show()
    }

    private fun showAllVowels() {
        Toast.makeText(this, "Showing all vowels", Toast.LENGTH_SHORT).show()
    }

    private fun incrementDrawing(image: ImageView, incorrect: Int) {
        when (incorrect) {
            1 -> image.setImageResource(R.drawable.screen2)
            2 -> image.setImageResource(R.drawable.screen3)
            3 -> image.setImageResource(R.drawable.screen4)
            4 -> image.setImageResource(R.drawable.screen5)
            5 -> image.setImageResource(R.drawable.screen6)
            6 -> image.setImageResource(R.drawable.screen7)
        }
    }


}