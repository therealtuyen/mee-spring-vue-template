import { createRouter, createWebHashHistory } from 'vue-router'
import AppLayout from '@/layout/AppLayout.vue'
import RouteInfo from '@/constants/routeInfo'
import constants from '@/constants'

const router = createRouter({
  history: createWebHashHistory(),
  routes: [
    {
      path: '/',
      component: AppLayout,
      children: [
        {
          path: RouteInfo.APP.DASH_BOARD.path,
          name: RouteInfo.APP.DASH_BOARD.name,
          component: () => import('@/views/Dashboard.vue')
        },
        {
          path: '/uikit/formlayout',
          name: 'formlayout',
          component: () => import('@/views/uikit/FormLayout.vue')
        },
        {
          path: '/uikit/input',
          name: 'input',
          component: () => import('@/views/uikit/Input.vue')
        },
        {
          path: '/uikit/floatlabel',
          name: 'floatlabel',
          component: () => import('@/views/uikit/FloatLabel.vue')
        },
        {
          path: '/uikit/invalidstate',
          name: 'invalidstate',
          component: () => import('@/views/uikit/InvalidState.vue')
        },
        {
          path: '/uikit/button',
          name: 'button',
          component: () => import('@/views/uikit/Button.vue')
        },
        {
          path: '/uikit/table',
          name: 'table',
          component: () => import('@/views/uikit/Table.vue')
        },
        {
          path: '/uikit/list',
          name: 'list',
          component: () => import('@/views/uikit/List.vue')
        },
        {
          path: '/uikit/tree',
          name: 'tree',
          component: () => import('@/views/uikit/Tree.vue')
        },
        {
          path: '/uikit/panel',
          name: 'panel',
          component: () => import('@/views/uikit/Panels.vue')
        },

        {
          path: '/uikit/overlay',
          name: 'overlay',
          component: () => import('@/views/uikit/Overlay.vue')
        },
        {
          path: '/uikit/media',
          name: 'media',
          component: () => import('@/views/uikit/Media.vue')
        },
        {
          path: '/uikit/menu',
          component: () => import('@/views/uikit/Menu.vue'),
          children: [
            {
              path: '/uikit/menu',
              component: () => import('@/views/uikit/menu/PersonalDemo.vue')
            },
            {
              path: '/uikit/menu/seat',
              component: () => import('@/views/uikit/menu/SeatDemo.vue')
            },
            {
              path: '/uikit/menu/payment',
              component: () => import('@/views/uikit/menu/PaymentDemo.vue')
            },
            {
              path: '/uikit/menu/confirmation',
              component: () => import('@/views/uikit/menu/ConfirmationDemo.vue')
            }
          ]
        },
        {
          path: '/uikit/message',
          name: 'message',
          component: () => import('@/views/uikit/Messages.vue')
        },
        {
          path: '/uikit/file',
          name: 'file',
          component: () => import('@/views/uikit/File.vue')
        },
        {
          path: '/uikit/charts',
          name: 'charts',
          component: () => import('@/views/uikit/Chart.vue')
        },
        {
          path: '/uikit/misc',
          name: 'misc',
          component: () => import('@/views/uikit/Misc.vue')
        },
        {
          path: '/blocks',
          name: 'blocks',
          component: () => import('@/views/utilities/Blocks.vue')
        },
        {
          path: '/utilities/icons',
          name: 'icons',
          component: () => import('@/views/utilities/Icons.vue')
        },
        {
          path: '/pages/timeline',
          name: 'timeline',
          component: () => import('@/views/pages/Timeline.vue')
        },
        {
          path: '/pages/empty',
          name: 'empty',
          component: () => import('@/views/pages/Empty.vue')
        },
        {
          path: '/pages/crud',
          name: 'crud',
          component: () => import('@/views/pages/Crud.vue')
        },
        {
          path: '/documentation',
          name: 'documentation',
          component: () => import('@/views/utilities/Documentation.vue')
        }
      ]
    },
    {
      path: '/landing',
      name: 'landing',
      component: () => import('@/views/pages/Landing.vue')
    },
    {
      path: '/:pathMatch(.*)*',
      name: 'notfound',
      component: () => import('@/views/pages/NotFound.vue')
    },

    {
      path: RouteInfo.AUTH.LOGIN.path,
      name: RouteInfo.AUTH.LOGIN.name,
      component: () => import('@/views/pages/auth/Login.vue')
    },
    {
      path: '/auth/access',
      name: 'accessDenied',
      component: () => import('@/views/pages/auth/Access.vue')
    },
    {
      path: '/auth/error',
      name: 'error',
      component: () => import('@/views/pages/auth/Error.vue')
    }
  ]
})

router.beforeEach((to, from, next) => {
  if (isTokenInvalid()) {
    localStorage.clear()
    redirectIfInvalid(to, next)
  } else {
    redirectIfValid(to, next)
  }
})

function isTokenInvalid() {
  const accessToken = localStorage.getItem(constants.TOKEN.ACCESS_TOKEN)
  if (accessToken === null || accessToken === 'undefined') {
    return true
  }
  const accessTokenExpired = parseInt(localStorage.getItem(constants.TOKEN.ACCESS_TOKEN_EXPIRED))
  const now = new Date().getTime()
  return !accessTokenExpired && now > accessTokenExpired
}

function redirectIfInvalid(to, next) {
  if (to.path !== RouteInfo.AUTH.LOGIN.path) {
    router.push({
      path: RouteInfo.AUTH.LOGIN.path
    })
  } else {
    next()
  }
}

function redirectIfValid(to, next) {
  if (to.path === RouteInfo.AUTH.LOGIN.path) {
    router.push({
      path: RouteInfo.APP.DASH_BOARD.path
    })
  } else {
    next()
  }
}

export default router
