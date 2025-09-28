package com.example.homework_3

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.ToggleButton
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
import com.example.homework_3.ui.theme.Homework3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
//        setContent {
//            Homework3Theme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                }
//            }
//        }
        val button = findViewById<Button>(R.id.btPrint)
        val editText = findViewById<EditText>(R.id.etNumber)
        val textView = findViewById<TextView>(R.id.twRes)
        val switchButton = findViewById<ToggleButton>(R.id.langButton)

        switchButton.setOnCheckedChangeListener { _, _  ->
            editText.text.clear()
            textView.text = ""
        }
        button.setOnClickListener {
            val result = editText.text.toString()
            var number = 0
            if(result.isEmpty()) {
                textView.text = "Empty input"
            }else {
                number = result.toInt()
            }

            if(switchButton.isChecked) {
                if(result.length > 3 && result != "1000") {
                    textView.text = "Number too Large"
                }

                textView.text = writeWithWords(number)
            } else {
                if(result.length > 3 && result != "1000") {
                    textView.text = "Number too Large"
                }

                textView.text = writeWithWordsEng(number)
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
    Homework3Theme {
        Greeting("Android")
    }
}