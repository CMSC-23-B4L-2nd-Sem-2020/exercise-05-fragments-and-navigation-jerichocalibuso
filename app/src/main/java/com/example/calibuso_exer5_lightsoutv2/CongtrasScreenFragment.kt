package com.example.calibuso_exer5_lightsoutv2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.calibuso_exer5_lightsoutv2.databinding.FragmentCongratsScreenBinding

/**
 * Author: Calibuso, Jericho R.
 * Date finished: 3/19/20
 * Description: A refactored lights out game with fragments
 * */

class CongtrasScreenFragment : Fragment() {

  private lateinit var binding: FragmentCongratsScreenBinding
  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {

    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_congrats_screen, container, false)
    binding.retryButton.setOnClickListener { view: View ->
      handleTap(view)
    }

    //set tapsCount from gameBoard
    binding.apply{
      tapsCount.text = arguments?.getString("count")
    }
    return binding.root
  }

  private fun handleTap(view: View) {
    view.findNavController()
      .navigate(R.id.action_congtrasScreenFragment_to_startScreenFragment)
  }

}
