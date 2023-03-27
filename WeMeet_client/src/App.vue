<script setup>
import { nextTick,ref,provide } from 'vue';
    
    const isRouterActive = ref(true)
    const reload = () => {
        isRouterActive.value = false
        nextTick(() => {
            isRouterActive.value = true
        })
    }
    provide('reload', reload)
</script>

<template>
    <router-view v-if="isRouterActive"></router-view>
</template>

<style scoped>

</style>
