import { constantRouterMap, asyncRouterMap } from '@/router'

/**
 * 通过meta.role判断是否与当前用户权限匹配
 * @param roles
 * @param route
 */
function hasPermission(roles, route) {
  if (route.meta && route.meta.roles) {
    return roles.some(role => route.meta.roles.includes(role))
  } else {
    return true
  }
}

/**
 * 递归过滤异步路由表，返回符合用户角色权限的路由表
 * @param routes asyncRouterMap
 * @param roles
 */
function filterAsyncRouter(routes, roles) {
  const res = []
  routes.forEach(route => {
    const tmp = { ...route }
    if (hasPermission(roles, tmp)) {
      if (tmp.children) {
        tmp.children = filterAsyncRouter(tmp.children, roles)
      }
      res.push(tmp)
    }
  })
  return res
}

function validatenull(val) {
  if (val instanceof Array) {
    if (val.length === 0) return true
  } else if (val instanceof Object) {
    if (JSON.stringify(val) === '{}') return true
  } else {
    if (val === 'null' || val == null || val === 'undefined' || val === undefined || val === '') return true
    return false
  }
  return false
}

function formatRoutes(aMenu) {
  const aRouter = []
  aMenu.forEach(oMenu => {
    const {
      path,
      component,
      redirect,
      name,
      icon,
      children
    } = oMenu
    if (!validatenull(component)) {
      const oRouter = {
        path: path,
        component(resolve) {
          let componentPath = ''
          if (component === 'Layout') {
            require(['../../views/layout/Layout'], resolve)
            return
          } else {
            componentPath = component
          }
          require([`../../views/${componentPath}.vue`], resolve)
        },
        name: name,
        redirect: redirect,
        meta: {
          title: name,
          icon: icon,
          noCache: false,
          breadcrumb: true
        },
        children: validatenull(children) ? [] : formatRoutes(children)
      }
      aRouter.push(oRouter)
    }
  })
  return aRouter
}

const permission = {
  state: {
    routers: constantRouterMap,
    addRouters: []
  },
  mutations: {
    SET_ROUTERS: (state, routers) => {
      state.addRouters = routers
      state.routers = constantRouterMap.concat(routers)
    }
  },
  actions: {
    GenerateRoutes({ commit }, data) {
      return new Promise(resolve => {
        const menus = data.data.menus
        const roles = data.data.roles
        // const accessedRouters = roles
        // if (roles.includes('admin')) {
        //   accessedRouters = asyncRouterMap
        // } else {
        //   accessedRouters = filterAsyncRouter(asyncRouterMap, roles)
        // }
        // const aRouter = []
        let oRouter = []
        if (menus && menus.length > 0) {
          oRouter = formatRoutes(menus)
        }
        const accessedRouters = filterAsyncRouter(asyncRouterMap, roles)
        const routers = oRouter.concat(accessedRouters)
        commit('SET_ROUTERS', routers)
        resolve()
      })
    }
  }
}

export default permission
