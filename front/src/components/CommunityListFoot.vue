<template>
  <div>
    <nav>
      <ul class="pagination py-3 justify-content-center">
        <li class="page-item">
          <button class="page-link" aria-label="Previous" @click="selectPage(searchPage - 5)">
            <span aria-hidden="true">&laquo;</span>
          </button>
        </li>

        <li class="page-item">
          <button v-show="searchPage > 2" class="page-link" @click="selectPage(searchPage - 2)">
            {{ searchPage - 2 }}
          </button>
        </li>
        <li class="page-item">
          <button v-show="searchPage > 1" class="page-link" @click="selectPage(searchPage - 1)">
            {{ searchPage - 1 }}
          </button>
        </li>
        <li class="page-item">
          <button class="page-link bg-secondary bg-opacity-50" disabled>{{ searchPage }}</button>
        </li>
        <li class="page-item">
          <button class="page-link" @click="selectPage(searchPage + 1)">
            {{ searchPage + 1 }}
          </button>
        </li>
        <li class="page-item">
          <button class="page-link" @click="selectPage(searchPage + 2)">
            {{ searchPage + 2 }}
          </button>
        </li>

        <li class="page-item">
          <button class="page-link" aria-label="Next" @click="selectPage(searchPage + 5)">
            <span aria-hidden="true">&raquo;</span>
          </button>
        </li>
      </ul>
    </nav>
  </div>
</template>

<script setup>
defineProps({
  searchPage: Number,
})
const emit = defineEmits(['selectPage'])
const selectPage = (page) => {
  emit('selectPage', page)
}
</script>

<style scoped></style>
