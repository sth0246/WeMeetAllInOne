<template>
    <AlertSoltVue>
        <template v-slot:input>
            <input type="text" placeholder="请输入uid..." v-model="uid" @keyup.enter="addFriend">
        </template>
        <template v-slot:btn>
            <button @click="addFriend">添加好友</button>
        </template>
    </AlertSoltVue>
</template>

<script setup>
    import AlertSoltVue from './AlertSolt.vue';
    function addFriend(){
        
    }
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css?family=Open+Sans:300,400|Lora");
    input {
    font-family: "Lora", serif;
    background-color: rgb(255, 204, 188);
    outline: none;
    border: 0px;
    border-radius: 4px;
    padding: 8px;
    font-size: 10px;
    margin: 15px auto;
    color: #6D4C41;
}
    input::-webkit-input-placeholder{
        color: #A1887F;
        letter-spacing: 1px;
    }

button {
    font-family: "Open Sans", sans-serif;
    outline: none;
    border: 0px;
    border-radius: 3px;
    cursor: pointer;
    padding: 8px 12px;
    background-color: rgb(255, 138, 101);
    color: rgb(245, 245, 245);
    font-size: 8px;
    font-weight: 500;
    margin-top: 18px;
}

button:hover {
    color: rgb(255, 183, 77);
    background-color: rgb(255, 87, 34);
    font-weight: 600;
}
</style>