<template>
  <div class="home">
    <div class="login">
      <div class="login-header"></div>
      <div class="login-form">
        <div class="login-title">
          <h5 class="login-title-text"><span>欢迎!</span> 您的到来</h5>
          <span class="login-subtitle-text">一款优质的存储程序</span>
        </div>

        <!-- 表单输入框 -->
        <input class="form-input" placeholder="Email" type="email" v-model="mail">
        <input class="form-input" placeholder="Password" type="password" v-model="password1">
        <input class="form-input" placeholder="Duplicate passwords" type="password" v-model="password2">
        
        <!-- 验证码区域 -->
        <div class="code-group">
          <input class="form-input code-input" placeholder="Code" type="text" v-model="code" maxlength="6">
          <button ref="getcodebutton" class="btn send-code-btn" @click="getCode">发送验证码</button>
        </div>
        
        <!-- 协议勾选 -->
        <div class="login-checkbox">
          <label class="agreement-label">
            <input class="agreement-checkbox" type="checkbox">
            <span class="agreement-text">注册表示同意用户协议</span>
          </label>
        </div>

        <!-- 操作按钮 -->
        <div class="button-group">
          <button class="btn login-btn" @click="login_button">登 录</button>
          <button class="btn register-btn" @click="register_button">注 册</button>
        </div>
      </div>
      <div class="login-footer"></div>
    </div>
  </div>
</template>

<script setup lang="ts" name="RegisterPage">
import { ref } from "vue";
import { RegisAPI_click } from "../api/regis/regis"; // 重命名API避免歧义

// 表单数据
const mail = ref("")
const password1 = ref("")
const password2 = ref("")
const code = ref("")
const getcodebutton = ref<HTMLButtonElement | null>(null);

// 验证码获取
function getCode() {
  RegisAPI_click.getCode(mail.value, getcodebutton.value)
}

// 登录跳转
function login_button() {
  RegisAPI_click.login()
}

// 注册提交
function register_button() {
  RegisAPI_click.regis({
    mail: mail.value,
    password: password1.value,
    code: code.value
  },password2.value)
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
  width: 100%; /* 响应式宽度 */
  margin: 10px;
  border-radius: 20px;
  display: flex;
  flex-direction: column;
  background-color: #232942;
  box-shadow: 0px 0px 20px #232942;
}

/* 容器装饰 */
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
  margin-top: 30px;
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

/* 输入框基础样式 */
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
  box-sizing: border-box; /* 确保padding不影响总宽度 */
}

/* 验证码区域 */
.code-group {
  width: 100%;
  display: flex;
  align-items: center;
  gap: 15px; /* 替代margin-left */
}

.code-input {
  flex: 2; /* 调整输入框占比 */
  margin-top: 25px;
}

/* 按钮基础样式 */
.btn {
  height: 50px;
  border-radius: 50px;
  color: #fff;
  font-size: 15px;
  border: none;
  cursor: pointer;
  transition: all 0.3s ease; /* 平滑过渡效果 */
}

/* 发送验证码按钮 */
.send-code-btn {
  flex: 1;
  margin-top: 25px;
  background-color: #7F5FEC;
  box-shadow: 0px 0px 10px #7F5FEC;
}

.send-code-btn:hover {
  box-shadow: 0px 0px 15px #7F5FEC; /* 悬停增强效果 */
}

/* 按钮组 */
.button-group {
  width: 100%;
  padding: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-top: 20px;
  gap: 10px; /* 替代margin左右 */
}

.login-btn {
  flex: 1;
  margin-top: 20px;
  background-color: rgba(255, 255, 255, 0.1);
}

.login-btn:hover {
  background-color: rgba(255, 255, 255, 0.2); /* 悬停效果 */
}

.register-btn {
  flex: 1;
  margin-top: 20px;
  background-color: #7F5FEC;
  box-shadow: 0px 0px 10px #7F5FEC;
}

.register-btn:hover {
  box-shadow: 0px 0px 15px #7F5FEC; /* 悬停增强效果 */
}

/* 协议勾选区域 */
.login-checkbox {
  width: 100%;
  display: flex;
  align-items: center;
  margin-top: 25px;
  margin-left: 15px;
}

.agreement-label {
  display: flex;
  align-items: center;
  cursor: pointer;
}

.agreement-checkbox {
  display: none;
}

.agreement-text {
  display: none;
  color: #aaa8a8;
  font-size: 14px;
  margin-left: 10px;
}
</style>
