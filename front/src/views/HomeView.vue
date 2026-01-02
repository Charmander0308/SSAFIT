<script setup>
import { onMounted, ref } from 'vue'
import Chart from 'chart.js/auto' 
import { useMainStore } from '@/stores/mainpage'
const mainStore = useMainStore()

const chartCanvas1 = ref(null)
const chartCanvas2 = ref(null)

const logoSrc = new URL('@/assets/img/logo.png', import.meta.url).href
const imgShare = new URL('@/assets/img/img_index_share_stories.png', import.meta.url).href
const imgAchieve = new URL('@/assets/img/img_index_achieve_fitness.png', import.meta.url).href
const imgInspire = new URL('@/assets/img/img_index_inspire_together.png', import.meta.url).href

onMounted(() => {
  createCharts()
  mainStore.fetchMain()
})

const createCharts = () => {
  if (chartCanvas1.value) {
    new Chart(chartCanvas1.value, {
      type: 'bar',
      data: {
        labels: ['Life Expectancy (+4%)'],
        datasets: [
          {
            label: 'Non-Exerciser',
            data: [78],
            backgroundColor: 'rgba(201, 203, 207, 0.8)',
            borderWidth: 0
          },
          {
            label: '1-Year Exerciser',
            data: [82],
            backgroundColor: 'rgba(54, 162, 235, 0.8)', 
            borderWidth: 0
          }
        ]
      },
      options: {
        responsive: true,
        aspectRatio: 1.5,
        plugins: {
          legend: {
            labels: { font: { size: 14 } }
          }
        },
        scales: {
          x: {
            ticks: { font: { size: 16, weight: 'bold' } },
            grid: { display: false }
          },
          y: {
            beginAtZero: true,
            grid: { display: false } 
          }
        }
      }
    })
  }

  // 두 번째 차트: Work Productivity
  if (chartCanvas2.value) {
    new Chart(chartCanvas2.value, {
      type: 'bar',
      data: {
        labels: ['Work Productivity (+21%)'],
        datasets: [
          {
            label: 'Non-Exerciser',
            data: [100],
            backgroundColor: 'rgba(201, 203, 207, 0.8)', 
            borderWidth: 0
          },
          {
            label: '1-Year Exerciser',
            data: [121],
            backgroundColor: 'rgba(255, 99, 132, 0.8)', 
            borderWidth: 0
          }
        ]
      },
      options: {
        responsive: true,
        aspectRatio: 1.5,
        plugins: {
          legend: {
            labels: { font: { size: 14 } }
          }
        },
        scales: {
          x: {
            ticks: { font: { size: 16, weight: 'bold' } },
            grid: { display: false }
          },
          y: {
            beginAtZero: true,
            grid: { display: false }
          }
        }
      }
    })
  }

}
</script>

<template>
  <div class="home-container">
    <div class="container-fluid min-vh-100 d-flex flex-column align-items-center justify-content-center">
      <div class="row row-cols-1 gy-5 mt-5 mb-5 align-items-center justify-content-md-center">
        <div class="col col-12 col-md-6 text-center">
          <img :src="logoSrc" class="img-fluid" alt="SSAFIT Logo">
        </div>
        <div class="col col-12 col-md-6 text-center">
          <h4 class="display-4 p-3">Share Stories, Achieve Fitness, Inspire Together</h4>
          <p class="lead p-3">
            서로의 이야기를 공유하고 동기부여를 얻으세요.<br>
            SSAFIT에서 건강한 일상이 시작됩니다.
          </p>
        </div>
      </div>
      <div class="row row-cols-1 gy-5 mt-5 mb-5 justify-content-md-center">
        <div class="col text-center">
          <RouterLink 
            to="/login" 
            class="btn btn-primary btn-lg px-5 py-3 fs-3 fw-bold shadow"
            role="button"
          >
            시작하기
          </RouterLink>
        </div>
      </div>
    </div>

    <div class="container-fluid p-5 mt-5 mb-5 bg-light">
      <div class="row row-cols-1 gy-5 mt-5 mb-5 align-items-center justify-content-md-center">
        <div class="col col-12 col-md-6 text-center">
          <img :src="imgShare" class="img-fluid" alt="Share Stories">
        </div>
        <div class="col col-12 col-md-6 text-center">
          <h2 class="display-2 p-2">Share Stories</h2>
          <p class="lead p-2">
            다양한 사람들의 이야기를 들어보세요.<br>
            그리고, 당신의 이야기도 공유해보세요.
          </p>
        </div>
      </div>
    </div>

    <div class="container-fluid p-5 mt-5 mb-5">
      <div class="row row-cols-1 gy-5 mt-5 mb-5 align-items-center justify-content-md-center">
        <div class="col col-12 col-md-6 text-center">
          <h2 class="display-2 p-2">Achieve Fitness</h2>
          <p class="lead p-2">
            당신만의 목표를 세우고, 매일 달성해보세요.<br>
            SSAFIT은 어떤 목표든 도움이 될 수 있습니다.
          </p>
        </div>
        <div class="col col-12 col-md-6 text-center">
          <img :src="imgAchieve" class="img-fluid" alt="Achieve Fitness">
        </div>
      </div>
    </div>

    <div class="container-fluid p-5 mt-5 mb-5 bg-light">
      <div class="row row-cols-1 gy-5 mt-5 mb-5 align-items-center justify-content-md-center">
        <div class="col col-12 col-md-6 text-center">
          <img :src="imgInspire" class="img-fluid" alt="Inspire Together">
        </div>
        <div class="col col-12 col-md-6 text-center">
          <h2 class="display-2 p-2">Inspire Together</h2>
          <p class="lead p-2">
            사람들과 함께하며 영감을 얻으세요.<br>
            당신의 목표에 더욱 가까워질 것입니다.
          </p>
        </div>
      </div>
    </div>

    <div class="container-fluid p-5 mt-5 mb-5">
      <div class="row gy-5 mt-5 mb-5 text-center">
        <div class="col">
          <h2 class="display-2 p-2">Why It Matters?</h2>
          <p class="lead p-2">
            SSAFIT은 당신과 함께 할 준비가 되었습니다.<br>
            지금 시작해보세요.
          </p>
        </div>
      </div>
      <div class="row row-cols-2 gy-5 mt-5 mb-5 align-items-center justify-content-md-center">
        <div class="col col-12 col-md-6 d-flex justify-content-md-center">
          <canvas ref="chartCanvas1"></canvas>
        </div>
        <div class="col col-12 col-md-6 d-flex justify-content-md-center">
          <canvas ref="chartCanvas2"></canvas>
        </div>
      </div>
    </div>

    <TheFooter />
  </div>
</template>

<style scoped>

</style>