package com.example.strawhatshangman

import android.annotation.SuppressLint
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private val letterButtons: MutableList<Button> = mutableListOf()
    private val wordBank: Set<Pair<String, String>> = setOf(
        "fruit" to "apple",
        "fruit" to "banana",
        "fruit" to "orange",
        "fruit" to "mango",
        "drinks" to "pepsi",
        "drinks" to "sprite",
        "drinks" to "fanta",
        "drinks" to "coke",
        "food" to "pizza",
        "food" to "hotdog",
        "food" to "burger",
        "food" to "sandwich",
        "cars" to "audi",
        "cars" to "toyota",
        "cars" to "mercedes",
        "cars" to "lexus"
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

        val randomElement: Pair<String, String> = wordBank.toList().random()

        // Use this variable to reveal when user wants hint
        val category : String = randomElement.first

        // Use this variable to compare with user input(button clicks)
        val word : String = randomElement.second


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
//            textView.visibility = View.INVISIBLE
            textView.setBackgroundResource(R.drawable.bottom_border)

            val wordReveal : LinearLayout = findViewById(R.id.word_reveal)


            // Add the TextView to layout

            wordReveal.addView(textView)
        }



    }


}