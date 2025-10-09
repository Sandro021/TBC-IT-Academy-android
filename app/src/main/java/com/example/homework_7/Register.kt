package com.example.homework_7

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import com.example.homework_7.databinding.LoginBinding
import com.example.homework_7.databinding.RegisterFirstBinding

class Register : ComponentActivity() {
    private lateinit var binding : RegisterFirstBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = RegisterFirstBinding.inflate(layoutInflater)
        setContentView(binding.root)
        nextPage()
        goBack()
    }
    fun nextPage() {
        binding.btnext.setOnClickListener {
            val intent = Intent(this, Registersecond::class.java)
            startActivity(intent)
        }
    }
    fun goBack() {
        binding.backArrow.setOnClickListener {
            val intent = Intent(this , MainActivity::class.java )
            startActivity(intent)
        }
    }

}