<template>
    <div class="row p-0">
        <!--댓글 개수 표현-->
        <div class="my-3 p-0 vstack gap-2">
            <!-- 댓글 개수 표시 -->
            <div class="fw-bolder">댓글 {{store.community.commentCnt}}개</div>
        
        <!-- Community Detail Comment write : 댓글 작성 컴포넌트 -->
        <CommunityDetailCommentWrite></CommunityDetailCommentWrite>
        </div>

        <!-- 댓글별 작성자사진 +(닉네임/내용/작성일) -->
        <!-- Community Detail Comment List : 댓글 목록 컴포넌트 -->
        <CommunityDetailCommentList></CommunityDetailCommentList>
    </div>
</template>

<script setup>
    import CommunityDetailCommentList from './CommunityDetailCommentList.vue';
    import { useCommunityStore } from '@/stores/community';
import CommunityDetailCommentWrite from './CommunityDetailCommentWrite.vue';

const store = useCommunityStore()
</script>

<style scoped>

</style>