<template>
  <div>
    <!-- 카테고리, 제목, 작성자-->
    <div class="row">
      <div class="d-flex border-bottom border-light-subtle">
        <div class="m-1 col-1 p-1 fw-bolder">
          {{ baseStore.convertCategory(store.community.category) }}
        </div>
        <div class="m-1 flex-fill p-1">{{ store.community.title }}</div>
        <div class="m-1 col-2 p-1">{{ store.community.nickname }}</div>
      </div>
    </div>
    <!-- 조회수, 업로드일자 작성할 것입니다.  -->
    <div class="row">
      <div class="d-flex justify-content-between">
        <div class="p-1 col-3">조회수 : {{ store.community.views }}</div>
        <div class="p-1 col-auto flex-column">
          <div class="small text-secondary">
            최초 업로드 일자 :{{ store.dateTimeFormatter(store.community.uploadDate) }}
          </div>
          <div class="small text-secondary">
            마지막 수정일 :{{ store.dateTimeFormatter(store.community.updateDate) }}
          </div>
        </div>
      </div>
    </div>
    <!-- 게시글 내용을 보여주는 화면입니다. -->
    <div class="row my-1">
      <div class="justify-content-center border rounded-3 w-100 p-2">
        <article class="container" style="min-height: 25vh">{{ store.community.content }}</article>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useCommunityStore } from '@/stores/community'
import { useBaseStore } from '@/stores/base'

const baseStore = useBaseStore()
const store = useCommunityStore()
</script>

<style scoped></style>
