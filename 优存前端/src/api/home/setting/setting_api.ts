import type { LoginInterface } from "@/types/login_type";
import  http  from "@/http/reque";
export namespace settingAPI {
    //更新token
    export const update_token = (params: LoginInterface.LoginRequest): Promise<LoginInterface.LoginResponse> => {
        return http({
            url: '/user/upToken',
            method: 'post',
            params:params
        });
    };

    //充值会员
    export const recharge_vip=(params: LoginInterface.LoginRequest): Promise<LoginInterface.LoginResponse> => {
        return http({
            url: '/user/userCaimi',
            method: 'post',
            params:params
        });
    };

    //修改密码
    export const update_password=(params: LoginInterface.LoginRequest): Promise<LoginInterface.LoginResponse> => {
        return http({
            url: '/user/updataPassword',
            method: 'post',
            params:params
        });
    };
}