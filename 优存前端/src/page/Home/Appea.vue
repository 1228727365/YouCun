<template>
  <div class="body">
    <!-- 修正拼写错误：heder -> header -->
    <div class="header">
      <!-- 用户个人信息卡片 -->
      <div v-for="(item, index) in AppeaAPI_click.useStatsStore().statsData" :key="index" class="info">
        <div class="info-card" :class="`info-card--${index + 1}`">
          <div class="info-title-card">
            <span class="info-title">{{ item.title }}</span>
            <p>
              <span>总</span>
              {{ item.value }}
              <span>{{ item.unit }}</span>
            </p>
          </div>
          <div class="info-db-card" :class="`info-db-card--${index + 1}`">
            <span>数据明细</span>
            <el-icon>
              <MoreFilled />
            </el-icon>
          </div>
        </div>
      </div>
    </div>

    <div class="body-app">
      <div id="app-card1">
        <div class="app-card-home"> 
          <h5>关于我们</h5>
          <span>主打文件安全管理与便捷存储。文档方面，支持高效保存文本，可加密设密、控制开关状态，且加密文档能单独设密；<br>图片存储则稳定支持多种格式，方便查看管理。界面简洁、操作便捷，适用于个人及小型团队。</span>
          <h5>开发团队</h5>
          <a href="">M (全栈开发)</a>
          <a href="">萧 (前端开发 UI设计)</a>
        </div>
      </div>
      <div id="app-card2"></div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted } from 'vue';
import router from '@/router';
import {AppeaAPI_click} from '@/api/home/appea/appea';
import type { CollapseModelValue } from 'element-plus'
import { MoreFilled } from '@element-plus/icons-vue'; // 显式导入图标



const handleChange = (val: CollapseModelValue) => {
  console.log(val)
}

onMounted(() => {
  const mail = localStorage.getItem("mail")
  const token = localStorage.getItem("token")
  console.log(mail, token)
  
  if (!mail || !token) {
    router.replace('/login'); // 替换当前路
    return;
  }
  
  AppeaAPI_click.init_get_user_info({
    mail: mail,
    token: token
  },AppeaAPI_click.useStatsStore().statsData)
  AppeaAPI_click.init_appea_info('app-card2', '用户数量', '图片数量', '文档数量')
});
</script>

<style scoped>
/* 基础布局样式 */
.body {
  width: 100%;
}

.header { /* 修正拼写错误 */
  width: 100%;
  display: flex;
  align-items: center;
  padding: 10px;
}

.body-app {
  width: 100%;
  padding: 10px;
  display: flex;
}

/* 信息卡片容器 */
.info {
  padding-left: 20px;
  padding-right: 20px;
  width: 100%;
  flex: 1;
}

/* 信息卡片基础样式（合并重复定义） */
.info-card {
  width: 300px;
  height: 200px;
  border-radius: 0px;
  display: flex;
  align-items: center;
  flex-direction: column;
}

/* 信息卡片主题样式（仅保留差异化样式） */
.info-card--1 {
  background-color: #232942;
  box-shadow: 0px 0px 5px #3C4170;
}

.info-card--2 {
  background-color: #0062ff;
  box-shadow: 0px 0px 5px #0062ff;
}

.info-card--3 {
  background-color: #0c9265;
  box-shadow: 0px 0px 5px #0c9265;
}

.info-card--4 {
  background-color: #F97316;
  box-shadow: 0px 0px 5px #F97316;
}

/* 数据明细卡片基础样式（合并重复定义） */
.info-db-card {
  width: 100%;
  margin-top: 15px;
  flex: 1;
  display: flex;
  align-items: center;
  border-radius: 0px;
}

.info-db-card span {
  flex: 1;
  margin-left: 20px;
  color: #ffffff;
}

.info-db-card .el-icon {
  margin-right: 20px;
  color: #ffffff;
}

/* 数据明细卡片主题样式 */
.info-db-card--1 {
  background-color: #3C4170;
  box-shadow: 0px 0px 5px #232942;
}

.info-db-card--2 {
  background-color: #3B82F6;
  box-shadow: 0px 0px 5px #3B82F6;
}

.info-db-card--3 {
  background-color: #10B981;
  box-shadow: 0px 0px 5px #10B981;
}

.info-db-card--4 {
  background-color: #f89853;
  box-shadow: 0px 0px 5px #f89853;
}

/* 卡片标题样式 */
.info-title-card {
  width: 100%;
  padding: 20px;
  color: white;
  font-size: 25px;
  font-family: 'Courier New', Courier, monospace;
}

.info-title-card p {
  font-size: 30px;
  margin-top: 20px;
  font-family: 'Courier New', Courier, monospace;
  font-weight: bold;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  color: #88BC80;
}

.info-title-card p span {
  color: white;
}

/* 关于我们卡片样式 */
.app-card-home {
  width: 700px;
  display: flex;
  border-radius: 10px;
  padding: 20px;
  background-color: white;
  box-shadow: 0px 0px 5px rgba(0, 0, 0, 0.1);
  flex-direction: column;
}

.app-card-home h5 {
  font-size: 20px;
}

.app-card-home span {
  font-size: 16px;
  margin: 10px 0 20px 10px;
}

.app-card-home a {
  color: #3B82F6;
  margin: 5px 0 0 10px;
  text-decoration: none;
}

/* 图表容器样式 */
#app-card1 {
  width: 100%;
  flex: 1;
  display: flex;
  height: 500px;
  margin-left: 20px;
  align-items: center;
}

#app-card2 {
  width: 100%;
  flex: 1;
  display: flex;
  height: 500px;
}

/* 折叠面板样式 */
.el-collapse-item {
  font-size: 16px;
}

.el-collapse {
  --el-collapse-content-font-size: 16px;
}

/* 统计图表子容器 */
#user_count,
#image_count,
#word_count {
  width: 100%;
  flex: 1;
}
</style>
