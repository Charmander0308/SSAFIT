<template>
  <div class="p-2 mx-2">
    <div class="btn-group position-relative" style="width: 150px; margin-left: auto">
      <button
        type="button"
        class="btn dropdown-toggle border d-flex justify-content-between align-items-center w-100"
        @click="toggleDropdown"
      >
        {{ selectedLabel }}
      </button>

      <ul
        v-if="isOpen"
        class="dropdown-menu show"
        style="display: block; position: absolute; top: 100%; left: 0"
      >
        <li><a class="dropdown-item" href="#" @click.prevent="handleSelect(0, '전체')">전체</a></li>
        <li><hr class="dropdown-divider" /></li>

        <li v-for="category in categoryDropdown" :key="category.id">
          <button
            class="dropdown-item"
            type="button"
            @click="handleSelect(category.id, category.value)"
          >
            {{ category.value }}
          </button>
        </li>
      </ul>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue'
import { useBaseStore } from '@/stores/base'
import { storeToRefs } from 'pinia'

const baseStore = useBaseStore()
const { categoryDropdown } = storeToRefs(baseStore)
const emit = defineEmits(['selectDropdown'])

// --- 상태 관리 ---
const isOpen = ref(false) // 드롭다운 열림 상태
const selectedLabel = ref('선택해주세요') // 버튼에 표시될 텍스트

// --- 함수 ---
const toggleDropdown = () => {
  isOpen.value = !isOpen.value
}

const handleSelect = (id, value) => {
  selectedLabel.value = value
  isOpen.value = false // 선택 후 메뉴 닫기
  emit('selectDropdown', id)
}

// 3. 바깥쪽 클릭 시 닫히는 기능 추가 (선택 사항이지만 필수적인 UX)
const closeDropdown = (e) => {
  if (!e.target.closest('.btn-group')) {
    isOpen.value = false
  }
}

onMounted(() => window.addEventListener('click', closeDropdown))
onUnmounted(() => window.removeEventListener('click', closeDropdown))
</script>
