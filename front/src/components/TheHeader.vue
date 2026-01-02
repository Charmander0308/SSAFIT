<template>
    <header class="d-flex flex-wrap justify-content-center align-items-center py-2 mb-4 border-bottom">
        <router-link :to="{name: 'main'}" 
        class="mb-3 mb-md-0 me-md-auto link-body-emphasis text-decoration-none">
            <img src="@/assets/img/logo.png" alt="ssafit 로고" style="width: 160px; height: 50px;">
        </router-link>
        <ul class="nav flex-nowrap hstack gap-3 ">
			<li class="nav-item">
                <router-link to="/main"
                class="nav-link active px-1 px-md-3 fw-bolder">홈</router-link> 
			</li>
			<li class="nav-item">
                <router-link :to="{name: 'dashboard', params: { id: userStore.userInfo?.id } }" 
                class="nav-link px-1 px-md-3 fw-bolder"> 대시보드 </router-link> 
			</li>
			<li class="nav-item">
                <router-link :to="{name: 'videoList'}" class="nav-link px-1 px-md-3 fw-bolder"> 운동 </router-link> 
			</li>
			<li class="nav-item">
                <router-link :to="{name: 'communityList'}" class="nav-link px-1 px-md-3 fw-bolder">게시판</router-link> 
			</li>
            <li class="nav-item">
                <a 
                    class="nav-link px-1 px-md-3 d-flex align-items-center fw-bolder text-dark"
                    style="cursor: pointer;"
                    @click.prevent="openMyPage" 
                >
                    <div class="badge px-3 py-2 me-2" :class="userStore.userRankInfo.class">{{userStore.userRankInfo.value}}</div>
                    {{ userStore.userInfo?.nickname }}님
                </a> 
            </li>
            <li class="nav-item">
                <button @click="logout" class="btn btn-sm btn-outline-secondary">로그아웃</button>
            </li>
		</ul>
        <MyPage ref="myPageModalRef"/>
    </header>
</template>

<script setup>
    import { ref } from 'vue';
    import { useUserStore } from '@/stores/user';
    import MyPage from '@/components/MyPage.vue'

    const userStore = useUserStore()
    const myPageModalRef = ref(null)

    const openMyPage = () => {
        myPageModalRef.value.show()
    }

    const logout = () => {
        userStore.logout()
    }

</script>

<style scoped>
    
</style>