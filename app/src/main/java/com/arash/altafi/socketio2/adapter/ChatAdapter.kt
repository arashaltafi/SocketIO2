package com.arash.altafi.socketio2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.arash.altafi.socketio2.R
import com.arash.altafi.socketio2.model.Message
import com.google.android.material.textview.MaterialTextView

class ChatAdapter(private val lista: List<Message>) : BaseAdapter() {
    override fun getCount(): Int {
        return lista.size
    }

    override fun getItem(i: Int): Message {
        return lista[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View? {
        val inflater = LayoutInflater.from(viewGroup.context)
        var vista = view
        if (vista == null) vista = inflater.inflate(R.layout.item_chat, viewGroup, false)
        (vista?.findViewById<MaterialTextView>(R.id.txtNickname))?.text = getItem(i).nickname
        (vista?.findViewById<MaterialTextView>(R.id.txtMessage))?.text = getItem(i).message
        return vista
    }
}
