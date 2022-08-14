package com.arash.altafi.socketio2

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {

    companion object {
        const val NICKNAME = "usernickname"
    }

    private lateinit var edtNickname: EditText
    private lateinit var btnChat: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init() {
        btnChat = findViewById(R.id.btnChat)
        edtNickname = findViewById(R.id.edtNickname)


        btnChat.setOnClickListener {
            if (edtNickname.text.toString().isNotEmpty()) {
                val intent = Intent(this@MainActivity, ChatBoxActivity::class.java)
                intent.putExtra(
                    NICKNAME,
                    edtNickname.text.toString().trim { it <= ' ' })
                startActivity(intent)
            }
        }

    }

}