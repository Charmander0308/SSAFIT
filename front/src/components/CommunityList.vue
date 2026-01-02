<template>
  <div>
    <div class="mt-4 py-2">

      <div class="row">
        <!-- community list section head :  드롭다운과 글 작성 버튼 -->
        <CommunityListHead @select-category="categorySelect"></CommunityListHead>
      </div>
      <div class="row">
        <!-- community list section body : 글 목록들 -->
        <CommunityListBody :communityList="store.communityList"></CommunityListBody>
      </div>
      <div class="row">
        <!-- community list section foot : 페이지네이션 구현 -->
        <community-list-foot
        :searchPage="communitySearchCondition.page"
        @select-page="pageSelect"
        ></community-list-foot>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'
import { useCommunityStore } from '@/stores/community'
import { onMounted, ref } from 'vue'
import CommunityListFoot from './CommunityListFoot.vue'
import CommunityListHead from './CommunityListHead.vue'
import CommunityListBody from './CommunityListBody.vue'
import { useChallengeStore } from '@/stores/challenge'
import ChallengeList from './ChallengeList.vue'

const router = useRouter()
const store = useCommunityStore()
const challengeStore = useChallengeStore()
const communitySearchCondition = ref({
  page: 1,
  pageSize: 10,
  category: null,
})
onMounted(() => {
  store.getCommunityList()
})

const categorySelect = (id) => {
  console.log('category-select')
  if (id === 0) {
    id = null
  }
  communitySearchCondition.value.category = id
  communitySearchCondition.value.page = 1
  store.getCommunitySearch(communitySearchCondition.value)
}
const pageSelect = (page) => {
  if (page <= 0) {
    page = 1
  }
  communitySearchCondition.value.page = page
  store.getCommunitySearch(communitySearchCondition.value)
}
</script>

<style scoped></style>
