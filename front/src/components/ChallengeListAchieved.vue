<template>
  <div>
    <!--끝난 챌린지 컴포넌트-->
    <div class="pt-2">
      <h4>내가 달성한 업적</h4>
    </div>
    <!--챌린지 컴포넌트-->
    <BaseChallengeComponent :challenge-list="achievedChallenges"></BaseChallengeComponent>
  </div>
</template>

<script setup>
import { useChallengeStore } from '@/stores/challenge'
import BaseChallengeComponent from './BaseChallengeComponent.vue'
import { storeToRefs } from 'pinia'

const challengeStore = useChallengeStore()
const { achievedChallenges, ongoingChallenges } = storeToRefs(challengeStore)
</script>

<style scoped></style>
