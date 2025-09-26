<template>
    <div class="home">
        <div class="home-card">
            <!-- 用户信息卡片 -->
            <div class="user-info">
                <el-avatar :size="80" class="user-avatar">
                    <img src="/src/assets/user.svg" alt="用户头像">
                </el-avatar>
                <div class="user-details">
                    <div class="user-email">{{ mail }}</div>
                    <el-tag :type="userInfo.vip_time ? (userInfo.vip_time === '已过期' ? 'primary' : 'danger') : 'info'"
                        size="small" class="mt-2">
                        <el-icon>
                            <Star />
                        </el-icon>
                        会员：{{ userInfo.vip_time }}
                    </el-tag>
                </div>
            </div>
            <div class="user-info">
                <span class="token_label">Token:</span><el-input class="token_input" v-model="token" autocomplete="off"
                    :disabled="true">
                    <template #append>
                        <el-button icon="Refresh" @click="updateFToken" />
                    </template>
                </el-input>
            </div>
            <!--功能区-->
            <div class="user-info">
                <el-button type="primary" @click="handleRechargeMember">充值会员</el-button>
                <el-button type="success" @click="handleUpdatePassword">修改密码</el-button>
                <el-button type="danger" @click="handleContactService">联系客服</el-button>

            </div>
            <el-empty :image-size="200" />

        </div>
    </div>

    <!--更新token弹窗-->
    <el-dialog v-model="update_token_show" title="提示" width="300" :align-center="true">

        <el-input v-model="password_input.value" autocomplete="off" placeholder="账户密码" :show-password="true"
            minlegth="4" maxlegth="32" />
        <template #footer>
            <div class="dialog-footer">
                <el-button @click="update_token_show = false">取消</el-button>
                <el-button type="primary" @click="updata_token_clikc">更新</el-button>
            </div>
        </template>
    </el-dialog>
    <!--充值会员-->
    <el-dialog v-model="update_vip_show" title="提示" width="300" :align-center="true">

        <el-input v-model="kami_input.value" autocomplete="off" placeholder="卡密" minlegth="4" maxlegth="32" />
        <template #footer>
            <div class="dialog-footer">
                <el-button @click="update_vip_show = false">取消</el-button>
                <el-button type="primary" @click="updata_vip_clikc">充值</el-button>
            </div>
        </template>
    </el-dialog>

    <!--修改密码-->
    <el-dialog v-model="update_password_show" title="提示" width="300" :align-center="true">

        <el-input v-model="xg_password_input.value" autocomplete="off" placeholder="原始密码" minlegth="4" maxlegth="32"
            show-password="true" />
        <el-input class="password2_input" v-model="xg_password_input1.value" autocomplete="off" placeholder="新密码"
            minlegth="4" maxlegth="32" show-password="true" />
        <template #footer>
            <div class="dialog-footer">
                <el-button @click="update_password_show = false">取消</el-button>
                <el-button type="primary" @click="updata_password_clikc">修改</el-button>
            </div>
        </template>
    </el-dialog>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue';
import { ElMessage, ElLoading } from 'element-plus';
import type { LoginInterface } from '@/types/login_type';
import { SettingAPI_click } from '@/api/home/setting/setting';
import router from '@/router';

//获取用户信息
const userInfo = ref<LoginInterface.LoginResponseData>({
    moreSize: 0,
    useSize: '',
    fileNumber: 0,
    imgNumber: 0,
    vip_time: ''
});
const mail = localStorage.getItem('mail') || '';
const token = ref();


//更新token弹窗
const update_token_show = ref(false);
//token编辑框
const password_input = ref({
    value: '',
});

//充值会员弹窗
const update_vip_show = ref(false);
//卡密编辑框
const kami_input = ref({
    value: '',
});

//修改密码
const update_password_show = ref(false);
//旧密码编辑框
const xg_password_input = ref({
    value: '',
});
//新密码编辑框
const xg_password_input1 = ref({
    value: '',
});

function updata_password_clikc() {
    if (!xg_password_input.value.value || !xg_password_input1.value.value) {
        ElMessage.error('请输入密码');
        return;
    }
    const loading = ElLoading.service({
        lock: true,
        text: 'Loading',
        background: 'rgba(0, 0, 0, 0.7)',
    })
    SettingAPI_click.update_password_dh({
        mail: mail,
        Token: token.value,
        password: xg_password_input.value.value,
        newPassword: xg_password_input1.value.value
    })
    update_password_show.value = false;
    loading.close()
    xg_password_input.value.value = '';
    xg_password_input1.value.value = '';
}

onMounted(() => {
    const mail = localStorage.getItem("mail")
    const tokens = localStorage.getItem("token")
    if (!mail || !tokens) {
        router.replace('/login'); // 替换当前路
        return;
    }
    token.value = localStorage.getItem('token');
    // 初始化用户信息或其他数据
    SettingAPI_click.get_user_info({
        mail: mail,
        token: token.value
    }, userInfo)


});

//充值执行
function updata_vip_clikc() {
    if (!mail || !token) {
        ElMessage.error("用户信息缺失，请重新登录");
        router.replace('/login');
        return;
    }
    if (!kami_input.value.value) {
        ElMessage.error('请输入卡密');
        return;
    }
    const loading = ElLoading.service({
        lock: true,
        text: 'Loading',
        background: 'rgba(0, 0, 0, 0.7)',
    })
    SettingAPI_click.recharge_vip_dh({
        mail: mail,
        token: token.value,
        km: kami_input.value.value
    }, userInfo)
    loading.close()
    update_vip_show.value = false;
    kami_input.value.value = '';
}

//充值会员按钮
const handleRechargeMember = () => {
    update_vip_show.value = true;
};

//修改密码按钮
const handleUpdatePassword = () => {
    update_password_show.value = true;
};

//联系客户按钮
const handleContactService = () => {
    ElMessage.primary('作者：1228727365@qq.com');
};

//更新token
function updateFToken() {
    update_token_show.value = true;
}

//执行token
function updata_token_clikc() {
    if (!mail || !token) {
        ElMessage.error("用户信息缺失，请重新登录");
        router.replace('/login');
        return;
    }
    if (!password_input.value.value) {
        ElMessage.error('请输入密码');
        return;
    }
    const loading = ElLoading.service({
        lock: true,
        text: 'Loading',
        background: 'rgba(0, 0, 0, 0.7)',
    })
    SettingAPI_click.update_token_dh({
        mail: mail,
        Token: token.value,
        password: password_input.value.value
    }, token)
    update_token_show.value = false;
    loading.close()
    password_input.value.value = '';
}


</script>


<style scoped>
.password2_input {
    margin-top: 20px;
}

.el-button--primary {
    --el-button-text-color: var(--el-color-white);
    --el-button-bg-color: #252E49;
    --el-button-active-bg-color: #7F5FEC;
    --el-button-outline-color: #252E49;
}

.el-button {
    --el-button-hover-text-color: #fff
}

.el-input {
    --el-input-inner-height: auto;
}

.token_label {
    font-weight: bold;
}

.token_input {
    width: 300px;
    margin-left: 5px;
}

.el-tag {
    font-size: 15px;
}

.el-button--primary {
    --el-button-bg-color: #232942;
    --el-button-border-color: #232942;
}

.el-button:hover {
    background-color: #8469DE;
    border-color: #232942;
    color: #ffffff;
    outline: none;
}

.el-divider--horizontal {
    border-top: 0px;
    display: block;
    height: 1px;
    margin: 24px 0;
    width: 100%;
}

.user-email {
    font-weight: bold;
    font-size: 20px;
    margin-bottom: 10px;
}

.user-details {
    margin-left: 20px;
}

.home {
    padding: 10px;
    background-color: #f5f7fa;

}

.home-card {
    background-color: #fff;
    border-radius: 10px;
    box-shadow: 0px;
    padding: 25px;
}

.user-info {
    display: flex;
    align-items: center;
    padding: 20px;
}
</style>
