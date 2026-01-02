import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import http from '@/util/http-common'
import router from '@/router'

//http를 이용하여 baseUrl을 세팅해서 쓸수도 있다.
const VIDEO_URL = '/video'
const COMMENT_URL = '/comment'

export const useVideoStore = defineStore('video', () => {
  //저장하자
  const videoList = ref([]) // 게시글 목록
  const recommendVideoList = ref([])
  const video = ref({}) //상세게시글

  // 비디오 추천받기
  const getVideoRecommend = function (category) {
    http
      .get(`/ai/video`, {
        params: {
          category: category,
        },
      })
      .then((res) => {
        recommendVideoList.value = res.data
        console.log('답변 수신 완료', res.data)
      })
  }

  //게시글 목록조회
  const getVideoList = function () {
    http
      .get(VIDEO_URL)
      .then((res) => {
        console.log(res.data)
        console.log('비디오 응답 완')
        videoList.value = res.data
      })
      .catch(() => {
        //필요시작성
      })
  }

  //비디오 상세 조회
  const getVideo = function (id) {
    http
      .get(`${VIDEO_URL}/${id}`)
      .then((res) => {
        console.log(res.data)
        video.value = res.data
        console.log('비디오 상세조회' + video.value)
        return video.value
      })
      .catch((err) => {
        console.log(err)
        console.log('비디오 상세조회 이슈발생')
      })
  }

  const dateTimeFormatter = function (obj) {
    if (!obj) {
      console.log('시간 데이터가 아직 없습니다.')
      return '로딩 중...'
    }
    console.log(obj)
    const targetDate = obj.value !== undefined ? obj.value : obj
    const date = new Date(targetDate)

    const formattedDate = date.toLocaleDateString('ko-KR', {
      year: 'numeric',
      month: 'long',
      day: 'numeric',
    })

    return formattedDate
  }

  //비디오 댓글 작성
  const writeComment = function (newComment) {
    http
      .post(COMMENT_URL, newComment)
      .then((res) => {
        console.log('비디오댓글잘됨?')
        return getVideo(newComment.youtubeVideoId)
      })
      .catch((err) => {
        console.log(err)
        console.log('비디오안됨')
      })
  }

  return {
    videoList,
    video,
    getVideoList,
    getVideo,
    writeComment,
    getVideoRecommend,
    recommendVideoList,
    dateTimeFormatter,
  }
})
