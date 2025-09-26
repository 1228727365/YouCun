<template>
  <div class="home">
    <div class="home-card">
      <div class="home-card-button">
        <div class="upload-container">
          <el-upload class="upload-demo" drag multiple :limit="1" :auto-upload="false" :before-upload="beforeUpload"
            accept=".jpg,.png,.jpeg" :show-file-list="true" @change="handleFileChange" ref="uploadRef">
            <el-icon class="el-icon--upload"><upload-filled /></el-icon>
            <div class="el-upload__text">
              将文件拖放到此处或 <em>点击上传</em>
            </div>
            <template #tip>
              <div class="el-upload__tip">
                jpg/png/jpeg 单个文件大小为5MB
              </div>
            </template>
          </el-upload>
        </div>
      </div>

      <!-- 图片列表区域 -->
      <el-empty v-if="tableData.length <= 0" :image-size="150" />

      <div class="image-list-container">
        <div class="image-grid">
          <!-- 循环展示固定数据的图片 -->
          <div v-for="(image, index) in tableData" :key="index" class="image-item">
            <div class="image-wrapper" @click="get_image_url(image)">
              <el-image :src="image.url" :alt="image.name" fit="cover" class="image-preview"></el-image>
              <!-- 删除按钮 -->
              <el-button class="delete-btn" icon="Delete" size="small" type="danger" circle
                @click.stop="delete_image(index, image)"></el-button>
            </div>
            <div class="image-info">
              <p class="image-name">{{ image.name }}</p>
              <div class="image_data_size">
                <p class="image-size">{{ (Number(image.size) / 1024 / 1024).toFixed(2) }}MB</p>
                <p class="image-date">{{ image.date }}</p>
              </div>
            </div>
          </div>
        </div>
      </div>


      <div class="home-card-pagination">
        <el-pagination size="small" :background="true" layout="prev, pager, next" :default-page-size="8"
          :total="image_sum_page" class="mt-4" @change='change_page_size' />
      </div>
    </div>
  </div>

  <!-- 确认对话框 -->
  <el-dialog v-model="showConfirmDialog" title="上传图片" :align-center="true" width="500px">
    <p>您确定要上传此图片吗？</p>
    <p>图片名: {{ selectedFileName }}</p>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="exit_image_upload">取消</el-button>
        <el-button type="primary" @click="upload_image">确认</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { UploadFilled } from '@element-plus/icons-vue';
import type { UploadInstance, UploadProps } from 'element-plus';
import { ImageAPI_click } from '@/api/home/image/image';
import { ElMessage } from 'element-plus';
import router from '@/router';
import type { imageinterface } from '@/types/image_type';
import { ElLoading } from 'element-plus'

const tableData = ref<imageinterface.imageInfo_Response[]>([])
const image_sum_page = ref(0)


//检查登陆状态
const mail = localStorage.getItem('mail')
const token = localStorage.getItem('token')


// 确定上传对话框
const showConfirmDialog = ref(false);
//上传文件名
const selectedFileName = ref('');
const uploadRef = ref<UploadInstance | null>(null);

//文件对象
const file_data = ref<File | null>(null);


//添加图片
function handleFileChange(file: UploadProps['onChange']): void {
  const files = Array.isArray(file) ? file : [file];
  const currentFile = files[0];
  // 确保是有效的文件对象
  if (currentFile && currentFile.raw) {
    // 检查文件大小
    if (currentFile.raw.size > 5 * 1024 * 1024) {
      ElMessage.error('文件大小不能超过5MB');
      resetUpload();
      return;
    }
    selectedFileName.value = currentFile.name;
    file_data.value = currentFile.raw;
    showConfirmDialog.value = true;
  }
}
//获取域名
const hostlocal = window.location.origin
//点击访问图片
function get_image_url(row: imageinterface.imageInfo_Response) {
  const fileUrl = `${hostlocal}/api/user/getImage?Tid=${row.id}`;
  window.open(fileUrl, '_blank');
}

//确定挂载
onMounted(() => {

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

  //获取所有图片个数
  const infoStr = localStorage.getItem('Info');
  if (infoStr) {
    const info = JSON.parse(infoStr);
    if (info && typeof info.imgNumber === 'number') {
      image_sum_page.value = info.imgNumber;
    }
  }

  ImageAPI_click.get_image_list_dh({
    mail: mail,
    token: token,
    page: 1,
  }, tableData)
  loading.close()
})


//上下一页
const change_page_size = async (page_size: number, currentPage: number) => {
  const mail = localStorage.getItem("mail")
  const token = localStorage.getItem("token")
  if (!mail || !token) {
    ElMessage.error("用户信息缺失，请重新登录");
    router.replace('/login');
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
  ImageAPI_click.get_image_list_dh({
    mail: mail,
    token: token,
    page: page_size
  }, tableData)
  loading.close()
}

//文件上传之前
function beforeUpload(file: File): boolean {
  // 检查文件类型
  const isImage = file.type.startsWith('image/');
  if (!isImage) {
    ElMessage.error('只能上传图片文件!');
    return false;
  }
  return true;
}

//取消按钮
function exit_image_upload() {
  showConfirmDialog.value = false;
  resetUpload()
}

//上传按钮
function upload_image() {

  if (!mail || !token) {
    ElMessage.error("用户信息缺失，请重新登录");
    router.replace('/login');
    return;
  }
  if (!file_data.value) {
    ElMessage.warning("未选择文件");
    return;
  }
  const loading = ElLoading.service({
    lock: true,
    text: 'Loading',
    background: 'rgba(0, 0, 0, 0.7)',
  })
  if (uploadRef.value) {
    ImageAPI_click.upload_image_dh({
      mail: mail,
      token: token
    }, file_data.value, image_sum_page, tableData)
    showConfirmDialog.value = false;
    resetUpload();
  }
  loading.close();
}


//删除图片
function delete_image(index: number, row: imageinterface.imageInfo_Response) {

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
  ImageAPI_click.delete_image_dh({
    mail: mail,
    token: token,
    Tid: row.id
  }, image_sum_page, tableData)
  loading.close();
}

//重置上传
function resetUpload() {
  selectedFileName.value = '';
  if (uploadRef.value) {
    uploadRef.value.clearFiles();
  }
}
</script>

<style scoped>
.image_data_size {
  display: flex;
  align-items: center;
  margin-top: 10px;

  .image-date {
    margin-left: auto;
    font-size: 12px;
    color: #666;
  }
}

.home-card-pagination {
  margin-top: 20px;
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}

/* 新增样式 */
.image-wrapper {
  position: relative;
}

.delete-btn {
  position: absolute;
  top: 5px;
  right: 5px;
  opacity: 0;
  transition: opacity 0.3s ease;
  z-index: 10;
}

.image-item:hover .delete-btn {
  opacity: 1;
}


.image-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 20px;
  margin-top: 15px;
}

.image-item {
  border: 1px solid #eee;
  border-radius: 4px;
  overflow: hidden;
  transition: all 0.3s ease;
}

.image-item:hover {
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
  transform: translateY(-3px);
}

.image-preview {
  width: 100%;
  height: 150px;
  object-fit: cover;
}

.image-info {
  padding: 10px;
}

.image-name {
  font-size: 14px;
  margin: 0 0 5px 0;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.image-size {
  font-size: 12px;
  color: #666;
  margin: 0;
}



.image-list-container {
  margin-top: 30px;
  padding: 10px;
  border-top: 1px solid #eee;
}


.image-item:hover {
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
  transform: translateY(-3px);
}

.image-preview {
  width: 100%;
  height: 150px;
  object-fit: cover;
}

.image-info {
  padding: 10px;
}

.image-name {
  font-size: 14px;
  margin: 0 0 5px 0;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.image-size {
  font-size: 12px;
  color: #666;
  margin: 0;
}



.el-button:hover {
  color: #fff;
  outline: none;
}

.word_help {
  margin-left: 10px;
  background-color: #88BC80;
}

.el-button--primary {
  --el-button-text-color: var(--el-color-white);
  --el-button-bg-color: #252E49;
  --el-button-active-bg-color: #7F5FEC;
  --el-button-outline-color: #252E49;
}

.el-button:hover {
  background-color: #7F5FEC;
  border-color: #7F5FEC;
}

.home-card-button[data-v-d961d772] {
  width: auto;
  display: flex;
  justify-content: start;
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