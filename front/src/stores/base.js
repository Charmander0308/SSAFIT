import { ref, computed } from 'vue'
import { defineStore } from 'pinia'

export const useBaseStore = defineStore('base', () => {
  const categoryDropdown = ref([
    { id: 1, value: '어깨' },
    { id: 2, value: '가슴' },
    { id: 4, value: '팔' },
    { id: 8, value: '복부' },
    { id: 16, value: '등' },
    { id: 32, value: '엉덩이' },
    { id: 64, value: '허벅지' },
    { id: 128, value: '종아리' },
    { id: 256, value: '전신' },
  ])

  const convertCategory = function (category) {
    if (!category) {
      return '값아직안온듯?error'
    }
    if (!categoryDropdown.value || categoryDropdown.value.length === 0) {
      return '카테고리 로딩 중... 이건 왜 검증해야 하는거야....'
    }
    if (typeof category === 'string') {
      console.log('글자인듯')
      const value = categoryDropdown.value.find((item) => item.value === category)
      return value ? value.id : '전체'
    } else if (typeof category === 'number') {
      console.log('숫자인듯')
      const value = categoryDropdown.value.find((item) => item.id === category)
      return value ? value.value : '전체'
    }
  }

  const userRank = ref([
    { id: 0, value: 'NONE', class: 'bg-secondary' },
    { id: 1, value: 'BRONZE', class: 'bg-bronze' },
    { id: 2, value: 'SILVER', class: 'bg-silver' },
    { id: 3, value: 'GOLD', class: 'bg-gold' },
    { id: 4, value: 'PLATINUM', class: 'bg-platinum' },
    { id: 5, value: 'DIAMOND', class: 'bg-diamond' },
    { id: 6, value: 'RUBY', class: 'bg-ruby' },
  ])

  return { categoryDropdown, userRank, convertCategory }
})
