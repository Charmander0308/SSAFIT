import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
// import axios from 'axios'
import http from '@/util/http-common'
import router from '@/router'

//axios를 이용하여 baseUrl을 세팅해서 쓸수도 있다.
const REST_API_URL = '/community'
const LIKED_URL = '/liked'
const COMMENT_URL = '/comment'

export const useCommunityStore = defineStore('community', () => {
  //저장하자
  const communityList = ref([]) // 게시글 목록
  const community = ref({}) //상세게시글

  //검색 및 정렬기능
  const SearchCondition = ref({
    page: 1,
    pageSize: 10,
    category: null,
  })
  //좋아요 요청

  const dateTimeFormatter = function (obj) {
    if (!obj) {
      console.log('데이터가 아직 없습니다.')
      return '...'
    }
    const targetDate = obj.value !== undefined ? obj.value : obj
    const date = new Date(targetDate)
    console.log('날짜까지 왔는가?' + date)
    const formattedDate = date.toLocaleDateString('ko-KR', {
      year: 'numeric',
      month: 'long',
      day: 'numeric',
    })

    return formattedDate
  }

  //게시글 목록조회
  const getCommunityList = function () {
    http
      .get(REST_API_URL, {
        params: SearchCondition,
      })
      .then((res) => {
        console.log(res.data)
        console.log(SearchCondition.value)
        communityList.value = res.data
      })
      .catch(() => {
        //필요시작성
      })
  }
  const getCommunitySearch = function (communitySearchCondition) {
    http
      .get(REST_API_URL, {
        params: communitySearchCondition,
      })
      .then((res) => {
        console.log(res.data)
        console.log(communitySearchCondition.value)
        communityList.value = res.data
      })
      .catch(() => {
        //필요시작성
      })
  }

  //게시글 상세 조회
  const getCommunity = function (id) {
    http
      .get(`${REST_API_URL}/${id}`)
      .then((res) => {
        console.log(res.data)
        community.value = res.data
      })
      .catch((err) => {
        console.log(err)
        console.log('게시글 상세조회 이슈발생')
      })
  }

  //게시글 등록 action
  const createCommunity = function (community) {
    // axios.post()
    http
      .post(REST_API_URL, community)
      .then((res) => {
        // console.log(res.data)
        router.push({ name: 'communityList' })
      })
      .catch((err) => {
        console.log(err)
      })
  }

  //게시글 수정
  const updateCommunity = function () {
    http.put(REST_API_URL, community.value).then(() => {
      //정상 완료
      router.push({ name: 'communityList' })
    })
  }

  const doLiked = function (likedDto) {
    http
      .post(LIKED_URL, likedDto)
      .then((res) => {
        console.log('잘됨?')
        return getCommunity(likedDto.communityId)
      })
      .catch((err) => {
        console.log(err)
      })
  }

  const writeComment = function (newComment) {
    http
      .post(COMMENT_URL, newComment)
      .then((res) => {
        console.log('댓글잘됨?')
        return getCommunity(newComment.communityId)
      })
      .catch((err) => {
        console.log(err)
      })
  }

  return {
    createCommunity,
    communityList,
    getCommunityList,
    community,
    getCommunity,
    updateCommunity,
    SearchCondition,
    doLiked,
    getCommunitySearch,
    writeComment,
    dateTimeFormatter,
  }
})
