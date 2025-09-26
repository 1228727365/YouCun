<template>
    <div class="home-container">
        <!-- 侧边导航栏 -->
        <el-menu class="sidebar-menu" active-text-color="#7F5FEC" background-color="#252E49" text-color="#fff"
            :default-active="defaultActive" :collapse="isCollapse" @open="handleMenuOpen" @close="handleMenuClose"
            @select="handleMenuSelect" router>
            <!-- Logo -->
            <el-menu-item class="brand-item">
                <img class="logo-img" src="../assets/logo.svg" alt="优存Logo">
                <span class="brand-name" v-if="!isCollapse">优 存</span>
            </el-menu-item>

            <!-- 导航菜单 -->
            <el-sub-menu index="Appea">
                <template #title>
                    <el-icon>
                        <Stopwatch />
                    </el-icon>
                    <span>仪表盘</span>
                </template>
                <el-menu-item index="Appea" :route="{ path: '/home/appea' }">
                    用户存储表
                </el-menu-item>
            </el-sub-menu>

            <el-menu-item index="Word" :route="{ path: '/home/word' }">
                <el-icon>
                    <Tickets />
                </el-icon>
                <span>文档页</span>
            </el-menu-item>

            <el-menu-item index="Image" :route="{ path: '/home/image' }">
                <el-icon>
                    <FolderOpened />
                </el-icon>
                <span>图片页</span>
            </el-menu-item>

            <el-menu-item index="Setting" :route="{ path: '/home/setting' }">
                <el-icon>
                    <Setting />
                </el-icon>
                <span>设置页</span>
            </el-menu-item>
        </el-menu>

        <!-- 主内容区域 -->
        <div class="main-content">
            <!-- 头部导航 -->
            <header class="page-header">
                <!-- 折叠/展开按钮 -->
                <el-icon class="toggle-sidebar" @click="toggleSidebar" color="#fff">
                    <Fold v-if="!isCollapse" />
                    <Expand v-else />
                </el-icon>

                <!-- 面包屑导航 -->
                <el-breadcrumb class="breadcrumb-nav" :separator-icon="ArrowRight">
                    <el-breadcrumb-item :to="{ path: '/home' }">
                        <span class="breadcrumb-text">{{ breadcrumbText }}</span>
                    </el-breadcrumb-item>
                </el-breadcrumb>

                <!-- 用户菜单 -->
                <div class="user-menu">
                    <img class="user-avatar" src="../assets/user.svg" alt="用户头像">
                    <el-dropdown trigger="click">
                        <span class="user-name">
                            {{ userName }}
                            <el-icon class="dropdown-icon">
                                <ArrowDown />
                            </el-icon>
                        </span>
                        <template #dropdown>
                            <el-dropdown-menu>
                                <el-dropdown-item @click="handleLogout">退出登录</el-dropdown-item>
                            </el-dropdown-menu>
                        </template>
                    </el-dropdown>
                </div>
            </header>

            <!-- 页面内容 -->
            <div class="page-content">
                <router-view v-slot="{ Component }">
                    <keep-alive exclude="Appea">
                        <component :is="Component" />
                    </keep-alive>
                </router-view>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
import { ref, onMounted, computed, onUnmounted } from 'vue';
// 导入Element Plus图标
import {
    ArrowRight,
    ArrowDown,
    Fold,
    Expand,
    Stopwatch,
    Tickets,
    FolderOpened,
    Setting
} from '@element-plus/icons-vue';
import router from '@/router';
import { ElMessage } from 'element-plus';

// 定义类型
interface MenuItem {
    key: string;
    label: string;
}

// 状态管理
const isCollapse = ref(false);

const userName = ref('');
const breadcrumbText = ref('用户存储表');

// 菜单数据
const menuItems: MenuItem[] = [
    { key: 'Appea', label: '用户存储表' },
    { key: 'Word', label: '文档页' },
    { key: 'Image', label: '图片页' },
    { key: 'Setting', label: '设置页' }
];

// 生命周期钩子
onMounted(() => {
    const mail = localStorage.getItem("mail")
    const token = localStorage.getItem("token")
    if (!mail || !token) {
        router.replace('/login'); // 替换当前路
        return;
    }

    userName.value = localStorage.getItem('mail') || '用户';
    if (sessionStorage.getItem('activeMenuIndex') == "Word") {
        router.push({ path: '/home/word' });
    } else if (sessionStorage.getItem('activeMenuIndex') == "Image") {
        router.push({ path: '/home/image' });
    } else if (sessionStorage.getItem('activeMenuIndex') == "Setting") {
        router.push({ path: '/home/setting' });
    } else {
        router.push({ path: '/home/appea' });
    }
});



// 方法
const toggleSidebar = () => {
    isCollapse.value = !isCollapse.value;
};

//导航栏点击
const defaultActive = computed(() => {
    return sessionStorage.getItem('activeMenuIndex') || 'Appea';

});

//退出登陆
function handleLogout() {
    localStorage.removeItem('token');
    localStorage.removeItem('mail');
    router.replace({ path: '/login' });
}
const handleMenuSelect = (key: string) => {
    updateBreadcrumb(key);
    sessionStorage.setItem('activeMenuIndex', key);
};

const handleMenuOpen = (key: string) => {
    updateBreadcrumb(key);
    sessionStorage.setItem('activeMenuIndex', key);
};

const handleMenuClose = (key: string) => {
    updateBreadcrumb(key);
    sessionStorage.setItem('activeMenuIndex', key);
};

// 更新面包屑文本
const updateBreadcrumb = (key: string) => {
    const selectedItem = menuItems.find(item => item.key === key);
    if (selectedItem) {
        breadcrumbText.value = selectedItem.label;
    }
};
</script>

<style scoped>
/* 全局容器样式 */
.home-container {
    width: 100%;
    height: 100vh;
    background-color: #F5F7FA;
    display: flex;
}

/* 侧边栏菜单样式 */
.sidebar-menu {
    border-right: none;
    height: 100%;
    transition: width 0.3s ease;
}

/* Logo*/
.brand-item {
    display: flex;
    align-items: center;
    padding: 15px 20px !important;
    margin-bottom: 10px;
}

.logo-img {
    width: 30px;
}

.brand-name {
    color: white;
    font-size: 18px;
    margin-left: 10px;
    transition: opacity 0.3s ease;
}

/* 主内容区域样式 */
.main-content {
    flex: 1;
    display: flex;
    flex-direction: column;
    height: 100%;
    overflow: hidden;
}

/* 页头样式 */
.page-header {
    display: flex;
    align-items: center;
    padding: 0 20px;
    background-color: #232942;
    height: 60px;
}

/* 侧边栏切换按钮 */
.toggle-sidebar {
    cursor: pointer;
    font-size: 20px;
}

/* 面包屑导航 */
.breadcrumb-nav {
    margin-left: 20px;
}

.breadcrumb-text {
    color: white;
}

/* 用户菜单 */
.user-menu {
    display: flex;
    align-items: center;
    margin-left: auto;
}

.user-avatar {
    width: 30px;
}

.user-name {
    margin-left: 10px;
    font-size: 16px;
    color: #fff;
    cursor: pointer;
    display: flex;
    align-items: center;
}

.dropdown-icon {
    margin-left: 5px;
    font-size: 14px;
}

/* 页面内容区域 */
.page-content {
    flex: 1;
    padding: 20px;
    overflow-y: auto;
    scrollbar-width: thin;
}

/* 滚动条样式优化 */
.page-content::-webkit-scrollbar {
    width: 6px;
}

.page-content::-webkit-scrollbar-thumb {
    background-color: #ddd;
    border-radius: 3px;
}

.page-content::-webkit-scrollbar-track {
    background-color: #f5f5f5;
}
</style>
