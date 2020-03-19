/**
 * Author: Calibuso, Jericho R.
 * Date finished: 3/19/20
 * Description: A refactored lights out game with fragments
 * */

package com.example.calibuso_exer5_lightsoutv2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.calibuso_exer5_lightsoutv2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }



}
