import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '@/views/HomeView.vue'
import LoginView from '@/views/LoginView.vue'
import SignupView from '@/views/SignupView.vue'
import MainView from '@/views/MainView.vue'
import DashboardView from '@/views/DashboardView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
    },
    {
      path: '/login',
      name: 'login',
      component: LoginView
    },
    {
      path: '/signup',
      name: 'signup',
      component: SignupView
    },
    {
      path: '/main',
      name: 'main',
      component: MainView
    },
    {
      path: '/dashboard/:id?',
      name: 'dashboard',
      component: DashboardView
    },
    {
      path: "/board",
      component: () => import('../views/BoardView.vue'),
      children: [
        {
          path: "community",
          name: "communityList",
          component: () => import('@/components/CommunityList.vue')
        },
        {
          path: "community/create",
          name: "communityCreate",
          component: () => import('@/components/CommunityCreate.vue')
        },
        {
          path: "community/:id",
          name: "communityDetail",
          component: () => import('@/components/CommunityDetail.vue')
        },
        {
          path: "video",
          name: "videoList",
          component: () => import('@/components/VideoList.vue')
        },
        {
          path: "video/:id",
          name: "videoDetail",
          component: () => import('@/components/VideoDetail.vue')
        },
      ]
    },
  ],
})

export default router
