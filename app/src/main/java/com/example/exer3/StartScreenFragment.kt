package com.example.exer3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.example.exer3.databinding.FragmentStartScreenBinding

/**
 * A simple [Fragment] subclass.
 */
class StartScreenFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentStartScreenBinding = DataBindingUtil.inflate(inflater,
           R.layout.fragment_start_screen,container,false)
        return binding.root
    }

}
