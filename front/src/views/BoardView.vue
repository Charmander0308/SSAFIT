<template>
  <div>
    <the-header class="container-fluid" />
    <main>
      <div class="h-100 d-flex justify-content-center align-items-center">
        <div class="container col-lg-8 col-10">
          <router-view></router-view>
        </div>
      </div>
    </main>
    <the-footer />
  </div>
</template>

<script setup>
import TheHeader from '@/components/TheHeader.vue'
import TheFooter from '@/components/TheFooter.vue'
</script>

<style>

</style>
