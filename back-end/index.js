const express = require('express');
const http = require('http');
const app = express();
const server = http.createServer(app);
const io = require('socket.io').listen(server);

//Peticion por default
app.get('/', (request, response) => {
    response.send('Chat server is running on port 3000');
});

//Cuando se conecta al socket
io.on('connection', socket => {
    console.log('User connected');

    //Cuando se une a la conversacion
    socket.on('join', nickname => {
        console.log(`${nickname} has joined the chat `);

        //Envia  el evento a todos los usuarios conectados al servidor, excepto al remitente (si se quiere incluir hay que usar io.emit())
        socket.broadcast.emit('userjoinedthechat', nickname + " : has joined the chat ");
    });

    //Para manejar los mensajes del chat
    socket.on('messagedetection', (senderNickname, messageContent) => {
        console.log(senderNickname + ": " + messageContent);

        //Crea un objeto del mensaje
        let message = {"message": messageContent, "senderNickname": senderNickname};

        //Envia el mensaje a todos los usuarios incluyendo al remitente usando io.emit()
        io.emit('message', message);
    });

    //Cuando el usuario se desconecta
    socket.on('disconnect', () => {
        console.log('User has left ');

        socket.broadcast.emit('userdisconnect', ' user has left');
    });
});

//Inicia el servidor
server.listen(3000, () => {
    console.log('Node app is running on port 3000');
});