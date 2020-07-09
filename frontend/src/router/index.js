import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/views/Login'
import Main from '@/views/Main'
import User from '@/views/User'
import Role from '@/views/Role'
import Permission from '@/views/Permission'

Vue.use(Router);

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Login',
      component: Login
    },
    {
      path: '/Main',
      name: 'Main',
      component: Main
    },
    {
      path: '/User',
      name: 'User',
      component: User
    },
    {
      path: '/Role',
      name: 'Role',
      component: Role
    },
    {
      path: '/Permission',
      name: 'Permission',
      component: Permission
    }

  ]
})
