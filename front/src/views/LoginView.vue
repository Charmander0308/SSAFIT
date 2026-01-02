<script setup>
import { useUserStore } from '@/stores/user'
import { ref } from 'vue'

const userStore = useUserStore()

const userId = ref('')
const password = ref('')

const handleLogin = async () => {
  if (!userId.value || !password.value) {
    alert('아이디와 비밀번호를 입력해주세요.')
    return
  }

  await userStore.login({ 
    id: userId.value, 
    password: password.value 
  })
}
</script>

<template>
  <div class="d-flex flex-column min-vh-100 justify-content-center align-items-center">
    
    <div class="container flex-grow-1 d-flex flex-column align-items-center justify-content-center">
      <div class="row row-cols-1 mt-5 mb-5 justify-content-md-center w-100" style="max-width: 500px;">
        
        <form @submit.prevent="handleLogin" class="col-12">
          <h2 class="text-center mb-4">로그인</h2>

          <div class="mb-3">
            <label for="userId" class="form-label">아이디</label>
            <input 
              type="text" 
              class="form-control" 
              id="userId" 
              v-model="userId" 
              placeholder="아이디를 입력하세요"
            />
          </div>

          <div class="mb-3">
            <label for="password" class="form-label">비밀번호</label>
            <input 
              type="password" 
              class="form-control" 
              id="password" 
              v-model="password"
              placeholder="비밀번호를 입력하세요"
            />
          </div>

          <div class="mb-3 text-center d-grid gap-2 d-md-block">
            <button type="submit" class="btn btn-primary me-md-2">로그인</button>
            <RouterLink to="/" class="btn btn-outline-primary" role="button">처음으로</RouterLink>
          </div>
        </form>
      </div>

      <div class="mb-5">
        <RouterLink to="/signup" class="text-decoration-none text-dark">
          아직 회원이 아니라면? 회원가입하기
        </RouterLink>
      </div>
    </div>
  </div>
</template>

<style scoped>
</style>