<template>
  <div class="home">
    <div class="login">
      <div class="login-header"></div>
      <div class="login-form">
        <div class="login-title">
          <h5 class="login-title-text"><span>欢迎!</span> 您的到来</h5>
          <span class="login-subtitle-text">一款优质的存储程序</span>
        </div>

        <input class="form-input" v-model="mail" placeholder="Email" type="email">
        <input class="form-input" v-model="password" placeholder="Password" type="password">
        
        <div class="login-checkbox">
          <label class="user-agreement">
            <input type="checkbox" class="user-xy">
            <span class="user-xy-text">用户协议</span>
          </label>
          <a class="forgot-password-text" @click="getPassword">忘记密码？</a>
        </div>

        <div class="button-group">
          <button class="btn register-btn" @click="register">注 册</button>
          <button class="btn login-btn" @click="login">登 录</button>
        </div>
      </div>
      <div class="login-footer"></div>
    </div>
  </div>
</template>

<script setup lang="ts" name="LoginPage">
import { ref } from "vue"
import {RegisAPI_click} from "@/api/login/login"  // 重命名避免与函数名冲突

// 表单数据
const mail = ref("")
const password = ref("")

// 注册处理
function register() {
  RegisAPI_click.regis()
}

// 登录处理
function login() {
  RegisAPI_click.login({
    mail: mail.value,
    password: password.value
  })
}

// 忘记密码处理
function getPassword() {
  RegisAPI_click.get_password(mail.value)
}
</script>

<style scoped>
/* 基础布局 */
.home {
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 20px;
  background-color: #1E243D;
}

/* 登录容器 */
.login {
  max-width: 400px;
  min-width: 300px;
  width: 100%;  /* 响应式宽度 */
  margin: 10px;
  border-radius: 20px;
  display: flex;
  flex-direction: column;
  background-color: #232942;
  box-shadow: 0px 0px 20px #232942;
}

/* 登录容器头部和底部装饰 */
.login-header {
  width: 100%;
  height: 10px;
  background-color: #5A78B8;
  border-radius: 20px 20px 0 0;
}

.login-footer {
  width: 100%;
  height: 8px;
  background-color: #88BC80;
  border-radius: 0 0 20px 20px;
  margin-top: 50px;
}

/* 表单区域 */
.login-form {
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 20px;
}

/* 标题样式 */
.login-title {
  width: 100%;
  padding: 20px;
  margin-top: 50px;
  text-align: center;
}

.login-title-text {
  font-size: 30px;
  color: #fff;
  margin-bottom: 15px;
  font-family: 'Microsoft YaHei', sans-serif;
  font-weight: 200;
}

.login-title-text span {
  color: #3C4170;
  font-weight: 600;
}

.login-subtitle-text {
  font-size: 14px;
  color: #aaa8a8;
  font-weight: 200;
}

/* 输入框样式 */
.form-input {
  width: 100%;
  height: 50px;
  border-radius: 50px;
  margin-top: 25px;
  background-color: #252E49;
  box-shadow: 0px 0px 20px #23294200;
  outline: none;
  border: none;
  padding-left: 20px;
  color: #fff;
}

/* 复选框和忘记密码 */
.login-checkbox {
  width: 100%;
  display: flex;
  justify-content: space-between;  /* 修正为space-between */
  align-items: center;
  font-size: 14px;
  margin-top: 25px;
  margin-left: 15px;
}

.user-agreement {
  display: flex;
  align-items: center;
  color: #aaa8a8;
}

.user-xy {
  display: none;
}

.user-xy-text {
  display: none;
  margin-left: 10px;
}

.forgot-password-text {
  color: #aaa8a8;
  margin-right: 15px;
  text-align: right;
  cursor: pointer;  /* 增加指针样式 */
}

/* 按钮样式 */
.button-group {
  width: 100%;
  padding: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-top: 20px;
}

.btn {
  flex: 1;
  margin-top: 50px;
  height: 50px;
  border-radius: 50px;
  color: #fff;
  font-size: 15px;
  border: none;
  cursor: pointer;  /* 增加指针样式 */
  transition: all 0.3s ease;  /* 增加过渡效果 */
}

.register-btn {
  margin-right: 10px;
  background-color: rgba(255, 255, 255, 0.1);
  box-shadow: 0px 0px 0px rgba(255, 255, 255, 0.5);
}

.register-btn:hover {
  background-color: rgba(255, 255, 255, 0.2);  /* 悬停效果 */
}

.login-btn {
  margin-left: 10px;
  background-color: #7F5FEC;
  box-shadow: 0px 0px 10px #7F5FEC;
}

.login-btn:hover {
  box-shadow: 0px 0px 15px #7F5FEC;  /* 悬停增强阴影 */
}
</style>
