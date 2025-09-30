package com.example.homework_4

import android.os.Bundle
import android.view.View
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
import com.example.homework_4.databinding.ActivityMainBinding
import com.example.homework_4.ui.theme.Homework4Theme

class MainActivity : ComponentActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

//        setContent {
//            Homework4Theme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                }
//            }
//        }
        val clearButton = binding.btClear
        val saveButton = binding.btSave
        val mailText = binding.etMail
        val username = binding.etUsername
        val firstname = binding.etFirstName
        val lastname = binding.etLastName
        val age = binding.etAge
        val errorMessages = mutableListOf<String>()

        saveButton.setOnClickListener {

            val str = mailText.text.toString()

            if (str.length <= 10 || str.takeLast(10) != "@gmail.com") {
                errorMessages.add("Mail is not valid")
            }
            if (username.text?.isEmpty() == true || username.length() < 10) errorMessages.add("Username is empty or too short")

            if (age.text?.isEmpty() == true) errorMessages.add("Age field is empty")

            if (firstname.text?.isEmpty() == true) errorMessages.add("Firstname field is empty")
            if (lastname.text?.isEmpty() == true) errorMessages.add("Lastname field is empty")

            if (!errorMessages.isEmpty()) {
                val error = errorMessages.joinToString("\n")
                binding.tvError.text = error
                binding.tvError.visibility = View.VISIBLE
            } else {
                binding.tvError.visibility = View.GONE
            }
        }
        clearButton.setOnLongClickListener {

            mailText.text?.clear()
            username.text?.clear()
            age.text?.clear()
            firstname.text?.clear()
            lastname.text?.clear()

            binding.tvError.text = ""
            binding.tvError.visibility = View.GONE
            errorMessages.clear()
            true
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
    Homework4Theme {
        Greeting("Android")
    }
}