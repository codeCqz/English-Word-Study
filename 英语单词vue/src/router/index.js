//该文件专门用于创建整个应用的路由器
import VueRouter from 'vue-router'

import Main from '../views/Main.vue'
import Subject from '../components/Subject.vue'
import Result from '../components/Result.vue'
import Translation from '../components/Translation.vue'
import Study from '../components/Study.vue'
import Choose from '../components/Choose.vue'
import Info from '../components/Info.vue'
import Test from '../components/Test.vue'
import Plan from '../components/Plan.vue'
import Collect from '../components/Collect.vue'



export default new VueRouter({
    // mode: 'history',
    routes:[
        {
            path: '/', 
            redirect: '/main/subject'
        },
        {
            name:'main',
            path: '/main',
            component:Main,
            children:[
                {
                    name:'subject',
                    path: 'subject',
                    component:Subject,
                    
                },
                {    
                    name:'result',
                    path: 'result',
                    component: Result
                },
                {
                    name:'translation',
                    path:'translation',
                    component: Translation
                },
                {
                    name:'choose',
                    path: 'choose',
                    component:Choose     
                },
                {
                    name:'study',
                    path: 'study',
                    component:Study       
                },
                {
                    name:'info',
                    path:'info',
                    component:Info
                },
                {
                    name:'test',
                    path:'test',
                    component:Test
                }
                ,{
                    name:'plan',
                    path:'plan',
                    component:Plan
                },
                {
                    name:'collect',
                    path:'collect',
                    component:Collect
                }
                
            ]
        }, 
    ]
})