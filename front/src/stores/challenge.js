import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import http from '@/util/http-common'
import router from '@/router'

//axios를 이용하여 baseUrl을 세팅해서 쓸수도 있다.
const CHALLENGE_URL = '/challenge'

export const useChallengeStore = defineStore('challenge', () => {
  //저장하자
  const challengeList = ref([]) // 게시글 목록
  const achievedChallenges = computed(() => {
    return challengeList.value.filter((c) => c.achieveDate !== null)
  })

  const ongoingChallenges = computed(() => {
    return challengeList.value.filter((c) => c.achieveDate === null)
  })

  //챌린지 목록조회
  const getChallengeList = function () {
    http.get(CHALLENGE_URL)
      .then((res) => {
        console.log('챌린지' + res.data)
        challengeList.value = res.data
      })
      .catch(() => {
        //필요시작성
      })
  }

  return {
    getChallengeList,
    achievedChallenges,
    ongoingChallenges,
  }
})
