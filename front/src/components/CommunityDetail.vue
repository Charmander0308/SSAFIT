<template>
    <div>
        <div class="my-4">

            <!-- 게시글 내용 조회 화면 -->
            <CommunityDetailPost></CommunityDetailPost>
            <!-- 게시글 좋아요 관련 화면 -->
            <CommunityDetailLiked></CommunityDetailLiked>
            <!-- 게시글 댓글 관련 화면 -->
            <CommunityDetailComment></CommunityDetailComment>
        </div>
    </div>
</template>

<script setup>
import { useCommunityStore } from '@/stores/community';
import { onMounted } from 'vue';
import { useRoute} from 'vue-router';
import CommunityDetailLiked from './CommunityDetailLiked.vue';
import CommunityDetailPost from './CommunityDetailPost.vue';
import CommunityDetailComment from './CommunityDetailComment.vue';

const store = useCommunityStore()
const route = useRoute()
onMounted(()=>{
    store.getCommunity(route.params.id)
})


</script>

<style scoped></style>
