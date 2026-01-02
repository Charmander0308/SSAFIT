<template>
  <div>
    <form>
      <div class="row">
        <div class="hstack gap-2 mb-3">
          <base-dropdown @select-dropdown="dropdownSelect"></base-dropdown>
          <div class="ms-2 flex-fill">
            <input
              type="text"
              v-model="community.title"
              class="form-control"
              name="title"
              placeholder="제목을 입력해주세요"
              required
            />
          </div>
        </div>
      </div>
      <div class="row">
        <div class="mb-3">
          <textarea
            class="form-control"
            name="content"
            v-model="community.content"
            style="height: 50vh"
            required
          ></textarea>
        </div>
      </div>
      <div class="row">
        <div class="hstack gap-2 justify-content-center">
          <base-button btn-type="등록" @btn-click="createCommunity"></base-button>
          <base-button btn-type="취소" @btn-click="resetCommunity"></base-button>
        </div>
      </div>
    </form>
  </div>
</template>

<script setup>
import BaseButton from '@/components/BaseButton.vue'
import BaseDropdown from '@/components/BaseDropdown.vue'
import { useRouter } from 'vue-router'
import { useCommunityStore } from '@/stores/community'
import { createApp, ref } from 'vue'

const router = useRouter()
const store = useCommunityStore()
const community = ref({
  type: 0,
  category: 0,
  title: '',
  content: '',
})

const dropdownSelect = (id) => {
  community.value.category = id
}

const createCommunity = function () {
  console.log(community.value)
  if (
    community.value.category === 0 ||
    community.value.category === null ||
    community.value.title.length === 0 ||
    community.value.content.length === 0
  ) {
    alert('다 입력하세요')
    return
  }
  store.createCommunity(community.value)
}

const resetCommunity = function () {
  router.push({ name: 'communityList' })
}
</script>

<style scoped></style>
