<template>
  <div>
    <RouterLink :to="`/board/video/${videoItem.id}`">
      <div class="ratio ratio-16x9">
        <img
          :src="`https://img.youtube.com/vi/${videoItem.youtubeId}/0.jpg`"
          class="w-100 border rounded-2"
        />
        <!-- <iframe
        class="rounded-3"
        :src="`https://www.youtube.com/embed/${videoItem}`"
        title="YouTube video"
        allowfullscreen
        ></iframe> -->
      </div>
    </RouterLink>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'

const router = useRouter()

defineProps({
  videoItem: Object,
})
</script>

<style scoped></style>
