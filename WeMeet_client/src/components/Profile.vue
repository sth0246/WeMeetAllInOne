<template>
    <div class="profile">
        <h4>个人信息</h4>
        <div class="info">
            <ul>
                <li>
                    <span>昵称</span>
                    <span v-if="updateUserNameFlag">{{ userStore.user.username || '' }}</span>
                    <input type="text" placeholder="请输入昵称..." v-model="newUsername" v-else>
                    <span class="userEditor" @click="updateUserName" v-show="updateUserNameFlag">编辑</span>
                    <span class="userEditor" @click="updateUserName" v-show="!updateUserNameFlag">完成</span>
                </li>
                <li>
                    <span>uid</span>
                    <span>{{ userStore.user.uid }}</span>
                </li>
                <li>
                    <span>用户密码</span>
                    <span v-if="updatePasswordFlag">*******</span>
                    <form class="updateaPsswoder" v-else>
                        <div class="itemInput">
                            <label for="oldPassworld">旧密码：</label>
                            <input type="password" v-model="oldPassword" @blur="confirmOldPassword">
                            <label style="color:red" v-show="confirmOldPasswordFlag">密码错误！</label>
                        </div>
                        <div class="itemInput">
                            <label for="newPassword">新密码：</label>
                            <input type="password" v-model="newPassword">
                        </div>

                        <div class="itemInput">
                            <label for="confirmNewPassword">再输入：</label>
                            <input type="password" v-model="confirmNewPassword">
                            <label v-show="newPassword != confirmNewPassword">与上次输入不符！</label>
                        </div>
                        
                        <div>
                            <button @click.prevent="updatePasswordOver">完成</button>
                        </div>
                    </form>
                    <span class="userEditor" @click="updatePassword" v-show="updatePasswordFlag">编辑</span>
                </li>
                <li>
                    <span>生日</span>
                    <!-- <span v-if="updateBirthdayFlag">{{user.birthday}}</span> -->
                    <input type="date" v-model="birthday">
                    <span class="userEditor" @click="updateBirthday">完成</span>
                </li>
                <li>
                    <span>性别</span>
                    <span>{{ userStore.user.sex == 1 ? "男" : "女" }}</span>
                </li>
            </ul>
        </div>
    </div>
</template>

<script setup>
import { reactive, ref } from 'vue';
import { useUserStore } from '@/store/userStore';
import { userInfoUpdate } from '@/requests/request'
const userStore = useUserStore()

const confirmOldPasswordFlag = ref(false)
const newUsername = ref(userStore.user.username)
const oldPassword = ref()
const newPassword = ref()
const birthday = ref(userStore.user.birthDay)
const confirmNewPassword = ref()
const updateUserNameFlag = ref(true)
const updatePasswordFlag = ref(true)
function updateUserName() {
    updateUserNameFlag.value = !updateUserNameFlag.value;
    if(updateUserNameFlag.value){
        userStore.updateUserName(newUsername.value)
        let user = JSON.parse(sessionStorage.getItem('user'))
        user.username = userStore.user.username;
        sessionStorage.setItem('user',JSON.stringify(user))
        userInfoUpdate(user).then(res => {
            console.log(res.data);
        })
    }
    
}
function updatePassword() {
    updatePasswordFlag.value = !updatePasswordFlag.value
   
}
function confirmOldPassword(){
    if(userStore.user.pwd != oldPassword.value){
        confirmOldPasswordFlag.value = true
    }else{
        confirmOldPasswordFlag.value = false
    }
}
function updateBirthday() {
    if(userStore.user.birthDay != birthday){
        userStore.updateUserBirthday(birthday.value)
        let user = JSON.parse(sessionStorage.getItem('user'))
        user.birthDay = userStore.user.birthDay;
        sessionStorage.setItem('user',JSON.stringify(user))
        userInfoUpdate(user).then(res => {
            console.log(res.data);
        })
    }
}
function updatePasswordOver() {
    updatePasswordFlag.value = !updatePasswordFlag.value
    userStore.updateUserPassword(newPassword.value)
        let user = JSON.parse(sessionStorage.getItem('user'))
        console.log(user);
        console.log(userStore.user);
        user.pwd = userStore.user.pwd;
        sessionStorage.setItem('user',JSON.stringify(user))
        userInfoUpdate(user).then(res => {
            console.log(res.data);
        })
}
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css?family=Open+Sans:300,400|Lora");

.profile {
    position: absolute;
    z-index: 9;
    top: 14%;
    left: 50%;
    transform: translateX(-50%);
    font-family: "Open Sans", serif;
    border-radius: 6px;
    width: 388px;
    height: 268px;
    padding: 10px;
    background-color: #F5F5F5;
    overflow: hidden;
    background-image: url("https://w.wallhaven.cc/full/x8/wallhaven-x8ye3z.jpg");
    background-size: contain;
    background-position: center;
    color: #616161;
    font-size: 10px;
}

.profile::before {
    content: "";
    position: absolute;
    z-index: -1;
    top: 0%;
    left: 0%;
    height: 278px;
    width: 398px;
    padding: 10px;
    border-radius: 6px;
    filter: blur(20px);
    background-color: #F5F5F5;
}

h4 {
    font-family: "Lora", serif;
    font-size: 12px;
    border-bottom: 1px #E0E0E0 solid;
    padding-bottom: 5px;
    padding-left: 15px;
}

.info ul {
    margin-top: 12px;
    padding-bottom: 13px;
    border-radius: 8px;
    background-color: #F5F5F5;
}

ul>li {
    height: 20px;
    width: 90%;
    line-height: 20px;
    padding: 10px;
}

span {
    padding: 0 10px;
}

input {
    outline: none;
    border: none;
    padding: 5px;
    padding-left: 10px;
    width: 40%;
    height: 80%;
    border-radius: 3px;
    font-size: 10px;
    color: #8D6E63;
}

input:focus {
    outline: #B3E5FC 1px solid;
}

input::-webkit-input-placeholder {
    font-family: "Lora", serif;
    font-size: 10px;
    letter-spacing: 1px;
    color: #BCAAA4;
}

.userEditor {
    display: none;
}

.userEditor:hover {
    color: #4FC3F7;
    font-weight: 800;
}

ul li:first-child:hover .userEditor,
ul li:nth-child(2):hover .userEditor,
ul li:nth-child(3):hover .userEditor,
ul li:nth-child(4):hover .userEditor,
ul li:last-child:hover .userEditor {
    display: inline-block;
    cursor: pointer;
}

.updateaPsswoder {
    font-family: "Open Sans", sans-serif;
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    width: 80%;
    height: 50%;
    background-color: #EFEBE9;
    padding-top: 20px;
    border-radius: 7px;
}
.itemInput{
    margin-top: 10px;
}
.updateaPsswoder label {
    padding: 0 15px;
}

.updateaPsswoder input {
    height: 10%;
    width: 120px;
}
button{
    font-family: "Open Sans", sans-serif;
    font-size: 10px;
    font-weight: 500;
    border: none;
    height: 28px;
    width: 55px;
    border-radius: 5px;
    margin-left: 50%;
    margin-top: 4%;
    transform: translateX(-50%);
    background-color: #64B5F6;
}
button:hover{
    cursor: pointer;
    background-color: #BBDEFB;
    font-weight: 800;
    border: 1px solid #64B5F6;
    color: #757575;
}
</style>    