<template>
  <div class="vstack gap-3 p-0 my-3">
    <div v-for="comment in community.comments" class="d-flex flex-row">
      <div class="me-3">
        <router-link
          :to="{ name: 'dashboard', query: { id: comment.memberId } }"
          class="nav-link px-1 px-md-3 fw-bolder"
        >
          <img
            :src="comment.profileImg || '/assets/img/userProfile/default_profile.png'"
            class="img-thumbnail rounded-circle"
            style="width: 50px; height: 50px;"
            object-fit: cover
          />
        </router-link>
      </div>
      <!-- 닉네임, 내용, 등록일자 -->
      <div class="flex-fill">
        <div class="d-flex flex-column">
          <div class="fw-bold">{{ comment.nickname }}</div>
          <div class="pt-1 pb-2">{{ comment.content }}</div>
          <div class="small text-secondary">{{ store.dateTimeFormatter(comment.uploadDate) }}</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useCommunityStore } from '@/stores/community'
import { storeToRefs } from 'pinia'
const store = useCommunityStore()
const { community } = storeToRefs(store)
console.log(community.value.comments)
</script>

<style scoped></style>
