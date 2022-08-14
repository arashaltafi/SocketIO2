package com.arash.altafi.socketio2.model

class Message(var nickname: String?, var message: String?) {

    override fun toString(): String {
        return String.format("Nickname: %s, Mensaje: %s", nickname, message)
    }
}

