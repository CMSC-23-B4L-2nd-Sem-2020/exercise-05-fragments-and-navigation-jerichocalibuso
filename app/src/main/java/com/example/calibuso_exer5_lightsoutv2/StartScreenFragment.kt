package com.example.calibuso_exer5_lightsoutv2

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.calibuso_exer5_lightsoutv2.databinding.FragmentStartScreenBinding

/**
 * A simple [Fragment] subclass.
 */
class StartScreenFragment : Fragment() {
  private lateinit var binding: FragmentStartScreenBinding
  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {

    binding = DataBindingUtil.inflate<FragmentStartScreenBinding>(
      inflater,
      R.layout.fragment_start_screen, container, false
    )
    binding.nameButton.setOnClickListener { view: View ->
      handleTap(view)
    }
    return binding.root
  }

  private fun handleTap(view: View) {
    if (binding.nameText.text.toString() != "") {
      view.findNavController()
        .navigate(R.id.action_startScreenFragment_to_gameBoardFragment2)
    } else {
      Toast.makeText(
        activity,
        "Name is required. Please enter your name.",
        Toast.LENGTH_SHORT
      ).show()
    }
  }
}
