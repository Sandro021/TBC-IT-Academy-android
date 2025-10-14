package com.example.homework_9

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import com.example.homework_9.databinding.ActivityMainBinding
import android.graphics.Color
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar


class MainPageActivity : ComponentActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var activeMembers: TextView
    private lateinit var removedMembers: TextView
    private lateinit var addUserButton: Button

    private lateinit var updateUserButton: Button
    private lateinit var status: TextView

    private lateinit var statusMessageLauncher : ActivityResultLauncher<Intent>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUp()
    }


    fun setUp() {
        initialize()
        addUser()
        updateUserInfo()
        updateStatusMessage()
    }

    fun initialize() {
        with(binding) {
            activeMembers = tvActiveMembers
            removedMembers = tvDeletedMembers
            addUserButton = btAddUsers
            updateUserButton = btUpdateUser
            status = tvStatus
        }
    }
    fun updateStatusMessage() {
        statusMessageLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            result ->
            if(result.resultCode == RESULT_OK) {
                val message = result.data?.getStringExtra(getString(R.string.status_message)) ?: getString(
                    R.string.user_updated
                )
                status.text = message
                status.setTextColor(Color.GREEN)
                status.visibility = View.VISIBLE

                val count = UsersStorage.users.size
                activeMembers.text = getString(R.string.active_members, count)

                removedMembers.text = getString(R.string.removed_users, UsersStorage.removedCount)
            }
        }
    }

    fun addUser() {
        addUserButton.setOnClickListener {
            addUserMode()

        }
    }

    fun addUserMode() {
        val intent = Intent(this, AddUserActivity::class.java)
        intent.putExtra("mode" , "add")
        statusMessageLauncher.launch(intent)
    }
    fun updateUserMode() {
        val intent = Intent(this, AddUserActivity::class.java)
        intent.putExtra("mode" , "update")
        statusMessageLauncher.launch(intent)
    }



    fun updateUserInfo() {
        updateUserButton.setOnClickListener {
            if(UsersStorage.users.isEmpty()) {
                Snackbar.make(binding.root , R.string.users_do_not_exist , Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }else {
                updateUserMode()
            }
        }
    }

}

