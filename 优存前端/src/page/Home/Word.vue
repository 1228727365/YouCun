<template>
    <div class="home">
        <div class="home-card">
            <div class="home-card-button">
                <el-button type="primary" @click="dialogFormVisible = true">创建文档</el-button>
                <el-button class="word_help" type="primary" @click="dialogWordHelp = true">接口说明</el-button>
            </div>
            <div class="home-card-table">
                
                <el-table :data="filterTableData" style="width: 100%" size="large" :highlight-current-row="true"
                    tooltip-effect="light" :border="true">
                    <el-table-column label="ID" prop="id" width="60px" :show-overflow-tooltip="true" />
                    <el-table-column label="内容" prop="content" width="300px" :resizable="true"
                        :show-overflow-tooltip="true" />
                    <el-table-column label="Ftoken" prop="token" :show-overflow-tooltip="true" />
                    <el-table-column label="密码" prop="password" :show-overflow-tooltip="true" />
                    <el-table-column label="密钥" prop="aesKey" :show-overflow-tooltip="true" />
                    <el-table-column label="状态" width="100px">
                        <template #default="scope">
                            <el-switch v-model="scope.row.state" inline-prompt active-text="开" inactive-text="关"
                                :active-value="1" :inactive-value="0" :loading="state_loading"
                                :before-change="() => beforeChangeState(scope.$index, scope.row)" />
                        </template>
                    </el-table-column>
                    <el-table-column label="时间" prop="date" :show-overflow-tooltip="true" />
                    <el-table-column align="right" width="210">
                        <template #header>
                            <el-input v-model="search" size="small" placeholder="搜索内容" />
                        </template>
                        <template #default="scope">
                            <el-button size="small" type="primary" @click="handleEdit(scope.$index, scope.row)">
                                编辑
                            </el-button>
                            <el-button size="small" type="danger" @click="handleDelete(scope.$index, scope.row)">
                                删除
                            </el-button>
                            <el-button class="button_get_word" size="small" type="primary"
                                @click="getWord_url(scope.$index, scope.row)">
                                访问
                            </el-button>
                        </template>
                        
                    </el-table-column>
                    
                </el-table>

            </div>
            <div class="home-card-pagination">
                <el-pagination size="small" :background="true" layout="prev, pager, next" :default-page-size="8"
                    :total="word_sum_page" class="mt-4" @change='change_page_size' />
            </div>
        </div>
    </div>
    <!---创建弹窗布局-->
    <el-dialog v-model="dialogFormVisible" title="创建文档" width="450" :align-center="true">
        <el-form :model="form" label-width="100px">

            <el-form-item label="内容" a>
                <el-input class="content" type="textarea" v-model="form.content" autocomplete="off"
                    :autosize="{ minRows: 0, maxRows: 15 }" placeholder="文档内容" />
            </el-form-item>

            <el-form-item label="密码" a>
                <el-input v-model="form.password" autocomplete="off" placeholder="文档密码(留空则无密码)" :show-password="true" />
            </el-form-item>

        </el-form>
        <template #footer>
            <div class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取消</el-button>
                <el-button type="primary" @click="handleCreate_word()">确定</el-button>
            </div>
        </template>
    </el-dialog>

    <!---修改文档弹窗布局-->
    <el-dialog v-model="update_word_show" title="修改文档" width="500" :align-center="true">
        <el-form :model="form" label-width="100px">

            <el-form-item label="内容" a>
                <el-input class="content" type="textarea" v-model="form_update.content" autocomplete="off"
                    :autosize="{ minRows: 0, maxRows: 15 }" placeholder="文档内容" />
            </el-form-item>
            <el-form-item label="Token" a>
                <el-input v-model="form_update.token" autocomplete="off" :disabled="true">
                    <template #append>
                        <el-button :icon="Refresh" @click="updateFToken" />
                    </template>
                </el-input>
            </el-form-item>

            <el-form-item label="密钥" a>
                <el-input v-model="form_update.aesKey" autocomplete="off" placeholder="文档加解密密钥(留空则无加密)"
                    :show-password="true" minlength="16" maxlength="16" />
            </el-form-item>

            <el-form-item label="密码" a>
                <el-input v-model="form_update.password" autocomplete="off" placeholder="文档密码(留空则无密码)"
                    :show-password="true" minlegth="4" maxlegth="32" />
            </el-form-item>

        </el-form>
        <template #footer>
            <div class="dialog-footer">
                <el-button @click="update_word_show = false">取消</el-button>
                <el-button type="primary" @click="update_word_data()">确定</el-button>
            </div>
        </template>
    </el-dialog>


    <!--文档说明弹窗布局-->
    <el-dialog v-model="dialogWordHelp" title="文档接口说明" width="800px" :align-center="true"
        :before-close="() => { dialogWordHelp = false }">
        <!-- 接口基础信息卡片 -->
        <div class="api-card">
            <h5 class="api-title">📌 接口地址</h5>
            <div class="api-url">
                <code>{{hostlocal}}/api/user/getFile</code>
            </div>

            <h5 class="api-title mt-4">📋 参数说明</h5>
            <div class="table-container">
                <table class="api-table">
                    <thead>
                        <tr>
                            <th>是否必填</th>
                            <th>参数名</th>
                            <th>示例值</th>
                            <th>数据类型</th>
                            <th>说明</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td><span class="required">是</span></td>
                            <td>Ftoken</td>
                            <td>448d73cc2deef1d8132d71f9630a6d8c</td>
                            <td>string</td>
                            <td>文件唯一标识</td>
                        </tr>
                        <tr>
                            <td><span class="required">是</span></td>
                            <td>type</td>
                            <td>json</td>
                            <td>string</td>
                            <td>返回数据格式</td>
                        </tr>
                        <tr>
                            <td><span class="optional">否</span></td>
                            <td>Fpassword</td>
                            <td>-</td>
                            <td>string</td>
                            <td>文件访问密码（如有）</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>

        <template #footer>
            <div class="dialog-footer">
                <el-button size="medium" @click="dialogWordHelp = false">
                    取消
                </el-button>
                <el-button size="medium" type="primary" @click="dialogWordHelp = false">
                    知晓
                </el-button>
            </div>
        </template>
    </el-dialog>

</template>

<script setup lang="ts">
import { computed, ref, onMounted, reactive } from 'vue'
import type { WordInterfaces } from '@/types/word_type'
import { WordAPI_click } from '@/api/home/word/word'
import { ElMessage } from 'element-plus';
import router from '@/router';
import { Refresh } from '@element-plus/icons-vue'
import { generateRandomString } from '@/tool'
import { ElLoading } from 'element-plus'

//获取域名
const hostlocal = window.location.origin

//文档说明弹窗
const dialogWordHelp = ref(false)


//文档开关
const state_loading = ref(false)
const beforeChangeState = (index: number, row: WordInterfaces.fileInfo_Response) => {
    if (!mail || !token) {
        ElMessage.error("用户信息缺失，请重新登录");
        router.replace('/login');
        return;
    }
    state_loading.value = true
    return new Promise((resolve) => {
        WordAPI_click.update_state_file({
            mail: mail,
            token: token,
            Fid: row.id,
            state: row.state == 0 ? 1 : 0
        }).then(res => {
            state_loading.value = false
            if (res.code == 200) {
                ElMessage.success("设置成功");
                resolve(true);
            } else {
                ElMessage.error("设置失败");
                resolve(false);
            }
        })
    });
};
//新建文档弹窗
const dialogFormVisible = ref(false)
const form = reactive({
    content: '',
    password: '',
})

//修改文档弹窗
const update_word_show = ref(false)
const form_update = reactive<WordInterfaces.FormUpdateType>({
    id: 0,
    content: '',
    aesKey: '',
    password: '',
    token: '',
})

// 表格数据
const tableData = ref<WordInterfaces.fileInfo_Response[]>([])
const word_sum_page = ref(0)

//检查登陆状态
const mail = localStorage.getItem('mail')
const token = localStorage.getItem('token')


//搜索
const search = ref('')
const filterTableData = computed(() => {
    if (!search.value) {
        return tableData.value;
    }
    const searchLower = search.value.toLowerCase();
    return tableData.value.filter(item => {
        return typeof item.content === 'string' &&
            item.content.toLowerCase().includes(searchLower);
    });
})


//编辑按钮
const handleEdit = (index: number, row: WordInterfaces.fileInfo_Response) => {
    console.log(index, row)
    form_update.id = row.id
    form_update.content = row.content
    form_update.aesKey = row.aesKey
    form_update.password = row.password
    form_update.token = row.token
    update_word_show.value = true
    //保存旧内容
    update_word_data_old.value = row
}


//访问文档链接
const getWord_url = (index: number, row: WordInterfaces.fileInfo_Response) => {
    const fileUrl = `${hostlocal}/api/user/getFile?Ftoken=${row.token}&type=text`;
    window.open(fileUrl, '_blank');

}

//保存旧内容
const update_word_data_old = ref({
    content: "",
    aesKey: "",
    password: "",
    token: ""
})
//修改文档信息
function update_word_data() {
    const loading = ElLoading.service({
        lock: true,
        text: 'Loading',
        background: 'rgba(0, 0, 0, 0.7)',
    })
    if (!mail || !token) {
        ElMessage.error("用户信息缺失，请重新登录");
        router.replace('/login');
        return;
    }
    if (update_word_data_old.value.content != form_update.content) {
        WordAPI_click.update_file_content_dh({
            mail: mail,
            token: token,
            content: form_update.content,
            Fid: form_update.id
        }, tableData)
    }
    if (update_word_data_old.value.password != form_update.password) {
        WordAPI_click.update_word_Fpassword_dh({
            mail: mail,
            token: token,
            Fpassword: form_update.password,
            Fid: form_update.id
        }, tableData)
    }
    if (update_word_data_old.value.aesKey != form_update.aesKey) {
        if (form_update.aesKey) {
            if (form_update.aesKey.length != 16) {
                loading.close()
                return ElMessage.error("密钥长度必须为16位");
            }

        }
        WordAPI_click.update_word_FaesKey_dh({
            mail: mail,
            token: token,
            Fid: form_update.id,
            AesKey: form_update.aesKey
        }, tableData)
    }

    update_word_show.value = false
    loading.close()
}

//更新文档token
function updateFToken() {

    if (!mail || !token) {
        ElMessage.error("用户信息缺失，请重新登录");
        router.replace('/login');
        return;
    }
    const loading = ElLoading.service({
        lock: true,
        text: 'Loading',
        background: 'rgba(0, 0, 0, 0.7)',
    })
    WordAPI_click.update_word_token_dh({
        mail: mail,
        token: token,
        Ftoken: generateRandomString(32),
        Fid: form_update.id
    }, form_update, tableData)
    loading.close()
}

//删除按钮
const handleDelete = (index: number, row: WordInterfaces.fileInfo_Response) => {
    if (!mail || !token) {
        ElMessage.error("用户信息缺失，请重新登录");
        router.replace('/login');
        return;
    }
    const loading = ElLoading.service({
        lock: true,
        text: 'Loading',
        background: 'rgba(0, 0, 0, 0.7)',
    })
    WordAPI_click.delete_word_dh({
        mail: mail,
        token: token,
        Fid: row.id
    }, tableData,word_sum_page)
    loading.close()
}

onMounted(async () => {
    if (!mail || !token) {
        ElMessage.error("用户信息缺失，请重新登录");
        router.replace('/login');
        return;
    }

    //获取所有文档个数
    const infoStr = localStorage.getItem('Info');
    if (infoStr) {
        const info = JSON.parse(infoStr);
        if (info && typeof info.fileNumber === 'number') {
            word_sum_page.value = info.fileNumber;
        }
    }
    const loading = ElLoading.service({
        lock: true,
        text: 'Loading',
        background: 'rgba(0, 0, 0, 0.7)',
    })
    //获取文档列表
    WordAPI_click.get_word_all_dh({
        mail: mail,
        token: token,
        page: 1
    }, tableData)
    loading.close()
})

//创建文档
async function handleCreate_word() {
    if (!mail || !token) {
        ElMessage.error("用户信息缺失，请重新登录");
        router.replace('/login');
        return;
    }
    const loading = ElLoading.service({
        lock: true,
        text: 'Loading',
        background: 'rgba(0, 0, 0, 0.7)',
    })
    WordAPI_click.create_word_dh({
        mail: mail,
        token: token,
        password: form.password,
        content: form.content
    }, tableData,word_sum_page)

    // 清空弹窗表单
    form.password = ''
    form.content = ''
    dialogFormVisible.value = false
    loading.close()
}



//上下一页
const change_page_size = async (page_size: number, currentPage: number) => {
    const mail = localStorage.getItem("mail")
    const token = localStorage.getItem("token")
    if (!mail || !token) {
        ElMessage.error("用户信息缺失，请重新登录");
        router.replace('/login');
        return;
    }

    //刷新文档列表
    if (!mail || !token) {
        ElMessage.error("用户信息缺失，请重新登录");
        router.replace('/login');
        return;
    }
    const loading = ElLoading.service({
        lock: true,
        text: 'Loading',
        background: 'rgba(0, 0, 0, 0.7)',
    })
    WordAPI_click.get_word_all_dh({
        mail: mail,
        token: token,
        page: page_size
    }, tableData)
    loading.close()
}

</script>

<style scoped>
.button_get_word {
    background-color: #88BC80;
}


.api-card {
    padding: 10px 0;
}

.api-title {
    color: #1f2329;
    font-weight: 600;
    margin-bottom: 8px;
    display: flex;
    align-items: center;
    gap: 6px;
}

.api-url {
    background-color: #f5f7fa;
    padding: 12px 15px;
    border-radius: 6px;
    border-left: 3px solid #409eff;
    font-family: 'Consolas', 'Monaco', monospace;
}

.table-container {
    overflow-x: auto;
    margin-top: 10px;
}

.api-table {
    width: 100%;
    border-collapse: collapse;
    font-size: 14px;
}

.api-table th,
.api-table td {
    padding: 10px 12px;
    text-align: left;
    border-bottom: 1px solid #e5e7eb;
}

.api-table th {
    background-color: #f9fafb;
    color: #4b5563;
    font-weight: 500;
}

.api-table tr:hover {
    background-color: #f9fafb;
}

.required {
    display: inline-block;
    padding: 2px 6px;
    background-color: #fef2f2;
    color: #dc2626;
    border-radius: 4px;
    font-size: 12px;
}

.optional {
    display: inline-block;
    padding: 2px 6px;
    background-color: #f0fdf4;
    color: #166534;
    border-radius: 4px;
    font-size: 12px;
}

.mt-4 {
    margin-top: 16px;
}

.dialog-footer {
    display: flex;
    justify-content: flex-end;
    gap: 10px;
    padding-top: 15px;
}


.word_help {
    background-color: #88BC80;
    margin-left: 20px;
}

.home-card-button[data-v-d961d772] {
    width: auto;
    display: flex;
    justify-content: start;
    align-items: center;
    margin-bottom: 20px;
}

.home-card-pagination {
    margin-top: 20px;
    width: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
}

/* 保持原有样式不变 */
.el-table .cell {
    box-sizing: border-box;
    line-height: 0px;
}

.el-switch {
    --el-switch-on-color: #252E49;
    --el-switch-off-color: red;
}

.el-switch.is-checked .el-switch__core {
    background-color: #252E49;
    border-color: #252E49;
}

.el-table {
    --el-table-header-background-color: #F5F5F5;
}

.el-button:hover {
    background-color: #7F5FEC;
    border-color: #7F5FEC;
}

.home-card-table {
    width: 100%;
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

.home-card-button {
    width: auto;
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 20px;
}

.home-card {
    width: 100%;
    background-color: white;
    border-radius: 10px;
    padding: 20px;
}

.home {
    padding: 10px;
    width: 100%;
}
</style>
