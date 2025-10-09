package com.example.homework_7

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.homework_7.databinding.LoggedoutBinding
import com.example.homework_7.databinding.RegisterFirstBinding
import com.example.homework_7.ui.theme.Homework7Theme

class MainActivity : ComponentActivity() {
    private lateinit var binding : LoggedoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = LoggedoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        goToLoginScreen()
        goToRegister()
    }
    fun goToLoginScreen() {
        binding.btlogin.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
    }
    fun goToRegister() {
        binding.btRegister.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }
    }
}




@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Homework7Theme {
        Greeting("Android")
    }
}