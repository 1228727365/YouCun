import { createRouter, createWebHistory } from 'vue-router'
import Login from '@/page/Login.vue'
import Register from '@/page/Register.vue'
import Home from '@/page/Home.vue'
import Appea from '@/page/Home/Appea.vue'
import Word from '@/page/Home/Word.vue'
import Image from '@/page/Home/Image.vue'
import Setting from '@/page/Home/Setting.vue'

const router = createRouter({
    history: createWebHistory(),
    routes: [
        // 添加根路径重定向到home
        {
            path: '/',
            redirect: '/home'
        },
        {
            path: '/login',
            name: 'Login',
            component: Login
        }, {
            path: '/register',
            name: 'Register',
            component: Register
        }, {
            path: '/home',
            name: 'Home',
            component: Home,
            redirect: '/home/appea', 
            children:[
                {
                    path:'appea',
                    name:'Appea',
                    component:Appea
                },{
                    path:'word',
                    name:'Word',
                    component:Word
                },{
                    path:'image',
                    name:'Image',
                    component:Image
                },{
                    path:'setting',
                    name:'Setting',
                    component:Setting
                }
            ]
        }
    ]
})

export default router
