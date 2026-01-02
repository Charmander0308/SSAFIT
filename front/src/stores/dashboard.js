import { ref } from 'vue'
import { defineStore } from 'pinia'
import http from '@/util/http-common' 

export const useDashboardStore = defineStore('dashboard', () => {
    const dashboardData = ref({
        follower: 0,
        following: 0,
        totalVisited: 0,
        continuousVisited: 0,
        totalViews: 0,
        totalComments: 0,
        achievedChallenges: [],
        grade: 0 
    })

    const followerList = ref([])
    const followingList = ref([])
    const isFollowing = ref(false)

    const modalAchievedList = ref([]) 

    const getDashboardData = async (targetId) => {
        try {
            const res = await http.get(`/dashboard/${targetId}`)
            
            if (res.data) {
                console.log(res.data)

                const { memberDto, achievedChallenges } = res.data
                
                dashboardData.value = {
                    ...memberDto, 
                    achievedChallenges: achievedChallenges || []
                }
            }
        } catch (err) {
            console.error("대시보드 정보 로딩 실패", err)
        }
    }

    const getChallengeList = async (targetId) => {
        try {
            const res = await http.get(`/challenge/rate/${targetId}`)
            if (res.data) {
                console.log(res.data)
                
                modalAchievedList.value = res.data
            }
        } catch (err) {
            console.error(err)
        }
    }

    const getFollowerList = async (targetId) => {
        try {
            const res = await http.get(`/member/${targetId}/followers`)
            console.log(res.data)
            if(res.data) {
                followerList.value = res.data
            }
        } catch (err) {
            console.error(err)
        }
    }

    const getFollowingList = async (targetId) => {
        try {
            const res = await http.get(`/member/${targetId}/followings`)
            console.log(res.data)
            if(res.data) {
                followingList.value = res.data
            }
        } catch (err) {
            console.error(err)
        }
    }

    const checkFollowStatus = async (targetId) => {
        try {
            const res = await http.get(`/member/status/${targetId}`)
            if (res.data !== undefined) {
                isFollowing.value = res.data // true or false
            }
        } catch (err) {
            console.error("팔로우 상태 확인 실패", err)
        }
    }

    const toggleFollow = async (targetId) => {
        try {
            await http.post(`/member/${targetId}/follow`)
            
            isFollowing.value = !isFollowing.value
            return true // 성공 여부 반환
        } catch (err) {
            console.error("팔로우 토글 실패", err)
            return false
        }
    }

    return { 
        dashboardData, 
        modalAchievedList, 
        getDashboardData, 
        getChallengeList,
        followerList,
        followingList,
        getFollowerList,
        getFollowingList,
        isFollowing,
        checkFollowStatus,
        toggleFollow,
    }
})