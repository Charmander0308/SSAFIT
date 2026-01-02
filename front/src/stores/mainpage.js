import { defineStore } from 'pinia'
import http from '@/util/http-common'
import { ref } from 'vue'

export const useMainStore = defineStore('main', () => {
    const videos = ref([])
    const posts = ref([])
    const clearMain = () => {
        videos.value = []
        posts.value = []
        // isLoaded.value = false
    }
    const isLoaded = ref(false)

    const fetchMain = async () => {
    //    if (isLoaded.value) return
        
        try {
            const response = await http.get('/ai/main')
            videos.value = response.data.videoList
            posts.value = response.data.postList
        //    isLoaded.value = true
        } catch (err) {
            console.error("메인 데이터 로딩 실패", err)
        } 
    }

    return { 
        videos, 
        posts, 
        // isLoaded, 
        fetchMain, 
        clearMain }
})