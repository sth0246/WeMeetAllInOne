var express = require('express')
var fs = require('fs');
var app = express()
var https = require('https');
var request = require('request');


var path = require('path')
console.log(options)


var keypath = process.cwd() + '\\build\\cert\\private.key'; //我把秘钥文件放在运行命令的目录下测试
var certpath = process.cwd() + '\\build\\cert\\file.crt'; //console.log(keypath);
//

var options = {
    key: fs.readFileSync(keypath),
    cert: fs.readFileSync(certpath)
}

// 使用https
//var server = https.createServer(options, app);

// 使用http
var server = require('http').Server(app)

var io = require('socket.io')(server)






// app.use(express.static('dist'))
// app.get('/', function(req, res) {
//     res.sendFile(path.resolve(__dirname, './index.html'))
// })
let rooms = []
let clients = []
io.on('connection', function(socket) {
    let query = socket.handshake.query
        // 前端传进来的用户名和房间号
    let username = query.username
    let room = query.room
    console.log(username + '连接了')
    if (clients.some(v => v.userId === socket.id)) {
        return
    }
    // 进入这个room房间
    socket.join(room)
    rooms.push(room)
    console.log(rooms)


    socket.emit('socketid', socket.id)
    console.log("socket.id", socket.id)
        // 已经进入的用户名socket.id和用户名
    clients.push({ userId: socket.id, username, roomId: room })
    console.log(clients)
        // 房间内有大于等于两个的客户时才可以开启采集视频
    if (clients.length >= 2) {
        io.sockets.in(room).emit('ready')
    }
    // 向自己发送加入成功信息 ，，，如果这个socket不加in（room）
    // 或者不加broadcast时就是向自己的socket连接发送信息
    socket.emit('joined')
        // 广播房间内所有人 username加入了房间
    socket.broadcast.to(room).emit('join', { username })
        // 向房间内所有人发送在线人信息
    io.sockets.in(room).emit('clients', clients)

    socket.on('message', function(data) {
            socket.broadcast.to(room).emit('message', data)
        })
        // 收到对等连接创建的消息
    socket.on('pc message', function(data) {
            socket.to(data.to.userId).emit('pc message', data)
                // socket.broadcast.to(room).emit('pc message', data);
        })
        // 发私信,发起视频互动的请求
    socket.on('interact', function(data) {
            socket.to(data.to.userId).emit('interact', data)
        })
        // 对方同意视频互动
    socket.on('agree interact', function(data) {
        socket.to(data.from.userId).emit('agree interact', data)
            // 更新互动的状态
            // clients = clients.map(v => {
            //   if (v.userId === data.from.userId || v.userId === data.to.userId) {
            //     return {...v, isLive: true}
            //   } else {
            //     return v;
            //   }
            // })
            // io.sockets.in(room).emit('clients',clients);
    })
    socket.on('refuse interact', function(data) {
        socket.to(data.from.userId).emit('refuse interact', data)
    })
    socket.on('stop interact', function(data) {
        socket.to(data.to.userId).emit('stop interact', data)
            // // 更新互动的状态
            // clients = clients.map(v => {
            //   if (v.userId === data.from.userId || v.userId === data.to.userId) {
            //     return {...v, isLive: true}
            //   } else {
            //     return v;
            //   }
            // })
            // io.sockets.in(room).emit('clients',clients);
    })
    socket.on('leave', function(data) {
        socket.emit('left')
        socket.broadcast.to(room).emit('leave', { userId: socket.id, username })
        clients = clients.filter(v => v.userId !== socket.id)
        io.sockets.in(room).emit('clients', clients)
    })
    socket.on('disconnect', function() {
        console.log(username + '断开连接了')
        console.log(room + "房间号")
        request.post(
            'https://127.0.0.1:8443/WeMeet/room/initRoom?roomId=' + room,
            function(error, response, body) {
                if (!error && response.statusCode == 200) {
                    console.log(body)
                } else {
                    console.log(error)
                }
            }
        );
        clients = clients.filter(v => v.userId !== socket.id)
        io.sockets.in(room).emit('clients', clients)
    })
})
server.listen(9000, function() {
    console.log('app is listening to 9000')
})