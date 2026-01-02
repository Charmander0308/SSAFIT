<template>
  <div>
    <!-- 비디오 정보 컴포넌트-->
    <!-- video Detail Post-->
    <VideoDetailPost></VideoDetailPost>
    <!-- 비디오 댓글 관련 화면-->
    <VideoDetailComment></VideoDetailComment>
  </div>
</template>

<script setup>
import { useRoute } from 'vue-router'
import { onMounted } from 'vue'
import { useVideoStore } from '@/stores/video'
import VideoDetailComment from './VideoDetailComment.vue'
import VideoDetailPost from './VideoDetailPost.vue'

const route = useRoute()
const videoStore = useVideoStore()

onMounted(() => {
  console.log('비디오 끝났나요?')
  videoStore.getVideo(route.params.id)
})
</script>

<style scoped></style>
