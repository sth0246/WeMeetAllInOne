# 该项目为大三上JavaWeb大作业。分享至此以记录成长之路。

### 项目背景及主要功能：

WeMeet项目参考网站https://ome.tv/，他是一个全球性的视频交友网站。在当下全球多元文化交汇的时代背景下，越来越多的人对陌生人有着好奇心，他们把线上交友当做一个新兴的，富有乐趣的交友方式。一些如Soul、陌陌、探探的交友软件在前段时间收获的大量的用户和流量。通过广告、会员、礼物道具等方式迅速盈利。在这样的背景下，我们利用JavaWeb所学的知识开发了视频盲盒交友这样的WebApp。

WeMeet视频盲盒交友旨在为人们提供一个舒适，称心，自由的线上交友环境。用户可以通过设置自己的兴趣偏好，来随机的匹配偏好相同或相关的异性或同性。鉴于人们对陌生人可能不愿意直接露脸交流，我们细心地在每个人的视频组件上加了一层蒙布，在刚匹配到的时候，蒙布的不透明度为100%，在通过聊天之后，用户可以选择性的降低自己的蒙布比率。让人们从语音聊天开始，逐步过渡到视频交友。缓慢过度的聊天节奏可以给用户更好的体验。

### 项目主要技术栈
项目前端使用Vue+Vite+Axios+WebRTC。其中Vue编写前端页面，Vite用于构建前端，Axios用于网络请求，WebRTC与服务器获得协商信息进行P2P实时会话。
后端使用Spring+SpringMVC+Mybatis+Nodejs+Coturn+MySQL+Swagger2。其中SSM框架用于进行经典的后端处理，如登录注册等，特别的是我们对房间也使用了SSM进行管理。Nodejs主要用于搭建WebSocket服务器，对用户通话进行连接和管理。Coturn服务器完整的实现了 STUN/TURN/ICE 协议，使用NAT技术进行 P2P 穿透防火墙。主要用于 webrtc 等点对点视频音频通话。MySQL则是进行持久层管理，Swagger2用于展示接口和模型文档。![Snipaste_2023-03-27_17-04-14](images/Snipaste_2023-03-27_17-04-14.png)

### 各种sdk版本

![Snipaste_2023-03-27_17-05-47](images/Snipaste_2023-03-27_17-05-47.png)
