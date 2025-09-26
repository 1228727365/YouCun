import axios from "axios";
import { ElMessage } from "element-plus";
const http = axios.create({
    baseURL: '/api',
    timeout: 5000
}
)




http.interceptors.request.use(
    config => {
        return config;
    },
    error => {
        return Promise.reject(error);
    }
);


http.interceptors.response.use(
    response => {
        return response.data;
    },
    error => {
        // 处理响应错误
        if (error.response) {
            const status = error.response.status;
            const responseData = error.response.data;
            
            // 对于400状态码，将数据返回而不是抛出错误
            if (status === 400) {
                return responseData; // 返回400状态码时的数据
            } 
            // 处理其他错误状态码
            else if (status === 401) {
                ElMessage.error('身份验证失败，请重新登录');
            } else if (status === 403) {
                ElMessage.error('没有权限访问');
            } else if (status === 404) {
                ElMessage.error('请求的资源不存在');
            } else if (status === 500) {
                ElMessage.error('服务器内部错误');
            }
        } else if (error.request) {
            // 请求已发出，但没有收到响应
            ElMessage.error('网络请求无响应，请检查网络');
        } else {
            // 其他错误
            ElMessage.error('请求出错: ' + error.message);
        }
        
        // 将错误继续抛出，让业务代码可以捕获
        return Promise.reject(error);
    }
);
export default http