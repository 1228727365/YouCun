import { AppeaAPI } from "../appea/appea_api";
import type { LoginInterface } from "@/types/login_type";
import { ElMessage } from "element-plus";
import { settingAPI } from "./setting_api";
import type { Ref } from "vue";

export namespace SettingAPI_click {
    export const get_user_info = (params: LoginInterface.LoginRequest, userInfo: Ref<LoginInterface.LoginResponseData>) => {
        return AppeaAPI.get_user_info(params).then(res => {
            if (res.code == 200) {
                if (res.data) {
                    userInfo.value = res.data;
                }
            } else {
                ElMessage.error(res.msg);
            }
        });
    }


    //更新用户token
    export const update_token_dh = (params: LoginInterface.LoginRequest, token: Ref<string>) => {
        return settingAPI.update_token(params).then(res => {
            if (res.code == 200) {
                if (res.token) {
                    ElMessage.success('Token更新成功');
                    token.value = res.token;
                    localStorage.setItem('token', res.token);
                } else {
                    ElMessage.error('Token更新失败');
                }
            } else {
                ElMessage.error(res.msg);
            }
        });
    }


    //充值会员
    export const recharge_vip_dh = (params: LoginInterface.LoginRequest, userInfo: Ref<LoginInterface.LoginResponseData>) => {
        return settingAPI.recharge_vip(params).then(res => {
            if (res.code == 200) {
                ElMessage.success('充值成功');
                // 用户信息
                SettingAPI_click.get_user_info({
                    mail: params.mail,
                    token: params.token
                }, userInfo)
            } else {
                ElMessage.error(res.msg);
            }
        });
    }

    //修改密码
    export const update_password_dh = (params: LoginInterface.LoginRequest) => {
        return settingAPI.update_password(params).then(res => {
            if (res.code == 200) {
                ElMessage.success('密码修改成功');
            } else {
                ElMessage.error(res.msg);
            }
        }).catch(err => {
            ElMessage.error(err);
        });
    }
}
