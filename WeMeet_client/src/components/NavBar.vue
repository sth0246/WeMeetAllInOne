<template>
    <div class="nav">
        <div @click="">视频盲盒</div>
        <div @click="searchRoom">搜寻房间</div>
        <div @click="toFriendsList">好友</div>
        <div @click="toProfile">个人信息</div>
        <div>
            <span class="login" @click="login">{{ userStore.user != null ? userStore.user.username : '登录' }}</span>
        </div>
        <div v-show="userStore.user != null">
            <span class="exit" @click="exit">退出</span>
        </div>
    </div>
</template>

<script setup>
import { useRouter, useRoute } from 'vue-router'
import { useUserStore } from '@/store/userStore';
import { inject } from 'vue';
const router = useRouter()
const route = useRoute()
const userStore = useUserStore()
const reload = inject('reload')
function login() {
    if (sessionStorage.getItem('token')) {
        // router.push(`${router.currentRoute.value.path}/profile`)
        router.push({name:'profile'})
    } else {
        // router.push(`${router.currentRoute.value.path}/login`)
        router.push({name:'login'})
    }

}
function searchRoom() {
    if (sessionStorage.getItem('token')){
        // router.push(`${router.currentRoute.value.path}/searchroom`);
        router.push({name:'searchRoom'})
    }else{
        // router.push(`${router.currentRoute.value.path}/login`)
        router.push({name:'login'})
    }
    
}
function toProfile() {
    if (sessionStorage.getItem('token')){
        // router.push(`${router.currentRoute.value.path}/profile`)
        router.push({name:'profile'})
    // console.log(`${router.currentRoute.value.path}/searchroom`);
    }else{
        // router.push(`${router.currentRoute.value.path}/login`)
        router.push({name:'login'})
    }
}
function toFriendsList() {
    if (sessionStorage.getItem('token')){
        // router.push(`${router.currentRoute.value.path}/friendlist`)
        router.push({name:'friendList'})
    // console.log(`${router.currentRoute.value.path}/searchroom`);
    }else{
        // router.push(`${router.currentRoute.value.path}/login`)
        router.push({name:'login'})
    }
   
}
function exit() {
    if (sessionStorage.getItem('token')) {
        sessionStorage.clear();
        userStore.user = null;
        router.replace('/home')
        console.log(router.currentRoute.value.path);
    }
}
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css?family=Open+Sans:300,400|Lora");

.nav {
    display: flex;
    align-items: center;
    text-align: center;
    margin: 0px auto;
    border-radius: 3px;
    cursor: pointer;
    height: 33px;
    width: 95%;
    background-color: #FF8A65;
}

.nav div {
    flex: 2;
    border-right: 2px #E6EE9C solid;
    color: #FAFAFA;
    font-size: 12px;
}

.nav div:hover {
    color: #FFD54F;
}

.nav div:last-child {
    border-right: none;
}
</style>