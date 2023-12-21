<template>
  <div>
    <div class="header">
      <div class="logo-container">
        <img src="../images/logo.png" alt="" class="logo">
        <div class="brand">
          <div class="brand-name">e江南(parody)</div>
        </div>
      </div>
      <div class="user-info">
        <img src="../images/admin.ico" alt="" class="user-icon">
        <span>{{ user.name }}</span>
      </div>
    </div>

    <div class="content-container">
      <div class="sidebar">
        <el-menu router class="menu" :default-active="$route.path" :default-openeds="['/home']"> <!-- 通过$route.path获取当前路由路径     , '2', '3' -->
          <el-menu-item index="/home" v-if="user.role === 'ADMIN' || user.role === 'STUDENT'">
            <el-icon><HomeFilled /></el-icon>
            <span>系统首页</span>
          </el-menu-item>

          <el-menu-item index="/person" v-if="user.role === 'STUDENT'">
            <el-icon><User /></el-icon>
            <span>个人资料</span>
          </el-menu-item>

          <el-menu-item index="/doc" v-if="user.role === 'ADMIN' ">
            <el-icon><Files /></el-icon>
            <span>学校文件</span>
          </el-menu-item>

          <el-menu-item index="/doc" v-if="user.role === 'STUDENT' ">
            <el-icon><Files /></el-icon>
            <span>学校文件</span>
          </el-menu-item>

          <el-menu-item index="/course" v-if="user.role === 'ADMIN'">
            <el-icon><List /></el-icon>
            <span>课程信息</span>
          </el-menu-item>

          <el-menu-item index="/courselist" v-if="user.role === 'STUDENT'">
            <el-icon><List /></el-icon>
            <span>学生选课</span>
          </el-menu-item>

          <el-menu-item index="/studentCourse" v-if="user.role === 'ADMIN' || user.role === 'STUDENT'">
            <el-icon><Document /></el-icon>
            <span>选课记录</span>
          </el-menu-item>

          <el-menu-item index="/student" v-if="user.role === 'ADMIN'">
            <el-icon><UserFilled /></el-icon>
            <span>学生管理</span>
          </el-menu-item>

          <el-menu-item index="login" @click="logout">
            <el-icon><SwitchButton /></el-icon>
            <span>退出系统</span>
          </el-menu-item>
        </el-menu>
      </div>

      <div class="main-content">
        <router-view />
      </div>
    </div>
  </div>
</template>


<script setup>
import { useRoute } from 'vue-router'
import {HomeFilled, User, SwitchButton, Files, List, Document} from "@element-plus/icons-vue";
import {UserFilled} from "@element-plus/icons-vue";
const $route = useRoute()

const user = JSON.parse(localStorage.getItem('student-user') || '{}')

const logout = () => {
  localStorage.removeItem('student-user')
}
</script>


<style scoped>

.header {
  height: 60px;
  background-color: #fff;
  display: flex;
  align-items: center;
  border-bottom: 1px solid #ddd;
}

.logo-container {
  flex: 1;
  padding-left: 20px;
  display: flex;
  align-items: center;
}

.logo {
  width: 40px;
}

.brand {
  font-weight: bold;
  font-size: 24px;
  margin-left: 5px;
}

.user-info {
  width: fit-content;
  padding-right: 10px;
  display: flex;
  align-items: center;
}

.user-icon {
  width: 40px;
  height: 40px;
}

.content-container {
  display: flex;
}

.sidebar {
  width: 200px;
  border-right: 1px solid #ddd;
  min-height: calc(100vh - 60px);
}

.menu {
  border: none;
}

.main-content {
  flex: 1;
  width: 0;
  padding: 10px;
  background-color: #fff;
  background-size: cover;
}

:deep(th) {
  color: #333;
}
</style>