<template>
  <div class="topContainer">
    <div class="container">
      <div class="welcome">
        <div class="pinkbox" ref="pinkbox">
          <!-- 注册 -->
          <div class="signup" v-if="flag">
            <h1>Register</h1>
            <form>

              <input type="text" placeholder="Username" v-model="userRegister.username">
              <input type="email" placeholder="Email" v-model="userRegister.email">
              <input type="date" placeholder="生日" v-model="userRegister.birthDay">
              <input type="password" placeholder="Password" v-model="userRegister.pwd">
              <input type="password" :placeholder="confirm" v-model="confirmPassword">
              <div class="genders">
                <input type="radio" name="gender" value='male' v-model="userRegister.sex">
                <label for="male">male</label>
                <input type="radio" name="gender" value="female" v-model="userRegister.sex">
                <label for="female">female</label>

              </div>
              <label v-show="resgisterMesg" style="color:red;font-family:'Lora', serif">信息不全！！！</label>
              <button class="button sumbit" @click.prevent="register">Create Account</button>
            </form>
          </div>

          <!-- 登录 -->
          <div class="signin" v-else>
            <h1>Sign In</h1>
            <form class="more-padding">
              <input type="text" placeholder="Username" v-model="userLogin.username">
              <input type="password" placeholder="Password" v-model="userLogin.pwd">
              <div class="checkbox">
                <input type="checkbox" id="remember" /><label for="remember">Remember Me</label>
              </div>
              <label v-show="loginMesg" style="color:red;font-family:'Lora', serif">用户名或密码错误</label>
              <button class="buttom sumbit" @click.prevent="login">Login</button>
            </form>
          </div>
        </div>

        <div class="leftbox">
          <h2 class="title"><span>BLOOM</span>&<br>BOUQUET</h2>
          <p class="desc">Pick your perfect <span>bouquet</span></p>
          <img class="flower smaller"
            src="https://hbimg.huabanimg.com/c09305167a883e60179a45374df73252304001359acca-W3qbYm_fw658/format/webp" />
          <p class="account">Have an account?</p>
          <button class="button" @click="signin">Login</button>
        </div>

        <div class="rightbox">
          <h2 class="title"><span>BLOOM</span>&<br>BOUQUET</h2>
          <p class="desc">Pick your perfect <span>bouquet</span></p>
          <img class="flower"
            src="https://hbimg.huabanimg.com/b28be92c8198975a74ad656eba00b352c9b9e589819af-lDXUAS_fw658/format/webp" />
          <p class="account">Don't have an account?</p>
          <button class="button" @click="signup">Sign Up</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed } from '@vue/reactivity';
import { onMounted, reactive, ref } from 'vue';
import { useUserStore } from '@/store/userStore';
import { axiosLogin, axiosRegister } from '@/requests/request.js';
import { useRouter, useRoute } from 'vue-router'
import { getToken } from '@/utils/token.js'

const router = useRouter()
const route = useRoute()
const confirmPassword = ref('')
const confirm = ref('Confirm Password')
let confirmError = '两次密码不一致，请重新输入！'
let resgisterMesg = ref(false)
let loginMesg = ref(false)
const flag = ref(false)
const pinkbox = ref(null)
const userRegister = reactive({
  username: '',
  email: '',
  birthDay: '',
  pwd: '',
  sex: ''
})
const userLogin = reactive({
  username: '',
  pwd: ''
})
const userStore = useUserStore()
function register() {
  // 补充
  if (userRegister.pwd != '' && userRegister.username != '' && userRegister.email != '' && userRegister.gender != '') {
    if (userRegister.pwd != confirmPassword.value) {
      userRegister.password = "",
        confirmPassword.value = ""
      confirm.value = confirmError;
    } else {
      resgisterMesg.value = false
      //注册请求
      if (userRegister.sex == 'male') {
        userRegister.sex = 1
      } else {
        userRegister.sex = 0
      }
      axiosRegister(userRegister).then(res => {
        console.log(res);
        signin()
      }).catch(err => {
        // 注册失败
      })
    }
  } else {
    resgisterMesg.value = true
  }
}
function login() {
  loginMesg.value = false
  if (userLogin.username != '' && userLogin.pwd != '') {
    // 登录请求
    axiosLogin(userLogin).then(res => {
      console.log(res.data);
      userStore.updatUser(res.data[0])
      let uid = res.data.length > 0 ? res.data[0].uid : null;
      if (uid != null) {
        router.push('/home')
        const token = getToken()
        userStore.updateToken(token)
      }else{
        loginMesg.value = true
      }
    })

  }else{
    loginMesg.value = true
  }
}
function signup() {
  flag.value = !flag.value
  pinkbox.value.style.transform = 'translateX(93%)'
}
function signin() {
  flag.value = !flag.value
  pinkbox.value.style.transform = 'translateX(0%)'
}
</script>

<style  scoped>
@import url("https://fonts.googleapis.com/css?family=Open+Sans:300,400|Lora");

.topContainer {
  position: absolute;
  height: 314px;
  width: 100%;
  top: 12%;
  left: 0;
  background-color: rgb(251, 233, 231, .3);

}

.container {
  width: 450px;
  height: 310px;
  background-color: rgba(0, 0, 0, 0.1);
  margin: 0 auto;
  border-radius: 5px;
  overflow: hidden;
  position: relative;
}

.container::before {
  content: "";
  position: absolute;
  width: 500px;
  height: 360px;
  top: -25px;
  right: 0;
  bottom: 0;
  left: -25px;
  box-shadow: inset 0 0 0 3000px rgba(255, 255, 255, 0.3);
  ;
  filter: blur(10px);
}

.welcome {
  background: #f6f6f6;
  width: 400px;
  height: 215px;
  position: absolute;
  top: 16%;
  left: 6%;
  border-radius: 5px;
  box-shadow: 5px 5px 5px rgba(0, 0, 0, 0.1);
}

.pinkbox {
  position: absolute;
  top: -15%;
  left: 5%;
  background: #eac7cc;
  width: 190px;
  height: 280px;
  border-radius: 5px;
  box-shadow: 2px 0 10px rgba(0, 0, 0, 0.1);
  transition: all 0.5s ease-in-out;
  z-index: 2;
}

.signup,
.signin {
  transition: all 0.5s ease;
}

.leftbox,
.rightbox {
  position: absolute;
  width: 50%;
  transition: 1s all ease;
}

.leftbox {
  left: -2%;
}

.rightbox {
  right: -2%;
}

/* 字体和按钮的样式 */
h1 {
  font-family: "Open Sans", sans-serif;
  text-align: center;
  margin-top: 38px;
  text-transform: uppercase;
  color: #f6f6f6;
  font-size: 10px;
  letter-spacing: 8px;
}

.title {
  font-family: "Lora", serif;
  color: #8e9aaf;
  font-size: 10px;
  line-height: 10px;
  letter-spacing: 3px;
  text-align: center;
  font-weight: 300;
  margin-top: 20%;
}

.desc {
  margin-top: 5px;
}

.account {
  margin-top: 28%;
  font-size: 10px;
}

p {
  font-family: "Open Sans", sans-serif;
  font-size: 6px;
  margin-bottom: 13px;
  letter-spacing: 2px;
  color: #8e9aaf;
  text-align: center;
}

span {
  color: #eac7cc;
}

.flower {
  position: absolute;
  width: 58px;
  height: 58px;
  top: 45%;
  left: 27%;
  opacity: 0.8;
}

.smaller {
  width: 30px;
  height: 30px;
  top: 48%;
  left: 30%;
  opacity: 0.9;
}

button {
  padding: 4px;
  font-family: "Open Sans", sans-serif;
  text-transform: uppercase;
  letter-spacing: 3px;
  font-size: 8px;
  border-radius: 10px;
  margin: auto;
  outline: none;
  display: block;
}

button:hover {
  cursor: pointer;
  background: #eac7cc;
  color: #f6f6f6;
  transition: background-color 1s ease-out;
}

.button {
  margin-top: 1%;
  background: #f6f6f6;
  color: #ce7d88;
  border: solid 1px #eac7cc;
}

/* 表单样式 */
form {
  display: flex;
  align-items: center;
  flex-direction: column;
  padding-top: 7px;
}

.more-padding {
  padding-top: 35px;
}

.more-padding input {
  padding: 8px;
}

.more-padding .sumbit {
  margin-top: 25px;
}

.sumbit {
  margin-top: 25px;
  padding: 8px;
  border-color: #ce7d88;
}

.sumbit:hover {
  background: #cbc0d3;
  border-color: #bfb1c9;
}

input {
  background: #eac7cc;
  width: 65%;
  color: #ce7d88;
  border: none;
  border-bottom: 1px solid rgba(246, 246, 246, 0.5);
  padding: 9px;
  font-weight: 100;
}

input::placeholder {
  color: #f6f6f6;
  letter-spacing: 2px;
  font-size: 1.0em;
  font-weight: 100;
}

input:focus {
  color: #ce7d88;
  outline: none;
  border-bottom: 1.2px solid rgba(206, 125, 136, 0.7);
  font-size: 10px;
  transition: 0.8s all ease;
}

input:focus::placeholder {
  opacity: 0;
}

.genders {
  display: flex;
  margin: 12px auto;
}

.genders label {
  margin: 0 10px 0 10px;
}

label {
  font-family: "Open Sans", sans-serif;
  color: #ce7d88;
  font-size: 10px;
  letter-spacing: 1px;
}

.checkbox {
  display: inline;
  white-space: nowrap;
  position: relative;
  left: -32px;
  top: 0px;
}

input[type=checkbox] {
  width: 15px;
  background: #ce7d88;
}

.checkbox input[type=checkbox]:checked+label {
  color: #ce7d88;
  transition: 0.5s all ease;
}
</style>