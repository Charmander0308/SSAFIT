<template>
  <div class="text-center">
    <div class="row row-cols-1 gy-2">
      <div v-for="challenge in challengeList" class="col">
        <div class="row justify-content-center border rounded p-3">
          <div class="col col-lg-2 d-flex">
            <img
              :src="challenge.challengeIconDirectory"
              class="img-thumbnail align-self-center"
              alt="업적 아이콘"
            />
          </div>
          <div class="col col-lg-10">
            <div class="row row-cols-1 py-2 justify-content-md-center">
              <div class="col">{{ challenge.challengeName }}</div>
              <div class="col">{{ challenge.challengeInfo }}</div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
defineProps({
  challengeList: Array,
})
</script>

<style scoped></style>
