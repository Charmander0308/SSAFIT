<template>
  <div>
    <button @click.prevent="$emit('btnClick')" class="btn btn-primary">
      {{ btnType }}
    </button>
  </div>
</template>

<script setup>
defineProps({
  btnType: String,
})
defineEmits(['btnClick'])
</script>

<style scoped></style>
