<template>
  <div class="main-container" v-if="isUserOrAdmin">
    <h1>
      您好，江南大学
      <span :style="{ color: user.role === 'ADMIN' ? '#35acf8' : '#ff9900' }">
        {{ user.role === 'ADMIN' ? '管理员' : '学生' }}
      </span>
      <br>
      <span :style="{ color: '#002594' }">
        {{ user.name }}
      </span>, 欢迎您!
    </h1>
  </div>

  <!-- 如果既不是用户也不是管理员，就不显示-->
  <div class="main-container" v-else>
    <h1>您不是江南大学成员，请退出系统</h1>
  </div>
</template>

<script setup>
const user = JSON.parse(localStorage.getItem('student-user') || '{}');
const isUserOrAdmin = ['ADMIN', 'STUDENT'].includes(user.role);
</script>

<style>
.main-container {
  text-align: center;
}
</style>
