<script setup>
import { ref, onMounted } from 'vue'
import { Modal } from 'bootstrap'

const props = defineProps({
    challengeData: {
        type: Object,
        default: () => ({
            achieved: [],
            challenging: []
        })
    },
})

const modalRef = ref(null)
let modalInstance = null

const show = () => {
    if (modalInstance) {
        modalInstance.show()
    }
}

const hide = () => {
    if (modalInstance) {
        modalInstance.hide()
    }
}

onMounted(() => {
    modalInstance = new Modal(modalRef.value)
})

defineExpose({
    show,
    hide
})

// 날짜 변환 함수 (2025-01-02T... -> 2025. 1. 2.)
const formatDate = (dateString) => {
    if (!dateString) return ''
    const date = new Date(dateString)
    return date.toLocaleDateString('ko-KR', {
        year: 'numeric',
        month: 'numeric',
        day: 'numeric'
    })
}
</script>

<template>
    <div class="modal fade" ref="modalRef" id="challengeModal" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable modal-lg"> <div class="modal-content">
                
                <div class="modal-header border-bottom-0">
                    <h5 class="modal-title fw-bold">🏆 도전과제 현황</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>

                <div class="modal-body p-4">
                    
                    <div class="mb-5">
                        <h5 class="fw-bold text-success mb-3">
                            <i class="bi bi-check-circle-fill me-2"></i>달성한 업적
                        </h5>
                        
                        <div v-if="challengeData.achieved && challengeData.achieved.length > 0" class="row g-3">
                            <div class="col-12" v-for="item in challengeData.achieved" :key="item.id">
                                <div class="card border shadow-sm h-100">
                                    <div class="card-body">
                                        <div class="d-flex align-items-center">
                                            <div class="flex-shrink-0 me-3">
                                                <img :src="item.challengeIconDirectory" 
                                                     alt="아이콘" 
                                                     class="rounded-circle border p-1 bg-light"
                                                     style="width: 60px; height: 60px; object-fit: contain;">
                                            </div>
                                            <div class="flex-grow-1">
                                                <div class="d-flex justify-content-between align-items-start">
                                                    <div>
                                                        <h6 class="fw-bold mb-1">{{ item.challengeName }}</h6>
                                                        <p class="text-muted small mb-2">{{ item.challengeInfo }}</p>
                                                    </div>
                                                    <span class="badge bg-success-subtle text-success border border-success-subtle rounded-pill">Completed</span>
                                                </div>
                                                <div class="text-end border-top pt-2 mt-1">
                                                    <small class="text-secondary">
                                                        달성일: <span class="fw-medium text-dark">{{ formatDate(item.achieveDate) }}</span>
                                                    </small>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div v-else class="text-center py-4 text-muted bg-light rounded">
                            아직 달성한 업적이 없습니다. 첫 발걸음을 떼보세요!
                        </div>
                    </div>

                    <hr class="my-4 border-secondary-subtle">

                    <div>
                        <h5 class="fw-bold text-primary mb-3">
                            <i class="bi bi-flag-fill me-2"></i>진행 중인 업적
                        </h5>

                        <div v-if="challengeData.challenging && challengeData.challenging.length > 0" class="row g-3">
                            <div class="col-12" v-for="item in challengeData.challenging" :key="item.id">
                                <div class="card border shadow-sm h-100">
                                    <div class="card-body">
                                        <div class="d-flex align-items-center">
                                            <div class="flex-shrink-0 me-3">
                                                <img :src="item.challengeIconDirectory" 
                                                     alt="아이콘" 
                                                     class="rounded-circle border p-1 bg-light grayscale-img"
                                                     style="width: 60px; height: 60px; object-fit: contain;">
                                            </div>
                                            <div class="flex-grow-1">
                                                <h6 class="fw-bold mb-1">{{ item.challengeName }}</h6>
                                                <p class="text-muted small mb-2">{{ item.challengeInfo }}</p>
                                                
                                                <div class="position-relative mt-2">
                                                    <div class="progress" style="height: 20px;">
                                                        <div class="progress-bar progress-bar-striped progress-bar-animated bg-primary" 
                                                             role="progressbar" 
                                                             :style="{ width: item.achievementRate + '%' }" 
                                                             :aria-valuenow="item.achievementRate" 
                                                             aria-valuemin="0" 
                                                             aria-valuemax="100">
                                                        </div>
                                                    </div>
                                                    <div class="position-absolute top-0 start-0 w-100 h-100 d-flex justify-content-center align-items-center">
                                                        <small class="fw-bold text-shadow" 
                                                               :class="item.achievementRate > 50 ? 'text-white' : 'text-dark'">
                                                            {{ item.achievementRate }}%
                                                        </small>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div v-else class="text-center py-4 text-muted bg-light rounded">
                            모든 업적을 달성하셨습니다! 대단해요! 🎉
                        </div>
                    </div>

                </div> </div>
        </div>
    </div>
</template>

<style scoped>
/* 미달성 아이콘을 약간 흐리게 처리 */
.grayscale-img {
    filter: grayscale(100%);
    opacity: 0.7;
}

.text-shadow {
    text-shadow: 0px 0px 2px rgba(255, 255, 255, 0.8);
}

.progress-bar-animated + .position-absolute .text-white {
    text-shadow: 0px 0px 2px rgba(0, 0, 0, 0.5);
}
</style>