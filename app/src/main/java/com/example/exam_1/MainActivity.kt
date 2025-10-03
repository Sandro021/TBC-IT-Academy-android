package com.example.exam_1

import android.annotation.SuppressLint
import android.os.Bundle
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
import com.example.exam_1.databinding.ActivityMainBinding
import com.example.exam_1.ui.theme.Exam1Theme

class MainActivity : ComponentActivity() {
    private lateinit var binding : ActivityMainBinding
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val anagramText = binding.etAnagram
        val saveButton = binding.btSave
        val outputButton = binding.btOutput
        val anagramCount = binding.tvAnagramCount
        val clearButton = binding.btClear

        var allWords = mutableListOf<String>()

        saveButton.setOnClickListener {
            val tmp = anagramText.text.toString()
            if(tmp.isEmpty()) {
                Toast.makeText(this , "Enter text to check on anagram ",Toast.LENGTH_SHORT).show()
            } else {
                val words: MutableList<String> = tmp.split("\\s+|[,;]".toRegex()).toMutableList()
                allWords = words
                anagramText.text?.clear()

            }
        }
        outputButton.setOnClickListener {
            val res = findAnagrams(allWords).size.toString()
            anagramCount.text = "Anagram-> $res"
        }
        clearButton.setOnClickListener {
            anagramCount.text = "Anagram->0"
            allWords.clear()
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
    Exam1Theme {
        Greeting("Android")
    }
}