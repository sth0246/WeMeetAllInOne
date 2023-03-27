<template>
       <div class="content">
        <video class="chat" autoplay playsinline ref="localVideo" controls id="local-video"></video>
        <li v-for="(item, index) in biPeersList" :key="index">
            <h4>{{ item.other.username }}
                <button type="primary" @click="stopInteract(item, index)" style="float:right;">结束互动</button>
            </h4>
            <video class="chat" autoplay playsinline controls :ref="'remoteVideo' + item.other.userId"
                :id="'remoteVideo' + item.other.userId"></video>
        </li>
        <button class="match" @click="rand">开始匹配</button>
        <button class="initRoom" @click="initRoom">初始化</button>
    </div>
</template>

<script>
import axios from 'axios'
export default {
    name: 'VideoChat',
    data() {
        return {
            pcConfig: {
                'iceServers': [
                    {
                        'url': 'stun:stun.l.google.com:19302'
                    },
                    {
                        'url': 'turn:47.92.143.63:3478',
                        'username': 'sth',
                        'credential': 'Sth1137506914.'
                    }
                ]
            },
            offerOptions: {
                offerToReceiveVideo: 1,
                offerToReceiveAudio: 1
            },
            biPeersList: [],
            peerList: Object.create(null),
            pc: null,
            localPc1: null,
            localPc2: null,
            pcMsgTo: {},
            remotePc: null,
            isStarted: false,
            isTeacher: true,
            isReady: false,
            sendingMsg: '',
            activeTab: 'chatTab',
            onlineClients: [],
            webrtc: null,
            socket: null,
            messages: [],
            localVideo: null,
            remoteVideo1: null,
            remoteVideo2: null,
            remoteVideo3: null,
            localStream: null,
            remoteStream: null,
            socketid: null,
            remoteStreamNum: 0,
            mediaStreamConstraints: {
                video: true,
                audio: true
            },
            form: {
                username: '111',
                password: 0,
                roomid: 0
            }
        }
    },
    methods: {
        getStatus(item) {
            return !!this.peerList[item.userId]
        },
        // creates local MediaStream.
        startAction(callback) {
            navigator.mediaDevices.getUserMedia(this.mediaStreamConstraints).then((stream) => {
                console.log("suceefuly open")
                this.gotLocalMediaStream(stream, callback)
            }).catch(this.handleLocalMediaStreamError)
        },
        gotLocalMediaStream(stream, callback) {
            this.localVideo = this.$refs.localVideo
            this.localVideo.srcObject = stream
            this.localStream = stream
            window.localStream = stream
            if (callback && typeof callback === 'function') {
                callback && callback()
            }
            this.trace('Received local stream.')
        },
        handleRemoteMediaStreamAdded(pc, event) {
            pc.remoteStream = event.stream
            let remoteVideo = this.$refs['remoteVideo' + pc.other.userId][0]
            remoteVideo.srcObject = event.stream
            remoteVideo.addEventListener('loadedmetadata', () => {
                remoteVideo.play()
            })
            console.log('biPeersList', this.biPeersList)
            this.remoteStream = event.stream
            this.trace('Received remote stream from ' + pc.other.username)
        },
        handleLocalMediaStreamError() {
            console.log('本地视频采集出错')
        },
        handleRemoteStreamRemoved(pc, event) {
            this.trace('Remote stream  removed event', pc.other.username)
        },
        sendPcMessage(PcMessage) {

            let from = { userId: this.socket.id, username: this.form.username }
            let to = this.pcMsgTo
            this.socket.emit('pc message', { from, to, pcMsg: PcMessage })
        },
        // A和B建立连接，A和C建立连接，收到的B和C的消息需要进行区分
        signalingMessageCallback(message) {
            let otherId = message.from.userId // 对方的id
            let pc = this.peerList[otherId]
            message = message.pcMsg
            if (message.type === 'offer') {
                console.log('signalingMessageCallback offer', message)
                pc.setRemoteDescription(new RTCSessionDescription(message)).then(() => {
                    pc.createAnswer()
                        .then((description) => this.createdAnswerSuccess(pc, description))
                        .catch(this.setSessionDescriptionError)
                }).catch(this.logError)
            } else if (message.type === 'answer') {
                console.log('收到了answer')
                console.log('pc', pc)
                pc.setRemoteDescription(new RTCSessionDescription(message), function () {
                }, this.logError)
            } else if (message.type === 'candidate') {
                let candidate = new RTCIceCandidate({
                    sdpMLineIndex: message.label,
                    candidate: message.candidate
                })
                pc.addIceCandidate(candidate).catch(err => {
                    console.log('addIceCandidate-error', err)
                })
            }
        },
        createdAnswerSuccess(pc, description) {
            pc.setLocalDescription(description).then(() => {
                this.sendPcMessage(pc.localDescription)
                this.setLocalDescriptionSuccess(description, 'answer')
                this.trace('local answer psd set.')
            }).catch(this.setSessionDescriptionError)
        },
        // 创建对等连接



        // 此处的data对象内有两个属性
        // {from: {username: this.form.username, userId: this.socket.id}, to: user}
        // to中的user为clientList中获取到的user信息，格式与from中的用户信息相同
        // 在移植过程中我们可以使用java服务器得到对方的socket.id和username然后通过
        // createPeerConnection函数创建p2p连接


        createPeerConnection(isCreatedOffer, data) {
            let other = isCreatedOffer ? data.to : data.from // 对方
            if (!this.peerList[other.userId]) {
                let pc = new RTCPeerConnection(this.pcConfig)
                pc.from = data.from
                pc.to = data.to
                pc.isSelf = isCreatedOffer // 是否  是自己发起
                pc.other = isCreatedOffer ? data.to : data.from // 对方
                this.peerList[other.userId] = pc
                this.biPeersList.push(pc)
                this.createConnect(isCreatedOffer, pc)
            }
        },
        createConnect(isCreatedOffer, pc) {
            pc.addEventListener('icecandidate', event => {
                console.log('icecandidate event:', event)
                if (event.candidate) {
                    this.sendPcMessage({
                        type: 'candidate',
                        label: event.candidate.sdpMLineIndex,
                        id: event.candidate.sdpMid,
                        candidate: event.candidate.candidate
                    })
                } else {
                    console.log('End of candidates.')
                }
            })
            if (this.localStream) {
                pc.addStream((this.localStream))
            } else {
                this.startAction(this.addStreamToLocalPc(pc))
            }
            pc.addEventListener('addstream', (event) => {
                console.log('addstream')
                this.handleRemoteMediaStreamAdded(pc, event)
            })
            pc.addEventListener('removestream', (event) => {
                return this.handleRemoteStreamRemoved(pc, event)
            })
            // 创建offer,生成本地会话描述,如果是视频接收方，不需要生成offer
            if (isCreatedOffer) {
                pc.createOffer(this.offerOptions).then((description) => this.createdOfferSuccess(pc, description)).catch(this.logError)
            }
        },
        addStreamToLocalPc(pc) {
            return () => {
                pc.addStream((this.localStream))
            }
        },
        // 创建offer,生成本地会话描述
        createdOfferSuccess(pc, description) {
            // 用sd生成localPc的本地描述，remotePc的远程描述
            pc.setLocalDescription(description)
                .then(() => {
                    this.sendPcMessage(pc.localDescription)
                    this.setLocalDescriptionSuccess(description, 'offer')
                    this.trace('local offer psd set.')
                }).catch(this.setSessionDescriptionError)
        },
        // 本地会话描述设置成功
        setLocalDescriptionSuccess(desc, type = 'offer') {
            // console.log('local desc', desc);
            // this.trace('setLocalDescription' + type + 'success', desc);
        },
        // 会话描述设置失败
        setSessionDescriptionError(err) {
            this.trace('set session Description error', err)
        },
        logError(err) {
            if (!err) return
            if (typeof err === 'string') {
                console.warn(err)
            } else {
                console.warn(err.toString(), err)
            }
        },
        // 开始互动
        interact(user) {
            // 开启互动之前,需要先开启视频采集
            if (!this.localStream) {
                this.startAction(() => {
                    this.socket.emit('interact', { from: { username: this.form.username, userId: this.socket.id }, to: user })
                    this.trace(`${this.form.username}向${user.username}发起了视频互动的请求`)
                })
            } else {
                this.socket.emit('interact', { from: { username: this.form.username, userId: this.socket.id }, to: user })
                this.trace(`${this.form.username}向${user.username}发起了视频互动的请求`)
            }
        },
        // 禁言
        forbidTalk() {

        },

        rand() {
            axios.post('/api/selectStatus?status=1').then(res => {
                // 无任何等待房间
                console.log("data.lenth  : " ,res.data.length)
                if (res.data.length <= 2) {
                    axios.post('/api/selectStatus?status=0').then(res=>{
                        this.form.roomid = res.data[0].roomId                        
                        console.log("v.form   : ",this.form)
                        this.joinRoom(true)
                    })
                }else{
                    let tmp={username:res.data[0].userP1,userId:res.data[0].socketIdP1}
                    this.form.roomid = res.data[0].roomId                        
                    console.log("v.form   : ",this.form)
                    this.joinRoom(false)
                    this.interact(tmp)

                }
            }
            ).catch(err => {
                // err 是请求失败 返回的结果
                console.log(err)
            })
            // let len = this.onlineClients.length
            // console.log(len)
            // let rInt = Math.floor(Math.random() * len) + 0
            // console.log(this.onlineClients[rInt].userId)
            // this.interact(this.onlineClients[rInt])
        },
        // 发送消息
        handleSendMesssage() {

            if (!this.sendingMsg) {
                return
            }
            let data = { msg: this.sendingMsg, username: this.form.username }
            this.socket.emit('message', data)
            this.updateChatMessage(data)
            this.sendingMsg = ''
        },
        tabClick() {
        },
        updateChatMessage(data) {
            this.messages.push(data)
        },
        leaveRoom() {
            this.socket.emit('leave')
        },
        joinRoom(p1) {
            //let url = 'wss://192.168.0.147:9000'
            let url = 'http://localhost:9000'
            //let url = 'wss://47.92.143.63:9000'
            this.startAction()
            

            if (!this.form.username) {
                this.$message('请输入用户名')
                return
            }
            if (this.onlineClients.some(v => v.username === this.form.username)) {
                this.$message('正在匹配中')
                return
            }
            let v = this
            this.socket = io.connect(url, { query: { username: this.form.username, room: this.form.roomid } })
            

            // 其他用户加入聊天室

            this.socket.on('join', (data) => {
                this.updateChatMessage({ msg: data.username + '加入了聊天室', type: 'sys' })
            })
            console.log("====================================")
            this.socket.on('socketid', (re) => {
                this.socketid = re; console.log(re)
                if(p1){
                    axios.post('/api/updateP1?roomId='+this.form.roomid+
                        '&userP1='+this.form.username+'&socketIdP1='+re)
                        .then(re=>console.log(re)).catch(e=>console.log(e))
                }else{
                    axios.post('/api/updateP2?roomId='+this.form.roomid+
                        '&userP2='+this.form.username+'&socketIdP2='+re)
                        .then(re=>console.log(re)).catch(e=>console.log(e))
                }
                console.log("-----------------------  ")
            })

            
            
            // 自己加入成功
            this.socket.on('joined', () => {
                console.log('i joined th room')
                // this.updateChatMessage();
            })
            this.socket.on('ready', () => {
                this.isReady = true
            })
            // 自己离开了
            this.socket.on('left', () => {
                this.socket.disconnect()
                this.messages = []
                this.onlineClients = []
            })
            // 别人离开了
            this.socket.on('leave', data => {
                this.updateChatMessage({ msg: data.username + '离开了聊天室', type: 'sys' })
                if (this.biPeersList[data.userId]) {
                    this.biPeersList[data.userId].close()
                    delete this.biPeersList[data.userId]
                }
            })
            // 更新在线人数列表
            this.socket.on('clients', (data) => {
                console.log('clients', data)
                v.onlineClients = data
            })
            this.socket.on('pc message', (data) => {
                this.trace('客户端收到了pc的消息', data)
                this.signalingMessageCallback(data)
            })
            // 收到别人发的聊天信息
            this.socket.on('message', data => {
                this.updateChatMessage(data)
            })
            // 收到别人的要求视频互动的私信
            this.socket.on('interact', data => {
                // console.log(data)
                // this.$confirm(`${data.from.username}想和你视频互动，请接受`, '提示信息', {
                //   distinguishCancelAndClose: true,
                //   confirmButtonText: '接受',
                //   cancelButtonText: '拒绝'
                // })
                //   .then(() => {
                // 同意和对方互动, 对方发起，自己接受
                this.socket.emit('agree interact', data)
                this.pcMsgTo = data.from
                console.log('正在建立p2p')
                this.createPeerConnection(false, data)
                // })
                // .catch(() => {
                //   // 拒绝和对方互动
                //   this.socket.emit('refuse interact', data)
                // })
            })
            // 对方同意了了和你视频互动，自己发起，对方接受
            this.socket.on('agree interact', data => {
                this.$message({ type: 'success', message: `${data.to.username}接受了视频互动的请求` })
                this.pcMsgTo = data.to
                this.trace(`${data.to.username}接受了视频互动的请求`)
                this.createPeerConnection(true, data)
            })
            // 对方拒绝了和你视频互动
            this.socket.on('refuse interact', data => {
                this.$message({ type: 'warning', message: `${data.to.username}拒绝了视频互动的请求` })
                this.trace(`${data.to.username}拒绝了视频互动的请求`)
            })
            // 监听到对方结束互动
            this.socket.on('stop interact', data => {
                let part = data.from
                this.$message({ type: 'info', message: `${part.username}停止了和您互动，连接即将断开`, duration: 1500 })
                console.log('this.biPeersList', this.biPeersList)
                this.peerList[data.from.userId].close()
                this.peerList[data.from.userId] = null
                let index = this.biPeersList.findIndex(v => v.other.userId === part.userId)
                if (index > -1) {
                    this.biPeersList[index].close()
                    this.biPeersList.splice(index, 1)
                }
            })
        },
        stopInteract(item, index) {
            this.socket.emit('stop interact', { from: { username: this.form.username, userId: this.socket.id }, to: item.other })
            this.biPeersList.splice(index, 1)
            this.peerList[item.other.userId].close()
            this.peerList[item.other.userId] = null
        },
        init() {
            this.localVideo = this.$refs.localVideo
        },
        trace(text, data = '') {
            text = text.trim()
            const now = (window.performance.now() / 1000).toFixed(3)
            console.log(now, text, data)
        },
        closeRoom(){
            axios.post('/api/initRoom?roomId='+this.form.roomid)
    
        },
        async initRoom(){
            await this.closeRoom()
            window.close()

           // axios.post('/api/initRoom?roomId='+this.form.roomid)
        }
    },
    beforeDestroy() {
        alert("wait0")
        this.biPeersList = []
        for (let k in this.peerList) {
            this.peerList[k].close()
            this.peerList[k] = null
        }
        
        

    },
   

    created() {
       
    },

    mounted() {
        window.addEventListener('beforeunload', e => this.initRoom(e))
        window.addEventListener('unload', e => this.initRoom(e))
        this.init()
    },
    destroyed(){
        window.removeEventListener('beforeunload', e => this.initRoom(e))
        window.addEventListener('unload', e => this.initRoom(e))
    }

}


</script>   

<style scoped>
    .content{
        display: flex;
        justify-content: center;
        margin: 0 auto;
        margin-top: 28px;
    }
    .chat{
        width: 228px;
        height: 228px;
        border-radius: 6px;
        background-color: #FBE9E7;
    }
    .chat:first-child{
        margin-right: 56px;
    }
    .match,
    .initRoom{
        position: absolute;
        height: 32px;
        width: 84px;
        top: 299px;
        left: 15%;
        border-radius: 2px;
        font-size: 10px;
        color: #ECEFF1;
        border: none;
        background-color: #FF7043;
    }
    .initRoom{
        left:28%;
    }
    .match:hover,
    .initRoom:hover{
        cursor: pointer;
        color: #FFF176;
        background-color: #FFAB91;
    }
</style>