<template>
  <div class="hstack gap-3 mt-2 overflow-x-auto scroller">
    <div class="col-4" v-for="videos in categoryVideoList">
      <!-- videoListCategoryItem 비디오 단일 객체 컴포넌트 ㅋㅋㅋㅋㅋㅋ별걸 다 쪼갬 -->
      <VideoListCategoryItem :video-item="videos"></VideoListCategoryItem>
    </div>
  </div>
</template>

<script setup>
import VideoListCategoryItem from './VideoListCategoryItem.vue'

defineProps({
  categoryVideoList: Array,
})
</script>

<style scoped></style>
