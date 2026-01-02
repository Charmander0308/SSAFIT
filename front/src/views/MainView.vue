<script setup>
import TheHeader from '@/components/TheHeader.vue';
import TheFooter from '@/components/TheFooter.vue';
import { ref, onMounted } from 'vue'
import { useUserStore } from '@/stores/user' // 유저 스토어
import { useMainStore } from '@/stores/mainpage';
import { storeToRefs } from 'pinia';

const userStore = useUserStore()
const mainStore = useMainStore()
const {videos, posts} = storeToRefs(mainStore)

onMounted(() => {
    // mainStore.fetchMain()
})
</script>

<template>
    <div>
        <TheHeader class="container-fluid"/>

        <main>
            <div class="h-100 d-flex justify-content-center align-items-center py-5">
                <div class="container col-lg-8 col-10">
                    
                    <div class="row">
                        <div class="m-4 py-2">
                            <div class="d-flex flex-wrap align-items-center">
                                <div class="fs-3">운동하기 좋은 날씨네요!&nbsp;</div>
                                <div class="d-flex flex-wrap">
                                    <div class="fs-3">
                                        <strong class="text-primary">
                                            {{ userStore.userInfo?.nickname || '회원' }}님
                                        </strong>을 위한&nbsp;
                                    </div>
                                    <div class="fs-3">목록을 준비했어요!</div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="d-flex flex-wrap justify-content-center">
                            
                            <div class="col-10 col-lg-6">
                                <div class="border rounded-2 p-3 m-2 h-100">
                                    <div class="fs-5 fw-bold pb-2 text-primary">추천 동영상</div>
                                    
                                    <div id="carouselExampleIndicators" class="carousel slide main-view-article" data-bs-ride="carousel">
                                        <div class="carousel-indicators">
                                            <button 
                                                v-for="(video, index) in videos" 
                                                :key="'indi-' + index"
                                                type="button" 
                                                data-bs-target="#carouselExampleIndicators" 
                                                :data-bs-slide-to="index" 
                                                :class="{ active: index === 0 }"
                                                :aria-current="index === 0"
                                                :aria-label="'Slide ' + (index + 1)">
                                            </button>
                                        </div>

                                        <div class="carousel-inner h-100 rounded">
                                            <div 
                                                v-for="(video, index) in videos" 
                                                :key="video.id || index"
                                                class="carousel-item h-100" 
                                                :class="{ active: index === 0 }"
                                            >
                                                <RouterLink :to="`/board/video/${video.id}`">
                                                    <img :src="`https://img.youtube.com/vi/${video.youtubeId}/0.jpg`" class="d-block w-100 h-100 object-fit-cover" alt="동영상 썸네일">
                                                </RouterLink>
                                            </div>
                                            
                                            <div v-if="videos.length === 0" class="carousel-item active h-100">
                                                <div class="d-flex justify-content-center align-items-center h-100 bg-light text-muted">
                                                    추천 동영상이 없습니다.
                                                </div>
                                            </div>
                                        </div>

                                        <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="prev">
                                            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                                            <span class="visually-hidden">Previous</span>
                                        </button>
                                        <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="next">
                                            <span class="carousel-control-next-icon" aria-hidden="true"></span>
                                            <span class="visually-hidden">Next</span>
                                        </button>
                                    </div>
                                </div>
                            </div>

                            <div class="col-10 col-lg-6">
                                <div class="border rounded-2 p-3 m-2 h-100">
                                    <div class="fs-5 fw-bold pb-2 text-primary">추천 게시물</div>
                                    
                                    <div class="overflow-y-auto main-view-article scroller pe-2">
                                        
                                        <div v-for="post in posts" :key="post.id" class="card mb-3 shadow-sm border-0 bg-light">
                                            <div class="card-body">
                                                <h5 class="card-title fw-bold text-dark">{{ post.title }}</h5>
                                                <p class="card-text text-secondary text-truncate">{{ post.contentPart }}</p>
                                                
                                                <div class="d-flex justify-content-end">
                                                    <router-link 
                                                        :to="{ name: 'communityDetail', params: { id: post.id } }" 
                                                        class="btn btn-sm btn-primary"
                                                    >
                                                        Go to Post
                                                    </router-link>
                                                </div>
                                            </div>
                                        </div>

                                        <div v-if="posts.length === 0" class="text-center py-5 text-muted">
                                            등록된 추천 게시물이 없습니다.
                                        </div>

                                    </div>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </main>

        <TheFooter />
    </div>
</template>

<style scoped>
.main-view-article {
    height: 400px; /* 적절한 높이로 조절하세요 */
}

.object-fit-cover {
    object-fit: cover;
}

.scroller::-webkit-scrollbar {
    width: 6px;
}
.scroller::-webkit-scrollbar-thumb {
    background-color: #adb5bd;
    border-radius: 10px;
}
.scroller::-webkit-scrollbar-track {
    background-color: #f8f9fa;
}
</style>