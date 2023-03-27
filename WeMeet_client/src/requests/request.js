import instance from "./base";
import axios from 'axios'
import qs from 'qs'
export function axiosRegister(userRegister){
    userRegister = qs.stringify(userRegister)
    return instance({
        url:'/user/register',
        method:'post',
        data:userRegister
    })
}
export function axiosLogin(userLogin){
    userLogin = qs.stringify(userLogin)
    return instance({
        method:'post',
        url:'/user/login',
        data:userLogin
    })
    // return axios.post('https://www.sthwjq.love:8443/WeMeet/user/login',userLogin)
}

export function addFriendRequest(){
   
}
export function userInfoUpdate(user){
    user = qs.stringify(user)
    return instance({
        method:'post',
        url:'/user/updateUSer',
        data:user
    })
}