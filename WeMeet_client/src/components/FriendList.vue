<template>
    <div class="fiendListContainer">
        <ul>
            <li v-for="item in 20">
                <span>username</span>
                <span @click="toHome">互动</span>
            </li>
        </ul>
        <button class="addFriend" @click.prevent="toAddFriend">添加好友</button>
    </div>
</template>

<script setup>
import { ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';

    const router = useRouter()
    const route = useRoute()
    function toAddFriend(){
        router.push(`/home/addfriend`)
    }
    function toHome(){
        router.push(`/home`)
    }
</script>

<style scoped>
    .fiendListContainer{
        font-family: "Lora", serif;
        font-size: 10px;
        font-weight: 600;
        position: absolute;
        top: 9%;
        left: 0%;
        border-top-right-radius: 5px;
        height: 91%;
        width: 142px;
        background-color: #F5F5F5;
        overflow: hidden;
    }
    ul{
        width: 102%;
        height: 83%;
        margin-top: 6px;
        margin-left: 4px;
        overflow-y: scroll;
        overflow-x: hidden;
    }
    li{
        display: flex;
      width: 134px;
      text-align: center;
      line-height: 30px;
      height: 30px;
      border-radius: 5px;
      margin-top: 3px;
      cursor: pointer;
      border-bottom: 1px solid #FFEBEE;
      background-color: #E0E0E0;  
    }
    li span:first-child{
        flex: 3;
        color: #5D4037;
        border-top-left-radius: 5px;
        border-bottom-left-radius: 5px;
        
    }
    li span:last-child:hover{
        background-color: #FF5722;
        color: #ECEFF1;
    }
    li span:last-child{
        flex: 2;
        background-color: #FF8A65;
        border-top-right-radius: 5px;
       border-bottom-right-radius: 5px;
    }
    .addFriend{
        font-family: "Lora", serif;
        font-size: 12px;
        position: fixed;
        bottom: 0%;
        border: none;
        width: 142px;
        height: 41px;
        color: #F5F5F5;
        background-color: #FFAB91;
    }
    .addFriend:hover{
        background-color: #FF8A65;
        cursor: pointer;
        color: #FFEB3B;
    }
</style>