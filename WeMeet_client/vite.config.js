import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import  { resolve } from 'path'

export default defineConfig({
  plugins: [vue()],
  resolve:{
    alias:{
      '@' : resolve(__dirname,'./src'),
    }
  },
  server:{
    host:'0.0.0.0',
    proxy: {
      '/api': {
        target:"https://www.sthwjq.love:8443/WeMeet",
        changeOrigin:true,
        rewrite: path => path.replace(/^\/api/, '')
      }
    }
   }
})
