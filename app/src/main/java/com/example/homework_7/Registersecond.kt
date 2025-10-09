package com.example.homework_7

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import com.example.homework_7.databinding.RegisterFirstBinding
import com.example.homework_7.databinding.RegisterSecBinding

class Registersecond : ComponentActivity() {
    private lateinit var binding : RegisterSecBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = RegisterSecBinding.inflate(layoutInflater)
        setContentView(binding.root)
        goBack()
    }
    fun goBack() {
        binding.backArrow.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }
    }
}