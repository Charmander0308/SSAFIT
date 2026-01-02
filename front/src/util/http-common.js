import axios from "axios"

const local = axios.create({
    baseURL: '/api',
    headers: {
        'Content-Type': 'application/json;charset=utf-8'
    }
})

local.interceptors.request.use(
  (config) => {
    // 로컬 스토리지에서 토큰을 꺼냅니다.
    const token = localStorage.getItem('accessToken')

    // 토큰이 있으면 헤더에 끼워 넣습니다.
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }

    return config
  },
  (error) => {
    // 요청 설정 중에 에러가 나면 여기서 잡습니다.
    return Promise.reject(error)
  }
)

export default local