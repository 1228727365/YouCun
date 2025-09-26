import { RegisAPI } from "./regis_api";
import { ElMessage } from "element-plus";
import router from "../../router/index";
import type { RegisInterface } from '@/types/regis_type'

const mail_yz = /[\w!#$%&'*+/=?^_`{|}~-]+(?:\.[\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\w](?:[\w-]*[\w])?\.)+[\w](?:[\w-]*[\w])?/

export namespace RegisAPI_click {
    export function getCode(mail: string, code_button: any) {
        if (!mail || !mail_yz.test(mail)) {
            return ElMessage.error("请输入正确的邮箱")
        }
        RegisAPI.code_api({
            mail: mail
        }).then(res => {
            if (res.code === 200) {
                ElMessage.success(res.msg)
                coun_regis_code(code_button)
            } else {
                ElMessage.error(res.msg)
            }
        }).catch(err => {
            ElMessage.error(err)
        })
    }

    export function regis(params: RegisInterface.regisRequest, password2: string) {

        if (!params.mail || !mail_yz.test(params.mail)) {
            return ElMessage.error("请输入正确的邮箱")
        }
        if (!params.password || params.password?.length < 6 || password2.length < 6) {
            return ElMessage.error("密码长度不能小于6")
        }
        if (params.password != password2) {
            return ElMessage.error("密码不一致")
        }
        if (!params.code || params.code.length < 6) {
            return ElMessage.error("请输入验证码")
        }
        RegisAPI.regis_api(params).then(res => {
            if (res.code === 200) {
                ElMessage.success(res.msg)
            } else {
                ElMessage.error(res.msg)
            }
        }).catch(err => {
            ElMessage.error(err)
        })


    }

    export function login() {
        router.push("/login")
    }


    //倒计时
    function coun_regis_code(code_button: HTMLButtonElement) {
        let seconds = 300; // 倒计时总秒数
        const originalText = code_button.innerText;

        code_button.disabled = true;
        code_button.innerText = `${seconds}s`;

        // 使用setInterval实现每秒更新
        const timer = setInterval(() => {
            seconds--;
            code_button.innerText = `${seconds}s`;

            if (seconds <= 0) {
                clearInterval(timer);
                code_button.disabled = false;
                code_button.innerText = originalText;
            }
        }, 1000);
    }
}