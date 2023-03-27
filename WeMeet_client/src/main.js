import { createApp, VueElement } from 'vue'
import './style.css'
import App from './App.vue'
import router from './router/router'
import { createPinia } from 'pinia'
import './utils/rem.js'
const pinia = createPinia()
createApp(App).use(router).use(pinia).mount('#app')
