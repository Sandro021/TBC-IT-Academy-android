package com.example.homework_5

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
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
import com.example.homework_5.databinding.ActivityMainBinding
import com.example.homework_5.ui.theme.Homework5Theme

class MainActivity : ComponentActivity() {
    private lateinit var binding : ActivityMainBinding
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val nameField = binding.etName
        val mailField = binding.etMail
        val addButton = binding.btAdduser
        val usersField = binding.tvUsers
        val checkMailField = binding.etSecondMail
        val infoButton = binding.btUserInfo
        val infoField = binding.tvUserInfo

        val info = mutableMapOf<String,String>()
        var userCount = 0

        addButton.setOnClickListener {
            val mail = mailField.text.toString()
            val name = nameField.text.toString()
            val errors = mutableListOf<String>()


            if(mail.length > 10 && mail.takeLast(10) == "@gmail.com" && !name.isEmpty()) {
                if(!info.containsKey(mail)) userCount++
                info.put(mail , name)
                mailField.text?.clear()
                nameField.text?.clear()
                usersField.text = "Users->$userCount"
            }
            if(mail.isEmpty()) errors.add("Please input valid email address")
            if(name.isEmpty()) errors.add("Please input Full name")

            if(!errors.isEmpty()) {
                Toast.makeText(this , errors.joinToString("\n"),Toast.LENGTH_SHORT).show()
            }
        }
        infoButton.setOnClickListener {
            val key = checkMailField.text.toString()

            if(key.isEmpty()) {
                Toast.makeText(this , "Please enter mail to find user" , Toast.LENGTH_SHORT).show()

            }
            if((userCount == 0 || !info.containsKey(key)) && !key.isEmpty() ) {
                infoField.visibility = View.VISIBLE
                infoField.text = "User not found"
            }
            if(info.containsKey(key)) {
                infoField.visibility = View.VISIBLE
                infoField.text = "Email: $key \n Full name: ${info[key]} "
                checkMailField.text?.clear()
            }
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
    Homework5Theme {
        Greeting("Android")
    }
}