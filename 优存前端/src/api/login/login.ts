
import { LoginAPI } from "@/api/login/login_api";
import { ElMessage, ElMessageBox } from 'element-plus';
import routes from "@/router/index";
import type { LoginInterface } from "@/types/login_type";
import '@/style/Toas.css';

const mail_yz = /[\w!#$%&'*+/=?^_`{|}~-]+(?:\.[\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\w](?:[\w-]*[\w])?\.)+[\w](?:[\w-]*[\w])?/
export namespace RegisAPI_click {
    export function login(params: LoginInterface.LoginRequest) {
        if (!params.mail || !mail_yz.test(params.mail)) {
            ElMessage.error("请输入正确的邮箱")
            return
        }
        if (!params.password || params.password.length < 6) {
            ElMessage.error("请输入正确的密码")
            return
        }
        LoginAPI.login_api(params).then(res => {
            if (res.code === 200) {
                ElMessage.success(res.msg)
                localStorage.setItem("mail", params.mail)
                localStorage.setItem("token", res.token || '')
                routes.push('/home')
            } else {
                ElMessage.error(res.msg)
            }
        }).catch(err => {
            ElMessage.error(err)
        })
    }


    export function regis() {
        routes.push('/register')
    }

    export function get_password(mail: string) {
        ElMessageBox.confirm('是否通过邮箱找回密码？', "提示", {
            distinguishCancelAndClose: true,
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            customClass: 'custom-message-box',
            cancelButtonClass: 'el-button--default',
            confirmButtonClass: 'el-button--primary'
        }).then(() => {
            if (!mail_yz.test(mail)) {
                return ElMessage.error("请输入正确的邮箱")
            }
            LoginAPI.get_password_code(mail).then(res => {
                if (res.code == 200) {
                    ElMessage.success("发送成功")
                } else {
                    ElMessage.error("发送失败")
                }
            })
        })
    }


}