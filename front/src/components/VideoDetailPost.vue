<template>
  <div class="row my-4">
    <div class="justify-content-center w-100 p-0">
      <div class="ratio ratio-16x9 rounded-3">
        <iframe
          class="rounded-3"
          :src="`https://www.youtube.com/embed/${videoStore.video.videoId}`"
          title="YouTube video"
          allowfullscreen
        ></iframe>
      </div>
    </div>
  </div>
  <!-- 제목, 업로드일자 작성. -->
  <div class="row">
    <div class="d-flex justify-content-between my-3">
      <div class="flex-fill fs-5 fw-bold">{{ videoStore.video.title }}</div>
      <div class="col-1"></div>
      <div class="col-auto flex-column">
        <div class="text-end">
          <div class="small text-secondary">
            최초 업로드 일자 : {{ date }}
            <!-- {{ videoStore.video.publishedAt }} -->
          </div>
        </div>
      </div>
    </div>
  </div>
  <!-- (사진+이름) + 조회수 -->
  <div class="row d-flex justify-content-between mb-3">
    <div class="col-auto hstack gap-2">
      <img
        :src="`${videoStore.video.channelThumbnailUrl}`"
        class="rounded-circle"
        style="width: 40px; height: 40px; background-color: blue"
      />
      <div>{{ videoStore.video.channelTitle }}</div>
    </div>
    <div class="col-auto">추천수 : {{ videoStore.video.likeCount }}</div>
  </div>
</template>

<script setup>
import { useVideoStore } from '@/stores/video'
import { computed, ref, onMounted } from 'vue'
const videoStore = useVideoStore()
const date = ref()

onMounted(() => {
  date.value = computed(() => {
    console.log('비디오 힘들다')
    console.log(videoStore.video.publishedAt)
    return videoStore.dateTimeFormatter(videoStore.video.publishedAt)
  })
})
</script>

<style scoped></style>
