<template>
  <div class="py-3">
    <div v-for="community in communityList" :key="community.id" class="row py-2">
      <div class="col text-center fw-bolder">
        {{ baseStore.convertCategory(community.category) }}
      </div>
      <!-- 카테고리는 피니아를 통해 데이터 변환작업을 할 것(드롭다운도)-->
      <div class="col-7 text-start">
        <router-link
          :to="`/board/community/${community.id}`"
          class="text-body text-decoration-none"
        >
          {{ community.title }}</router-link
        >
      </div>
      <div class="col text-center">{{ community.nickname }}</div>
      <div class="col text-center text-secondary">
        {{ communityStore.dateTimeFormatter(community.uploadDate) }}
      </div>
    </div>
  </div>
</template>

<script setup>
import { useCommunityStore } from '@/stores/community'
import { useBaseStore } from '@/stores/base'
const communityStore = useCommunityStore()
const baseStore = useBaseStore()
const props = defineProps({
  communityList: {
    type: Array,
  },
})
</script>

<style scoped></style>
