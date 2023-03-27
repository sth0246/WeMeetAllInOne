import { defineStore } from "pinia";

export const useUserStore = defineStore('userStore',{
    state: () => ({
            token:null,
            user:null,
    }),
    getters:{
       
        
    },
    actions:{
        updatUser(user){
            this.user = user
            if(!sessionStorage.getItem('user')){
                user = JSON.stringify(user)
            sessionStorage.setItem('user',user)
            }
        },
        updateToken(token){
            this.token = token
            if(!sessionStorage.getItem('token')){
                sessionStorage.setItem('token',token);
            }
        },
        updateUserName(newUserName){
            if(this.user != null){
                this.user.username = newUserName
                // console.log(this.user.username);
            }
        },
        updateUserPassword(newPassword){
            if(this.user != null){
                this.user.pwd = newPassword
                // console.log(this.user.username);
            }
        },
        updateUserBirthday(birthday){
            if(this.user != null){
                this.user.birthDay = birthday
                // console.log(this.user.username);
            }
        }
    }
})