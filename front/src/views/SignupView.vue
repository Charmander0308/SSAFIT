<script setup>
import { ref, computed, reactive } from 'vue'
import { useRouter } from 'vue-router'
import local from '@/util/http-common'
import { useBaseStore } from '@/stores/base'

const router = useRouter()

const baseStore = useBaseStore()

// 현재 단계 관리
const currentStep = ref(1)

// 입력 데이터
const form = reactive({
  userId: '',
  userPw: '',
  name: '',
  nickname: '',
  email: '',
  birth: '',
  category: []
})

// 에러메시지 + 유효성 상태 관리
const status = reactive({
  userId: { message: '', isValid: false },
  userPw: { message: '', isValid: false },
  name: { message: '', isValid: false },
  nickname: { message: '', isValid: false },
  email: { message: '', isValid: false },
  birth: { message: '', isValid: false }
})

// 아이디 검사
const validateId = () => {
  const idRegex = /^[a-zA-Z0-9]+$/
  if (!idRegex.test(form.userId) || form.userId.length < 6) {
    status.userId.message = "아이디는 대·소문자, 숫자만으로 구성된, 6글자 이상이어야 합니다."
    status.userId.isValid = false
    return false
  } else {
    status.userId.message = ""
    status.userId.isValid = true
    return true
  }
}

// 비밀번호 검사 (대소문자+숫자+특수문자, 8자 이상)
const validatePw = () => {
  const pwRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{8,}$/
  if (!pwRegex.test(form.userPw) || form.userPw.length < 8) {
    status.userPw.message = "비밀번호는 대·소문자, 숫자, 특수문자를 모두 포함, 8자리 이상이어야 합니다."
    status.userPw.isValid = false
    return false
  } else {
    status.userPw.message = ""
    status.userPw.isValid = true
    return true
  }
}

// 이름 검사
const validateName = () => {
  if (form.name.trim() === "" || form.name.length < 2) {
    status.name.message = "이름을 입력해주세요."
    status.name.isValid = false
    return false
  } else {
    status.name.message = ""
    status.name.isValid = true
    return true
  }
}

// 닉네임 검사
const validateNickname = () => {
  if (form.nickname.trim() === "" || form.nickname.length < 2 || form.nickname.length > 8) {
    status.nickname.message = "닉네임은 2글자 이상, 8글자 이하로 입력해주세요."
    status.nickname.isValid = false
    return false
  } else {
    status.nickname.message = ""
    status.nickname.isValid = true
    return true
  }
}

// 이메일 검사
const validateEmail = () => {
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
  if (!emailRegex.test(form.email)) {
    status.email.message = "올바른 이메일 형식이 아닙니다."
    status.email.isValid = false
    return false
  } else {
    status.email.message = ""
    status.email.isValid = true
    return true
  }
}

// 생년월일 검사
const validateBirth = () => {
  if (form.birth === "") {
    status.birth.message = "생년월일을 선택해주세요."
    status.birth.isValid = false
    return false
  } else {
    status.birth.message = ""
    status.birth.isValid = true
    return true
  }
}

const nextStep = () => {
  // 모든 검증 함수 실행
  const v1 = validateId()
  const v2 = validatePw()
  const v3 = validateName()
  const v4 = validateNickname()
  const v5 = validateEmail()
  const v6 = validateBirth()

  // 전부 통과해야 다음으로 이동
  if (v1 && v2 && v3 && v4 && v5 && v6) {
    currentStep.value = 2
  } else {
    alert("입력 정보를 다시 확인해주세요.")
  }
}

const prevStep = () => {
  currentStep.value = 1
}

// 회원가입 요청 
const submitSignup = async () => {
  try {
    await local.post('/member/signup', form)

    currentStep.value = 3
  } catch (error) {
    console.error(error)
    alert('회원가입 중 오류가 발생했습니다.')
  }
}

const progressWidth = computed(() => {
  if (currentStep.value === 1) return '33%'
  if (currentStep.value === 2) return '66%'
  return '100%'
})

</script>

<template>
  <div class="d-flex flex-column min-vh-100">
    <div class="container-sm border p-3 mt-5" style="max-width: 800px;">
      
      <div class="row row-cols-1 justify-content-center flex-column align-items-center mb-4">
        <div class="col col-12 col-md-8">
          <div class="progress" role="progressbar" style="height: 10px">
            <div class="progress-bar transition-all" :style="{ width: progressWidth }"></div>
          </div>
        </div>
        <div class="col col-12 col-md-8 mt-2">
          <div class="text-center text-nowrap">
            <p class="text-primary fw-bold">
              <span v-if="currentStep === 1">1. 기본 정보 입력</span>
              <span v-else-if="currentStep === 2">2. 관심항목 선택</span>
              <span v-else>3. 가입 완료</span>
            </p>
          </div>
        </div>
      </div>
      
      <div v-if="currentStep === 1" class="step-container fade-in">
        <div class="row row-cols-1 row-cols-md-2 g-4 px-4 justify-content-center align-items-center">
          
          <div class="col">
            <div class="form-floating has-validation">
              <input 
                type="text" 
                class="form-control" 
                :class="{ 'is-invalid': status.userId.message, 'is-valid': status.userId.isValid }"
                id="userId" 
                v-model="form.userId" 
                @input="validateId"
                placeholder="아이디"
              >
              <label for="userId">아이디</label>
              <div class="invalid-feedback">{{ status.userId.message }}</div>
            </div>
          </div>

          <div class="col">
            <div class="form-floating has-validation">
              <input 
                type="password" 
                class="form-control"
                :class="{ 'is-invalid': status.userPw.message, 'is-valid': status.userPw.isValid }" 
                id="password" 
                v-model="form.userPw" 
                @input="validatePw"
                placeholder="비밀번호"
              >
              <label for="userPw">비밀번호</label>
              <div class="invalid-feedback">{{ status.userPw.message }}</div>
            </div>
          </div>

          <div class="col">
            <div class="form-floating has-validation">
              <input 
                type="text" 
                class="form-control" 
                :class="{ 'is-invalid': status.name.message, 'is-valid': status.name.isValid }"
                id="username" 
                v-model="form.name" 
                @input="validateName"
                placeholder="이름"
              >
              <label for="name">이름</label>
              <div class="invalid-feedback">{{ status.name.message }}</div>
            </div>
          </div>

          <div class="col">
            <div class="form-floating has-validation">
              <input 
                type="text" 
                class="form-control" 
                :class="{ 'is-invalid': status.nickname.message, 'is-valid': status.nickname.isValid }"
                id="nickname" 
                v-model="form.nickname" 
                @input="validateNickname"
                placeholder="닉네임"
              >
              <label for="nickname">닉네임</label>
              <div class="invalid-feedback">{{ status.nickname.message }}</div>
            </div>
          </div>

          <div class="col">
            <div class="form-floating has-validation">
              <input 
                type="email" 
                class="form-control" 
                :class="{ 'is-invalid': status.email.message, 'is-valid': status.email.isValid }"
                id="email" 
                v-model="form.email" 
                @input="validateEmail"
                placeholder="이메일"
              >
              <label for="email">이메일</label>
              <div class="invalid-feedback">{{ status.email.message }}</div>
            </div>
          </div>

          <div class="col">
            <div class="form-floating has-validation">
              <input 
                type="date" 
                class="form-control" 
                :class="{ 'is-invalid': status.birth.message, 'is-valid': status.birth.isValid }"
                id="birth" 
                v-model="form.birth" 
                @input="validateBirth"
                placeholder="생년월일"
              >
              <label for="birth">생년월일</label>
              <div class="invalid-feedback">{{ status.birth.message }}</div>
            </div>
          </div>
        </div>

        <div class="row mt-5 mb-3 justify-content-center">
          <div class="col-4 text-center">
            <button type="button" class="btn btn-primary px-5 py-2" @click="nextStep">다음</button>
          </div>
        </div>
      </div>

      <div v-if="currentStep === 2" class="step-container fade-in d-flex flex-column align-items-center justify-content-center">
        <h4 class="mb-4">관심 있는 분야를 선택해주세요 (다중 선택 가능)</h4>
        <div class="row row-cols-2 row-cols-md-3 g-4 m-3 w-100">
          <div class="col" v-for="(item, index) in baseStore.categoryDropdown" :key="item.id">
            <div class="form-check">
              <input 
                class="form-check-input" 
                type="checkbox" 
                :value="item.id" 
                :id="`c_${index}`" 
                v-model="form.category"
              >
              <label class="form-check-label" :for="`c_${index}`">{{ item.value }}</label>
            </div>
          </div>
        </div>
        <div class="text-center mt-5 mb-3">
          <button type="button" class="btn btn-secondary me-2 px-4" @click="prevStep">이전</button>
          <button type="button" class="btn btn-primary px-4" @click="submitSignup">가입완료</button>
        </div>
      </div>

      <div v-if="currentStep === 3" class="step-container fade-in d-flex flex-column align-items-center justify-content-center py-5">
        <div class="text-center m-5">
          <h2 class="display-6 mb-4">🎉 환영합니다!</h2>
          <p class="lead">회원가입이 성공적으로 완료되었습니다.</p>
        </div>
        <div class="m-3">
          <button type="button" class="btn btn-primary px-5 py-2" @click="router.push('/login')">로그인 하러 가기</button>
        </div>
      </div>

    </div>
  </div>
</template>

<style scoped>
.transition-all { 
    transition: all 0.5s ease; 
}
.fade-in { 
    animation: fadeIn 0.5s; 
}
@keyframes fadeIn {
    from { 
        opacity: 0; 
        transform: translateY(10px); 
    } 
    to {
        opacity: 1; 
        transform: translateY(0); 
    } 
}
.invalid-feedback { 
    display: block; 
}
</style>