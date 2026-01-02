<template>
  <div class="d-flex flex-row align-items-start">
    <div class="me-3">
      <!--사용자 사진 저장-->
      <img
        :src="userProfileImg"
        alt="profile image"
        class="img-fluid rounded-circle mb-3 border"
        style="width: 50px; height: 50px; object-fit: cover"
      />
    </div>
    <div class="col border rounded-1">
      <!-- JS로 수정할 것 -->
      <form class="d-flex justify-content-end">
        <textarea
          v-model="newComment.content"
          placeholder="내용을 입력하세요"
          class="flex-fill overflow-hidden"
          style="border: none; outline: none; background-color: transparent; resize: none"
        ></textarea>
        <button @click.prevent="writeComment" class="bg-transparent border-0 px-3">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            width="30"
            height="30"
            fill="currentColor"
            class="bi bi-send"
            viewBox="0 0 16 16"
          >
            <path
              d="M15.854.146a.5.5 0 0 1 .11.54l-5.819 14.547a.75.75 0 0 1-1.329.124l-3.178-4.995L.643 7.184a.75.75 0 0 1 .124-1.33L15.314.037a.5.5 0 0 1 .54.11ZM6.636 10.07l2.761 4.338L14.13 2.576zm6.787-8.201L1.591 6.602l4.339 2.76z"
            />
          </svg>
        </button>
      </form>
    </div>
  </div>
</template>

<script setup>
import { storeToRefs } from 'pinia'
import { useVideoStore } from '@/stores/video'
import { computed, ref } from 'vue'
import { useUserStore } from '@/stores/user'

const userStore = useUserStore()
const store = useVideoStore()
const newComment = ref({
  content: '',
  youtubeVideoId: 0,
})

const { video } = storeToRefs(store)
const writeComment = () => {
  if (newComment.value.content.length === 0) {
    alert('입력하세요')
    return
  }
  newComment.value.youtubeVideoId = video.value.id
  console.log('댓글입니다 : ' + newComment.value)
  store.writeComment(newComment.value)
  newComment.value.content = ''
}

const userProfileImg = computed(() => {
  return userStore.userInfo?.profileImgDirectory || '/assets/img/userProfile/default_profile.png'
})
</script>

<style scoped></style>
