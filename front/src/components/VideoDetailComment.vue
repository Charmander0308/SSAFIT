<template>
  <div class="row p-0">
    <!--댓글 개수 표현-->
    <div class="my-3 p-0 vstack gap-2">
      <!-- 댓글 개수 표시 -->
      <div class="fw-bolder">댓글 {{ video.commentCnt }}개</div>

      <!-- Community Detail Comment write : 댓글 작성 컴포넌트 -->
      <VideoDetailCommentWrite></VideoDetailCommentWrite>
    </div>

    <!-- 댓글별 작성자사진 +(닉네임/내용/작성일) -->
    <!-- Community Detail Comment List : 댓글 목록 컴포넌트 -->
    <VideoDetailCommentList></VideoDetailCommentList>
  </div>
</template>

<script setup>
import VideoDetailCommentList from './VideoDetailCommentList.vue'
import VideoDetailCommentWrite from './VideoDetailCommentWrite.vue'
import { useVideoStore } from '@/stores/video'
import { storeToRefs } from 'pinia'

const videoStore = useVideoStore()
const { video } = storeToRefs(videoStore)
console.log('videoDetailComment : ' + video.value)
</script>

<style scoped></style>
