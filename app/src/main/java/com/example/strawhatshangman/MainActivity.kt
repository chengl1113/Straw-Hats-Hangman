package com.example.strawhatshangman

import android.annotation.SuppressLint
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

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

        initializeUI()
        }

    private fun initializeUI() {
        val randomElement: Pair<String, String> = wordBank.toList().random()
        // Use this variable to reveal when user wants hint
        val category : String = randomElement.first
        // Use this variable to compare with user input(button clicks)
        val word : String = randomElement.second
        // Grab the picture
        val gameImage: ImageView = findViewById(R.id.game_image)
        val hintTextView: TextView = findViewById(R.id.hint_text_view)
        val hintButton: Button = findViewById(R.id.hint_button)
        var hintClicks: Int = 0
        var incorrect: Int = 0
        var correct: Int = 0

        // Generate the boxes for the letters
        val textViewList: MutableList<TextView> = mutableListOf()
        for (char in word) {
            // Create new TextView and give it its layout
            val textView = TextView(this)
            textViewList.add(textView)
            textView.text = ""
            val layoutParams = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.MATCH_PARENT,
            )
            textView.text = ""
            layoutParams.weight = 1f
            textView.layoutParams = layoutParams
            textView.textSize = 36f
            textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
            textView.gravity = Gravity.CENTER
            textView.paintFlags = textView.paintFlags
            textView.setBackgroundResource(R.drawable.bottom_border)

            val wordReveal : LinearLayout = findViewById(R.id.word_reveal)

            wordReveal.addView(textView)
        }

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
                // Player guesses wrong
                if (letter !in word) {
                    // Add a body part to image
                    incorrect++
                    incrementDrawing(gameImage, incorrect)
                }
                // Player guesses right
                else {
                    correct++
                    processCorrectGuess(view, letter, word, textViewList, correct)
                }
            }
        }

        // Map all letter buttons to letterButtonClickListener
        letterButtons.map { button -> button.setOnClickListener(letterButtonClickListener) }

        Toast.makeText(this, word, Toast.LENGTH_SHORT).show()

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
                    3 -> {correct += showAllVowels(textViewList, word, letterButtons)}
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

    private fun showAllVowels(textViewList : MutableList<TextView>, word : String, letterButtons: MutableList<Button>): Int {
        Toast.makeText(this, "Showing all vowels", Toast.LENGTH_SHORT).show()
        val vowels = arrayOf('A', 'E', 'I', 'O', 'U')
        var count = 0
        var revealed = 0
        for (char in word) {
            if (char in vowels) {
                textViewList[count].text = char.toString()
                // Remove the button from letterButtons
                val buttonId = resources.getIdentifier("LETTER_$char","id", packageName)
                val curButton = findViewById<Button>(buttonId)
                curButton.animate().alpha(0f).withEndAction {
                    curButton.isEnabled = false
                    letterButtons.remove(curButton)
                }
                revealed++
            }
            count++
        }
        return revealed
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

    private fun processCorrectGuess(view: View, letter: String, word: String, textViewList: MutableList<TextView>, correct: Int) {
        for ((index, char) in word.withIndex()) {
            if (char.toString() == letter) {
                textViewList[index].setText(letter)
            }
        }
        // Player completed the word
        if (correct == word.length) {
            val snackbar = Snackbar.make(view, R.string.player_win, Snackbar.LENGTH_INDEFINITE)
            snackbar.setAction("Action", View.OnClickListener {
                // Handle the action click here
                // For example, you can perform an action or dismiss the Snackbar
                snackbar.dismiss()
                initializeUI()
            })
            snackbar.show()
        }
    }


}