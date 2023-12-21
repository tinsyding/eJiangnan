<template>
  <div class="container0">
    <div class="container1">
      <div class="form-and-image">
        <div class="form-content">
          <div class="logo-container">
            <img src="@/images/ejiangnan.png" alt="e江南logo" class="logo-image">
          </div>

          <el-form :model="data.form" :rules="formRules" ref="formRef">

            <el-form-item class="custom-margin" prop="username">
              <el-input prefix-icon="User" v-model="data.form.username" placeholder="请输入学号/工号" class="input-large" />
            </el-form-item>

            <el-form-item prop="password">
              <el-input show-password prefix-icon="Lock" v-model="data.form.password" placeholder="请输入新密码" class="input-large" />
            </el-form-item>

            <el-form-item>
              <el-button type="primary" class="custom-button large-button" @click="reset">重 置</el-button>
            </el-form-item>

          </el-form>

          <div class="login-link">
            <router-link to="/login" class="white-link">返回登录页面</router-link>
          </div>

        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref } from "vue";
import request from "@/utils/request";
import router from "@/router";
import { ElMessage } from "element-plus";

const data = reactive({
  form: { }
})

const formRules = reactive({
  username: [{ required: true, message: '请输入学号/工号', trigger: 'blur' }],
  password: [{ required: true, message: '请输入新密码', trigger: 'blur' }]
});

const formRef = ref();

// 处理登录逻辑
const reset = () => {
  formRef.value.validate(valid => {
    if (valid) {
      request.post('/reset', data.form).then(res => {
        if (res.code === '200') {
          ElMessage.success('重置成功，请登录')
          // 短暂延迟后跳转到登录页面
          setTimeout(() => {
            router.push('/login')
          }, 1000)
        } else {
          ElMessage.error(res.msg)
        }
      })
    } else {
      return false
    }
  })
}

</script>

<style scoped>
.container0 {
  min-height: 100vh;
  overflow: hidden;
  display: flex;
  align-items: center;
  justify-content: center;
  background-image: url('@/images/bg3.jpg');
  background-size: cover;
}

.container1 {
  width: auto;
  height: auto;
  background-color: rgba(0, 0, 0, 0.77);
  box-shadow: rgba(0, 0, 0, 0.6);
  padding: 30px;
  border-radius: 20px;
}

.logo-container {
  text-align: center;
  margin-bottom: 40px;
}

.logo-image {
  width: 200px;
  height: 60px;
  margin-bottom: -15px;
}

.input-large {
  font-size: 18px;
  height: 50px;
  margin-bottom: 10px;
}

.large-button {
  width: 100%;
  font-size: 20px;
  height: 55px;
}

.login-link {
  margin-top: 13px;
  text-align: right;
  color: #b6b6b6;
  font-size: 15px;
}

.white-link {
  color: #ffffff;
  text-decoration: none;
}

.custom-button {
  background-color: #002954 !important;
  border-color: #002954 !important;
}

.form-and-image {
  display: flex;
  align-items: center;
}

</style>
