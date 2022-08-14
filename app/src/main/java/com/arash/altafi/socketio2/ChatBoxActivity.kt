package com.arash.altafi.socketio2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.arash.altafi.socketio2.adapter.ChatAdapter
import com.arash.altafi.socketio2.model.Message
import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONException
import org.json.JSONObject
import java.net.URISyntaxException

class ChatBoxActivity : AppCompatActivity() {

    private var socket: Socket? = null
    private lateinit var nickname: String
    private lateinit var rvConversation: ListView
    private lateinit var edtMessage: EditText
    private lateinit var btSend: Button
    private var messageList: ArrayList<Message> = ArrayList()
    private lateinit var chatAdapter: ChatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_box)

        init()
    }

    private fun init() {
        edtMessage = findViewById(R.id.edtMessage)
        btSend = findViewById(R.id.btSend)
        rvConversation = findViewById(R.id.messageList)

        chatAdapter = ChatAdapter(messageList)
        rvConversation.adapter = chatAdapter

        nickname = intent.extras!!.getString(MainActivity.NICKNAME)!!

        try {
            socket = IO.socket("http://192.168.1.100:3000")
            socket?.connect()

            socket?.emit("join", nickname)
        } catch (e: URISyntaxException) {
            e.printStackTrace()
        }

        btSend.setOnClickListener {
            if (edtMessage.text.toString().isNotEmpty()) {
                socket?.emit(
                    "messagedetection",
                    nickname,
                    edtMessage.text.toString().trim { it <= ' ' })
                edtMessage.setText("")
            }
        }

        socket?.on("userjoinedthechat") { args ->
            runOnUiThread {
                val data = args[0] as String
                Toast.makeText(this@ChatBoxActivity, data, Toast.LENGTH_LONG).show()
            }
        }

        socket?.on("userdisconnect") { args ->
            runOnUiThread {
                val data = args[0] as String
                Toast.makeText(this@ChatBoxActivity, data, Toast.LENGTH_LONG).show()
            }
        }

        socket?.on("message") { args ->
            runOnUiThread {
                val data = args[0] as JSONObject
                try {
                    val nickname = data.getString("senderNickname")
                    val message = data.getString("message")
                    val messageTxt = Message(nickname, message)
                    messageList.add(messageTxt)
                    chatAdapter.notifyDataSetChanged()
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        }
    }

}