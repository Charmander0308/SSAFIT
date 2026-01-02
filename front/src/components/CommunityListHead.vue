<template>
  <div class="row">
    <div class="d-flex justify-content-between align-items-end">
      <BaseDropdown @select-dropdown="dropdownSelect"></BaseDropdown>
      <BaseButton btn-type="글쓰기" @btn-click="writeButton"></BaseButton>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'
import { useCommunityStore } from '@/stores/community'
import { useBaseStore } from '@/stores/base'
import BaseDropdown from './BaseDropdown.vue'
import BaseButton from './BaseButton.vue'
const router = useRouter()
const emit = defineEmits(['selectCategory'])
const baseStore = useBaseStore()

const category = baseStore.categoryDropdown

const dropdownSelect = (id) => {
  emit('selectCategory', id)
}
const writeButton = () => {
  router.push({ name: 'communityCreate' })
}
</script>

<style scoped></style>
