<script setup>
import { ref, computed, onMounted } from 'vue'
import { useUserStore } from '@/stores/user'
import { useBaseStore } from '@/stores/base'
import { Modal } from 'bootstrap'
import local from '@/util/http-common'

const userStore = useUserStore()
const baseStore = useBaseStore()

const modalElement = ref(null)
let modalInstance = null

const fetchDetailInfo = async () => {
    if(!userStore.userInfo) return

    try {
        const res = await local.get('/member/mypage', { 
            headers: {
                Authorization: `Bearer ${userStore.accessToken}` 
            }
        })
        userStore.userInfo = { ...userStore.userInfo, ...res.data }
        console.log(res.data)
    } catch (err) {
        console.error("마이페이지 로딩 실패", err)
    }
}

const interestLabels = computed(() => {
    return userStore.userInfo?.interests || []
})

onMounted(() => {
  modalInstance = new Modal(modalElement.value)
})

const show = () => {
    fetchDetailInfo()
  modalInstance.show()
}
const hide = () => {
  modalInstance.hide()
}

defineExpose({ show, hide })
</script>

<template>
  <div class="modal fade" ref="modalElement" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
        
        <div class="modal-header">
          <h5 class="modal-title fw-bold">마이페이지</h5>
          <button type="button" class="btn-close" @click="hide"></button>
        </div>
        
        <div class="modal-body" v-if="userStore.userInfo">
          <div class="row">
            <aside class="col-md-4 text-center d-flex flex-column align-items-center justify-content-center">
              <figure class="d-flex flex-column align-items-center mb-0">
                <img 
                  :src="userStore.userInfo.profileImgDirectory || '/assets/img/userProfile/default_profile.png'" 
                  alt="profile image" 
                  class="img-fluid rounded-circle mb-3 border"
                  style="width: 100px; height: 100px; object-fit: cover;"
                >
                <div class="badge px-3 py-2" :class="userStore.userRankInfo.class">{{userStore.userRankInfo.value}}</div>
              </figure>
            </aside>
            
            <section class="col-md-8">
              <div class="row g-2">
                <div class="col-6">
                  <div class="text-secondary small mb-1">이름</div>
                  <div class="border rounded p-2 text-center bg-light">
                    {{ userStore.userInfo.name }}
                  </div>
                </div>
                <div class="col-6">
                  <div class="text-secondary small mb-1">닉네임</div>
                  <div class="border rounded p-2 text-center bg-light">
                    {{ userStore.userInfo.nickname }}
                  </div>
                </div>
                
                <div class="col-6">
                  <div class="text-secondary small mb-1">생년월일</div>
                  <div class="border rounded p-2 text-center bg-light">
                    {{ userStore.userInfo.birth }}
                  </div>
                </div>
                <div class="col-6">
                  <div class="text-secondary small mb-1">가입일</div>
                  <div class="border rounded p-2 text-center bg-light">
                    {{ userStore.userInfo.signdate || '1010.01.01' }}
                  </div>
                </div>
                
                <div class="col-12">
                  <div class="text-secondary small mb-1">이메일</div>
                  <div class="border rounded p-2 text-center bg-light text-truncate">
                    {{ userStore.userInfo.email }}
                  </div>
                </div>

                <div class="col-12">
                  <div class="text-secondary small mb-1">관심 항목</div>
                  <div class="d-flex flex-wrap gap-2 p-2 border rounded bg-light min-h-50">
                    <span 
                      v-for="interest in interestLabels" 
                      :key="interest"
                      class="user-interest badge border rounded-pill text-dark fw-normal px-3 py-2"
                    >
                      {{ interest }}
                    </span>
                    <span v-if="interestLabels.length === 0" class="text-muted small">
                      선택된 관심사가 없습니다.
                    </span>
                  </div>
                </div>
              </div>
            </section>
          </div>
        </div>

        <div class="modal-body text-center p-5" v-else>
            <p>로그인 정보가 없습니다.</p>
        </div>

      </div>
    </div>
  </div>
</template>

<style scoped>
.user-interest {
  background-color: #ffffff;
  border-color: #dee2e6 !important;
}

.min-h-50 {
    min-height: 50px;
}
</style>