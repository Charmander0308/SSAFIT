<template>
  <!--챌린지 모달 내부 화면 -->
  <div
    class="modal fade"
    id="staticBackdrop"
    data-bs-backdrop="static"
    data-bs-keyboard="false"
    tabindex="-1"
    aria-labelledby="staticBackdropLabel"
    aria-hidden="true"
  >
    <div class="custom-modal modal-dialog modal-dialog-centered modal-lg">
      <div class="modal-content p-4">
        <!--챌린지 내부 화면-->
        <div class="modal-header">
          <h5 class="modal-title" id="staticBackdropLabel">도전과제 목록</h5>
          <button
            type="button"
            class="btn-close"
            data-bs-dismiss="modal"
            aria-label="Close"
          ></button>
        </div>
        <!-- challenge list achieved-->
        <ChallengeListAchieved></ChallengeListAchieved>
        <!-- challenge list ongoing-->
        <ChallengeListOngoing></ChallengeListOngoing>
      </div>
    </div>
  </div>
</template>

<script setup>
import ChallengeListAchieved from './ChallengeListAchieved.vue'
import ChallengeListOngoing from './ChallengeListOngoing.vue'
</script>

<style scoped>
.custom-modal {
  max-height: 60vh;
  overflow-y: auto;
}
</style>
