<template>
  <div class="container">
    <div class="bg"></div>
    <div class="content">
      <div class="navbar">
        <NavBar />
      </div>
      <VideoChatVue />
    </div>
  </div>
  <router-view></router-view>
</template>

<script setup>
import NavBar from './NavBar.vue';
import VideoChatVue from './VideoChat.vue';
import { useUserStore } from '@/store/userStore';
const userStore = useUserStore();

(() => {
  if (JSON.stringify(sessionStorage) != "{}") {
    let user = sessionStorage.getItem('user')
    let token = sessionStorage.getItem('token')
    user = JSON.parse(user)
    userStore.updatUser(user)
    userStore.updateToken(token)
  }
})()
</script>

<style scoped>
.container {
  width: 100%;
  height: 100%;
}

.bg {
  position: absolute;
  z-index: -1;
  width: 100%;
  height: 100%;
  background-image: url("https://w.wallhaven.cc/full/qz/wallhaven-qz22lq.jpg");
  background-position: center;
  background-size: contain;
  filter: blur(8px) brightness(0.6);
}
</style>