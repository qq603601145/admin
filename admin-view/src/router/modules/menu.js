/** When your routing table is too long, you can split it into small modules**/

import Layout from '@/views/layout/Layout'

const menuRouter = {
  path: '/menu',
  component: Layout,
  redirect: '/menu/list',
  name: '菜单管理',
  meta: {
    roles: ['ROLE_admin'],
    title: '菜单管理',
    icon: 'example'
  },
  children: [
    {
      path: 'list',
      component: () => import('@/views/menu/list'),
      name: '菜单列表',
      meta: { roles: ['ROLE_admin'], title: '菜单列表', icon: 'edit' }
    },
    {
      path: 'account',
      component: () => import('@/views/menu/account'),
      name: '管理账户',
      meta: { roles: ['ROLE_admin'], title: '管理账户', icon: 'edit' }
    },
    {
      path: 'role',
      component: () => import('@/views/menu/role'),
      name: '用户权限',
      meta: { roles: ['ROLE_admin'], title: '用户权限', icon: 'edit' }
    }
  ]
}
export default menuRouter
