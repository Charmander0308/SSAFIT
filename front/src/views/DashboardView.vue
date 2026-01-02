<script setup>
import { ref, onMounted, watch, computed } from 'vue'
import { useUserStore } from '@/stores/user'
import { useBaseStore } from '@/stores/base'
import { useDashboardStore } from '@/stores/dashboard'
import { useRoute } from 'vue-router'

import TheHeader from '@/components/TheHeader.vue'
import TheFooter from '@/components/TheFooter.vue'
import ChallengeListModal from '@/components/ChallengeListModal.vue'
import FollowListModal from '@/components/FollowListModal.vue'

const userStore = useUserStore()
const baseStore = useBaseStore()
const dashboardStore = useDashboardStore()
const route = useRoute()

const challengeModalRef = ref(null)

const followModalRef = ref(null)
const followModalTitle = ref('')
const currentFollowList = ref([])

const targetRankInfo = computed(() => {
    const grade = dashboardStore.dashboardData.grade || 0
    const found = baseStore.userRank.find(item => item.id == grade)
    return found || { value: 'NONE', class: 'bg-secondary' }
})

const fetchDashboardData = async () => {
    const targetId = route.params.id || route.query.id || userStore.userInfo?.id
    if(!targetId) return

    await dashboardStore.getDashboardData(targetId)
    if (userStore.userInfo && String(targetId) !== String(userStore.userInfo.id)) {
        await dashboardStore.checkFollowStatus(targetId)
    }
}

const isMyPage = computed(() => {
    const targetId = route.params.id || route.query.id
    const myId = userStore.userInfo?.id
    // 둘 다 없거나, ID가 같으면 내 페이지
    if (!targetId || !myId) return true
    return String(targetId) === String(myId)
})



const openChallengeModal = async () => {
    if (!userStore.userInfo) return
    const targetId = route.params.id || route.query.id || userStore.userInfo?.id

    await dashboardStore.getChallengeList(targetId)
    challengeModalRef.value.show()
}

const openFollowModal = async (type) => {
    if (!userStore.userInfo) return // 로그인 체크

    const targetId = route.params.id || route.query.id || userStore.userInfo?.id
    
    if (type === 'follower') {
        followModalTitle.value = '팔로워'
        await dashboardStore.getFollowerList(targetId) // API 호출
        currentFollowList.value = dashboardStore.followerList // 데이터 바인딩
    } else {
        followModalTitle.value = '팔로잉'
        await dashboardStore.getFollowingList(targetId) // API 호출
        currentFollowList.value = dashboardStore.followingList // 데이터 바인딩
    }
    
    followModalRef.value.show()
}

const handleFollow = async () => {
    if (!userStore.userInfo) {
        alert("로그인이 필요합니다.")
        return
    }
    
    const targetId = route.params.id || route.query.id
    
    // 팔로우 토글 요청
    const success = await dashboardStore.toggleFollow(targetId)
    
    if (success) {
        // 성공 시 대시보드 데이터(팔로워 숫자 등) 새로고침
        await dashboardStore.getDashboardData(targetId)
    }
}

onMounted(() => {
    fetchDashboardData()
})

watch(() => route.params.id, () => {
    fetchDashboardData()
})
</script>

<template>
    <div>
        <TheHeader class="container-fluid" />

        <main class="container py-5">
            <div class="row h-100 d-flex justify-content-center align-items-center">
                
                <aside class="col-md-4 text-center">
                    <figure class="d-flex flex-column align-items-center">
                        <img 
                            :src="userStore.userInfo?.profileImg || '/assets/img/userProfile/default_profile.png'" 
                            alt="profile image" 
                            class="img-fluid rounded-circle border"
                            style="width: 150px; height: 150px; object-fit: cover;"
                        >
                        
                        <figcaption 
                            class="badge mt-3 fs-5 px-3 py-2"
                            :class="targetRankInfo.class"
                        >
                            {{ targetRankInfo.value }}
                        </figcaption>

                        <div v-if="!isMyPage" class="mt-3">
                            <button 
                                class="btn fw-bold px-4 rounded-pill transition-btn"
                                :class="dashboardStore.isFollowing ? 'btn-outline-secondary' : 'btn-primary'"
                                @click="handleFollow"
                            >
                                <i class="bi" :class="dashboardStore.isFollowing ? 'bi-check-lg' : 'bi-person-plus-fill'"></i>
                                {{ dashboardStore.isFollowing ? ' 팔로잉' : ' 팔로우' }}
                            </button>
                        </div>
                    </figure>

                    <section class="mt-4">
                        <span class="fw-bold fs-5">업적</span>
                        <i class="bi bi-clipboard ms-1" 
                           style="cursor: pointer; color: blue;" 
                           @click="openChallengeModal">
                        </i>
                        
                        <div class="d-flex justify-content-center gap-2 mt-2">
                            <template v-if="dashboardStore.dashboardData.achievedChallenges.length > 0">
                                <div v-for="(item, index) in dashboardStore.dashboardData.achievedChallenges" :key="index">
                                    <img :src="item" class="badge-small rounded-circle border" alt="업적아이콘" style="width:40px; height:40px;">
                                </div>
                            </template>

                            <template v-else>
                                <span class="text-muted small">달성한 업적이 없습니다.</span>
                            </template>
                        </div>
                    </section>
                </aside>
                
                <section class="col-md-8">
                    <div class="row">
                        <div class="col-6 p-3"
                            @click="openFollowModal('follower')"
                            style="cursor: pointer;">
                            <div class="text-secondary mb-2">팔로워</div>
                            <div class="display-4 text-end fw-normal">
                                {{ dashboardStore.dashboardData?.follower }}
                                <span class="fs-6 text-muted">명</span>
                            </div>
                        </div>
                        <div class="col-6 p-3"
                            @click="openFollowModal('following')"
                            style="cursor: pointer;">
                            <div class="text-secondary mb-2">팔로잉</div>
                            <div class="display-4 text-end fw-normal">
                                {{ dashboardStore.dashboardData?.following }}
                                <span class="fs-6 text-muted">명</span>
                            </div>
                        </div>
                    </div>
                    <hr class="my-2">

                    <div class="row">
                        <div class="col-6 p-3">
                            <div class="text-secondary mb-2">총 출석일수</div>
                            <div class="display-4 text-end fw-normal">
                                {{ dashboardStore.dashboardData?.totalVisited }}
                                <span class="fs-6 text-muted">일</span>
                            </div>
                        </div>
                        <div class="col-6 p-3">
                            <div class="text-secondary mb-2">연속 출석일수</div>
                            <div class="display-4 text-end fw-normal">
                                {{ dashboardStore.dashboardData?.continuousVisited }}
                                <span class="fs-6 text-muted">일</span>
                            </div>
                        </div>
                    </div>
                    <hr class="my-2">

                    <div class="row">
                        <div class="col-6 p-3">
                            <div class="text-secondary mb-2">내 게시물 누적 조회수</div>
                            <div class="display-4 text-end fw-normal">
                                {{ dashboardStore.dashboardData?.totalViews }}
                                <span class="fs-6 text-muted">회</span>
                            </div>
                        </div>
                        <div class="col-6 p-3">
                            <div class="text-secondary mb-2">내가 단 댓글 수</div>
                            <div class="display-4 text-end fw-normal">
                                {{ dashboardStore.dashboardData?.totalComments }}
                                <span class="fs-6 text-muted">회</span>
                            </div>
                        </div>
                    </div>
                </section>

            </div>
        </main>

        <TheFooter />

        <ChallengeListModal 
            ref="challengeModalRef"
            :challenge-data="dashboardStore.modalAchievedList" 
        />
        <FollowListModal
            ref="followModalRef"
            :title="followModalTitle"
            :follow-list="currentFollowList"
        />
    </div>
</template>

<style scoped>
.transition-btn {
    transition: all 0.3s ease;
}
.transition-btn:active {
    transform: scale(0.95);
}
</style>