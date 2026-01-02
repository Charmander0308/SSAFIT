<template>
  <div>
    <div class="row">
      <div class="mt-4 py-2">
        <div class="d-flex">
          <div class="fs-3">나에게 맞는 운동을 찾아보세요</div>
          <div class="ms-auto"><BaseDropdown @select-dropdown="dropdownSelect"></BaseDropdown></div>
        </div>
        <!-- 드롭다운을 클릭해서 알맞은 운동 찾기 -->
        <!-- 드롭다운을 클릭하면 추천 운동 동영상 목록이 나오게 됩니다.-->
        <!-- AI에게서 id를 받아오지 않고 비디오만 받아왔네 내가.....컴포넌트 재사용성을 고려하지 않았슴-->
        <div class="row" v-if="recommended">
          <div class="w-100 border rounded-3 m-3 p-4">
            <div class="d-flex flex-column">
              <div class="fs-4 fw-bold">AI 운동 추천 영상</div>
              <div class="hstack gap-3 mt-2 overflow-x-auto scroller">
                <div class="col-4" v-for="videos in recommendVideoList">
                  <RouterLink :to="`/board/video/${videos.id}`">
                    <div class="ratio ratio-16x9">
                      <img
                        :src="`https://img.youtube.com/vi/${videos.youtubeId}/0.jpg`"
                        :alt= "`${videos.title}`"
                        class="w-100 border rounded-2"
                      />
                      <!-- -->
                    </div>
                  </RouterLink>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!--카테고리별로 v-for동작시키기-->
    <div v-for="list in videoList" class="row">
      <div class="w-100 border rounded-3 m-3 p-4">
        <div class="d-flex flex-column">
          <div class="fs-4 fw-bold">{{ list.category }}운동 추천 영상</div>
          <!-- 드롭다운 버튼 클릭시 videoRecommend보일 수 있게 하기-->
          <!-- video list by category : 비디오 리스트 받아서 v-for 다시 동작 -->
          <VideoListCategory :category-video-list="list.videoIds"></VideoListCategory>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { storeToRefs } from 'pinia'
import { useVideoStore } from '@/stores/video'
import { onMounted, computed } from 'vue'
import VideoListCategory from './VideoListCategory.vue'
import BaseDropdown from './BaseDropdown.vue'
import VideoListCategoryItem from './VideoListCategoryItem.vue'

const videoStore = useVideoStore()
onMounted(() => {
  videoStore.getVideoList()
})
const { videoList, recommendVideoList } = storeToRefs(videoStore)
const recommended = computed(() => recommendVideoList.value.length != 0)

const dropdownSelect = (id) => {
  console.log(id+"카테고리의 영상 추천요청")
  videoStore.getVideoRecommend(id)
}
</script>

<style scoped></style>
