/*
* Author: Calibuso, Jericho R.
* Date finished: 2/25/20
* Program description: An implementation of Lights Out game, with a neumorphic UI design.
* */

package com.example.exer3

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.exer3.databinding.ActivityMainBinding
import android.widget.*
import androidx.databinding.DataBindingUtil
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    var tapsCount = 0
    var onCount = 25

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        binding.nameButton.setOnClickListener {
            getName(it)
        }

        binding.titleText.setOnClickListener {
            updateName()
        }

        setListeners()

    }

    private fun endGame() { //displays an exit layout if all buttons are turned off
        val titleText = binding.titleText
        val mainLayout = binding.mainLayout
        val finalLayout = binding.finalLayout
        val finalTaps = binding.tapsFinalCount

        mainLayout.visibility = View.GONE
        titleText.visibility = View.GONE
        finalLayout.visibility = View.VISIBLE

        finalTaps.text = tapsCount.toString()

        binding.retryFinalButton.setOnClickListener { // if retry button is tapped
            mainLayout.visibility = View.VISIBLE
            titleText.visibility = View.VISIBLE
            finalLayout.visibility = View.GONE

            retry()
        }

        binding.exitButton.setOnClickListener {// if exit button is tapped
            moveTaskToBack(true)
            exitProcess(-1)
        }


    }

    private fun getName(view: View) { //gets the name of the player

        val nameText = binding.nameText
        val nameImage = binding.nameImage
        val titleText = binding.titleText
        val mainLayout = binding.mainLayout
        val nameButton = binding.nameButton

        val validText = nameText.text.toString()


        if (validText.isEmpty()) { //requires input of name, shows a toast if an empty name is entered
            Toast.makeText(this, "Name is required. Please enter your name.", Toast.LENGTH_SHORT)
                .show()
        } else {
            titleText.text = nameText.text
            mainLayout.visibility = View.VISIBLE
            titleText.visibility = View.VISIBLE
            nameImage.visibility = View.GONE
            nameText.visibility = View.GONE
            nameButton.visibility = View.GONE
        }


        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)

    }

    private fun updateName() {  // allows update of name while playing

        val nameText = binding.nameText
        val nameButton = binding.nameButton
        val nameImage = binding.nameImage
        val mainLayout = binding.mainLayout
        val titleText = binding.titleText

        titleText.text = getString(R.string.title)
        titleText.visibility = View.VISIBLE
        nameText.visibility = View.VISIBLE
        nameButton.visibility = View.VISIBLE
        mainLayout.visibility = View.GONE
        nameImage.visibility = View.VISIBLE


    }


    private fun getId(
        row: Int,
        column: Int
    ): Int {  //returns button id only using the row and column
        val listOfButtons: List<List<Int>> = listOf(
            listOf(
                R.id.untapped1_1_image,
                R.id.untapped1_2_image,
                R.id.untapped1_3_image,
                R.id.untapped1_4_image,
                R.id.untapped1_5_image
            ),
            listOf(
                R.id.untapped2_1_image,
                R.id.untapped2_2_image,
                R.id.untapped2_3_image,
                R.id.untapped2_4_image,
                R.id.untapped2_5_image
            ),
            listOf(
                R.id.untapped3_1_image,
                R.id.untapped3_2_image,
                R.id.untapped3_3_image,
                R.id.untapped3_4_image,
                R.id.untapped3_5_image
            ),
            listOf(
                R.id.untapped4_1_image,
                R.id.untapped4_2_image,
                R.id.untapped4_3_image,
                R.id.untapped4_4_image,
                R.id.untapped4_5_image
            ),
            listOf(
                R.id.untapped5_1_image,
                R.id.untapped5_2_image,
                R.id.untapped5_3_image,
                R.id.untapped5_4_image,
                R.id.untapped5_5_image

            )
        )

        return listOfButtons[row][column]
    }


    private fun setListeners() {  //listens on the main layout

        val rootConstraintLayout = findViewById<View>(R.id.constraint_layout)
        val retryButton = binding.retryButton

        val clickableViews: MutableList<View> = mutableListOf(
            retryButton,
            rootConstraintLayout
        )

        for (i in 0..4) {  // adds all the button in the clickableViews
            for (j in 0..4) {
                val button = findViewById<ImageView>(getId(i, j))
                clickableViews.add(button)
            }
        }


        for (item in clickableViews) {
            if (item is ImageView) { //catches tap on constraint layout
                item.setOnClickListener { makePressed(it as ImageView) }
            }
        }

    }

    private fun makePressed(view: ImageView) {

        val tags = view.tag.toString()
            .split("-") // splits the tag with a format of "status-row-column", returned as list
        val tapStatus = tags[0]
        val row = tags[1].toInt()
        val col = tags[2].toInt()

        if (tapStatus != "retry") {
            adjacentTap(row, col)
            tapsCount++
        } else {
            retry()
        }

        val taps = binding.tapsCount
        taps.text = tapsCount.toString()

        if (onCount == 0) {  // if all buttons are turned off, game is over
            endGame()
        }

    }

    private fun retry() {  // implementation of reset
        onCount = 25
        tapsCount = 0
        val taps = binding.tapsCount
        taps.text = tapsCount.toString()

        for (i in 0..4) {
            for (j in 0..4) {
                val image = findViewById<ImageView>(getId(i, j))
                image.setImageResource(R.drawable.untapped)
                image.tag = "untapped-${i}-${j}"
            }
        }
    }


    private fun adjacentTap(
        row: Int,
        column: Int
    ) {  //main game logic, has 3 cases (inner buttons, non-corner edge buttons, and corner buttons
        if (row in 1..3 && column in 1..3) { //inner buttons
            tap(row, column)
            tap(row + 1, column)
            tap(row, column + 1)
            tap(row - 1, column)
            tap(row, column - 1)
        } else if (kotlin.math.abs(row - column) in 1..3) { //edge buttons beside corners
            if (row == 0) {
                tap(row, column)
                tap(row + 1, column)
                tap(row, column + 1)
                tap(row, column - 1)
            } else if (row == 4) {
                tap(row, column)
                tap(row, column + 1)
                tap(row - 1, column)
                tap(row, column - 1)
            } else if (column == 0) {
                tap(row, column)
                tap(row + 1, column)
                tap(row, column + 1)
                tap(row - 1, column)
            } else {
                tap(row, column)
                tap(row + 1, column)
                tap(row - 1, column)
                tap(row, column - 1)
            }
        } else { //corners
            if (row == 0 && column == 0) {
                tap(row, column)
                tap(row + 1, column)
                tap(row, column + 1)
            } else if (row == 0 && column == 4) {
                tap(row, column)
                tap(row + 1, column)
                tap(row, column - 1)
            } else if (row == 4 && column == 0) {
                tap(row, column)
                tap(row - 1, column)
                tap(row, column + 1)
            } else {
                tap(row, column)
                tap(row - 1, column)
                tap(row, column - 1)
            }
        }
    }

    private fun tap(row: Int, column: Int) { //switch the state of a button
        val button = findViewById<ImageView>(getId(row, column))
        val tags = button.tag.toString().split("-")

        if (tags[0] == "untapped") {
            button.setImageResource(R.drawable.tapped)
            button.tag = "tapped-${tags[1]}-${tags[2]}"
            onCount--

        } else {
            button.setImageResource(R.drawable.untapped)
            button.tag = "untapped-${tags[1]}-${tags[2]}"
            onCount++
        }

    }


}

