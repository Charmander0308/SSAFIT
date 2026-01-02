import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import local from '@/util/http-common'
import { useRouter } from 'vue-router'
import { parseJwt } from '@/util/jwt-util'
import { useBaseStore } from './base'

export const useUserStore = defineStore('user', () => {
    const router = useRouter()

    const accessToken = ref('')
    const refreshToken = ref('') 
    const userInfo = ref(null)

    const isLogin = computed(() => !!accessToken.value)

    const userRankInfo = computed(() => {
        const baseStore = useBaseStore()

        if (!userInfo.value) {
            return { value: 'NONE', class: 'bg-secondary' }
        }

        const myGrade = userInfo.value.grade
        const targetRank = baseStore.userRank.find(item => item.id === myGrade)

        return targetRank || { value: 'NONE', class: 'bg-secondary' }
    })

    //로그인
    const login = async (loginData) => {
        try {
            const res = await local.post('/auth/login', loginData)

            // 서버에서 받은 토큰 2개 다 저장
            accessToken.value = res.data['accessToken'] 
            refreshToken.value = res.data['refreshToken']
            
            localStorage.setItem('accessToken', accessToken.value)
            localStorage.setItem('refreshToken', refreshToken.value)

            const payload = parseJwt(accessToken.value)

            userInfo.value = {
                id: payload.sub,
                userId: payload.userId,
                nickname: payload.nickname,
                grade: payload.grade,
            }
            
            router.push('/main') 
        } catch (err) {
            console.error(err)
            alert('로그인에 실패했습니다. 다시 시도해주세요.')
        }
    }

    //로그아웃
    const logout = () => {
        accessToken.value = ''
        refreshToken.value = ''
        userInfo.value = null
        
        localStorage.removeItem('accessToken')
        localStorage.removeItem('refreshToken')
        
        router.push('/login')
    }

    return { accessToken, refreshToken, userInfo, userRankInfo, isLogin, login, logout }
})