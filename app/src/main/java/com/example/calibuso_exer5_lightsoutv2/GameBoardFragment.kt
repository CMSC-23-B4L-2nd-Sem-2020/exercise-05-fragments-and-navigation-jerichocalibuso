/**
 * Author: Calibuso, Jericho R.
 * Date finished: 3/19/20
 * Description: A refactored lights out game with fragments
 * */

package com.example.calibuso_exer5_lightsoutv2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.calibuso_exer5_lightsoutv2.databinding.FragmentGameBoardBinding


/**
 * A simple [Fragment] subclass.
 */
class GameBoardFragment : Fragment() {

    var tapsCount = 0
    var onCount = 25

    private lateinit var binding: FragmentGameBoardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_game_board, container, false)

      setListeners()
      return binding.root
    }


  private fun setListeners() {  //listens on the main layout

        val rootGameBoardLayout = binding.rootGameBoardLayout
        val retryButton = binding.retryButton

        val clickableViews: MutableList<View> = mutableListOf(
            retryButton,
            rootGameBoardLayout)

        for (i in 0..4){  // adds all the button in the clickableViews
            for (j in 0..4){
                val button = getButton(i,j)
                clickableViews.add(button)
            }
        }


        for (item in clickableViews) {
            if (item is ImageView) { //catches tap on constraint layout
                item.setOnClickListener { play(it as ImageView) }
            }
        }

    }
   private fun play(view: ImageView) {

        val tags = view.tag.toString().split("-") // splits the tag with a format of "status-row-column", returned as list
        val tapStatus = tags[0]
        val row = tags[1].toInt()
        val col = tags[2].toInt()

        if (tapStatus != "retry"){
            adjacentTap(row, col)
            tapsCount++
        }else{
            retry()
        }

        val taps = binding.tapsCount
        taps.text = tapsCount.toString()

        if (onCount == 0){  // if all buttons are turned off, game is over
            view.findNavController()
        .navigate(R.id.action_gameBoardFragment2_to_congtrasScreenFragment)
        }

    }

   private fun adjacentTap(row:Int, column:Int) {  //main game logic, has 3 cases (inner buttons, non-corner edge buttons, and corner buttons
        if (row in 1..3 && column in 1..3){ //inner buttons
            tap(row, column)
            tap(row+1, column)
            tap(row, column+1)
            tap(row-1, column)
            tap(row, column-1)
        }else if (kotlin.math.abs(row - column) in 1..3){ //edge buttons beside corners
            if (row == 0){
                tap(row, column)
                tap(row+1, column)
                tap(row, column+1)
                tap(row, column-1)
            }else if (row == 4){
                tap(row, column)
                tap(row, column+1)
                tap(row-1, column)
                tap(row, column-1)
            }else if (column == 0){
                tap(row, column)
                tap(row+1, column)
                tap(row, column+1)
                tap(row-1, column)
            }else{
                tap(row, column)
                tap(row+1, column)
                tap(row-1, column)
                tap(row, column-1)
            }
        }else{ //corners
            if (row == 0 && column == 0){
                tap(row, column)
                tap(row+1, column)
                tap(row, column+1)
            }else if (row == 0 && column == 4){
                tap(row, column)
                tap(row+1, column)
                tap(row, column-1)
            }else if (row == 4 && column == 0){
                tap(row, column)
                tap(row-1, column)
                tap(row, column+1)
            }else{
                tap(row, column)
                tap(row-1, column)
                tap(row, column-1)
            }
        }
    }

    private fun tap(row: Int, column: Int) { //switch the state of a button
        val button = getButton(row, column)
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

  private fun getButton(row:Int, column:Int): ImageView {  //returns button id only using the row and column
        val listOfButtons: List<List<ImageView>> = listOf(
            listOf( binding.untapped11Image,
                binding.untapped12Image,
                binding.untapped13Image,
                binding.untapped14Image,
                binding.untapped15Image),
            listOf(binding.untapped21Image,
                binding.untapped22Image,
                binding.untapped23Image,
                binding.untapped24Image,
                binding.untapped25Image),
            listOf(binding.untapped31Image,
                binding.untapped32Image,
                binding.untapped33Image,
                binding.untapped34Image,
                binding.untapped35Image),
            listOf(binding.untapped41Image,
                binding.untapped42Image,
                binding.untapped43Image,
                binding.untapped44Image,
                binding.untapped45Image),
            listOf(binding.untapped51Image,
                binding.untapped52Image,
                binding.untapped53Image,
                binding.untapped54Image,
                binding.untapped55Image

            ))

        return listOfButtons[row][column]
    }


  private fun retry(){  // implementation of reset
        onCount = 25
        tapsCount = 0
        val taps = binding.tapsCount
        taps.text = tapsCount.toString()

        for (i in 0..4){
            for (j in 0..4){
                val image = getButton(i,j)
                image.setImageResource(R.drawable.untapped)
                image.tag = "untapped-${i}-${j}"
            }
        }
    }
}
