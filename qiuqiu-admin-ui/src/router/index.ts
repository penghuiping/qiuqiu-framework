import Vue from 'vue'
import VueRouter, { RouteConfig } from 'vue-router'
import Login from '@/views/Login.vue'
import Home from '@/views/Home.vue'
import Index from '@/components/Index.vue'
import User from '@/components/User.vue'
import Role from '@/components/Role.vue'
import Group from '@/components/Group.vue'
import Media from '@/components/Media.vue'
import Report from '@/components/Report.vue'
import Permission from '@/components/Permission.vue'

Vue.use(VueRouter)

const routes: Array<RouteConfig> = [
  {
    path: '/',
    name: 'Login',
    component: Login
  },
  {
    path: '/home',
    name: 'Home',
    component: Home,
    children: [
      {
        path: '/home',
        name: 'Index',
        component: Index
      },
      {
        path: '/home/user',
        name: 'User',
        component: User
      },
      {
        path: '/home/role',
        name: 'Role',
        component: Role
      },
      {
        path: '/home/permission',
        name: 'Permission',
        component: Permission
      },
      {
        path: '/home/group',
        name: 'Group',
        component: Group
      },
      {
        path: '/home/media',
        name: 'Media',
        component: Media
      },
      {
        path: '/home/report',
        name: 'Report',
        component: Report
      }
    ]
  }
  // {
  //   path: '/about',
  //   name: 'About',
  //   // route level code-splitting
  //   // this generates a separate chunk (about.[hash].js) for this route
  //   // which is lazy-loaded when the route is visited.
  //   component: () =>
  //     import(/* webpackChunkName: "about" */ '../views/About.vue')
  // }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
