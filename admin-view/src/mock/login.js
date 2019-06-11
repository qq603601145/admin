import { param2Obj } from '@/utils'

const myAsyncRouterMap = [
  {
    path: '/test',
    component: 'Layout',
    redirect: 'menu/create',
    name: 'Menu',
    meta: {
      title: 'Menu',
      icon: 'example'
    },
    children: [
      {
        path: 'create',
        component: 'menu/create',
        name: 'CreateMenu',
        meta: { title: 'CreateMenu', icon: 'edit' }
      },
      {
        path: 'edit/:id(\\d+)',
        component: 'menu/edit',
        name: 'EditMenu',
        meta: { title: 'EditMenu', icon: 'edit' },
        hidden: true
      },
      {
        path: 'list',
        component: 'menu/list',
        name: 'MenuList',
        meta: { title: 'menuList', icon: 'list' }
      },
      {
        path: 'account',
        component: 'menu/account',
        name: 'account',
        meta: { title: 'account', icon: 'list' }
      },
      {
        path: 'role',
        component: 'menu/role',
        name: 'role',
        meta: { title: 'role', icon: 'list' }
      }
    ]
  }
]

const userMap = {
  admin: {
    menus: myAsyncRouterMap,
    token: 'admin',
    introduction: '我是超级管理员',
    avatar: 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif',
    name: 'Super Admin'
  },
  editor: {
    menus: myAsyncRouterMap,
    token: 'editor',
    introduction: '我是编辑',
    avatar: 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif',
    name: 'Normal Editor'
  }
}

export default {
  loginByUsername: config => {
    const { username } = JSON.parse(config.body)
    return userMap[username]
  },
  getUserInfo: config => {
    const { token } = param2Obj(config.url)
    if (userMap[token]) {
      return userMap[token]
    } else {
      return false
    }
  },
  logout: () => 'success'
}
