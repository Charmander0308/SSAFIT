<script setup>
import { ref, onMounted } from 'vue'
import { Modal } from 'bootstrap'
import { useRouter } from 'vue-router'

const props = defineProps({
    title: String,      // "팔로워" 또는 "팔로잉" 제목
    followList: {       // 유저 목록 데이터
        type: Array,
        default: () => []
    }
})

const router = useRouter()
const modalRef = ref(null)
let modalInstance = null

const show = () => {
    modalInstance?.show()
}

const hide = () => {
    modalInstance?.hide()
}

onMounted(() => {
    modalInstance = new Modal(modalRef.value)
})

// 유저 프로필 클릭 시 해당 유저의 대시보드로 이동 (선택사항)
const goUserDashboard = (userId) => {
    hide() // 모달 닫고 이동
    router.push({ name: 'dashboard', params: { id: userId } }) 
}

defineExpose({ show, hide })
</script>

<template>
    <div class="modal fade" ref="modalRef" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title fw-bold">{{ title }} 목록</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body p-0">
                    <ul class="list-group list-group-flush" v-if="followList.length > 0">
                        <li v-for="user in followList" :key="user.id" 
                            class="list-group-item d-flex align-items-center gap-3 p-3 action-item"
                            @click="goUserDashboard(user.id)">
                            
                            <img :src="user.profileImgDirectory || 'https://raw.githubusercontent.com/twbs/icons/main/icons/person-circle.svg'" 
                                 class="rounded-circle border" 
                                 style="width: 50px; height: 50px; object-fit: cover;" 
                                 alt="프로필">
                            
                            <div class="flex-grow-1">
                                <div class="fw-bold">{{ user.nickname }}</div>
                                </div>

                            <i class="bi bi-chevron-right text-muted"></i>
                        </li>
                    </ul>
                    <div v-else class="text-center py-5 text-muted">
                        목록이 비어있습니다.
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
.action-item {
    cursor: pointer;
    transition: background-color 0.2s;
}
.action-item:hover {
    background-color: #f8f9fa;
}
</style>