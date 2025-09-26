import http from "@/http/reque";
import type { LoginInterface } from "@/types/login_type";

export namespace LoginAPI {
    export const login_api = (params: LoginInterface.LoginRequest): Promise<LoginInterface.LoginResponse> => {
        return http({
            url: '/user/login',
            method: "post",
            params: params
        })
    }


    export const get_password_code = (mail: string): Promise<LoginInterface.LoginResponse> => {
        const formData = new FormData();
        formData.append('mail', mail);
        return http({
            url: '/user/sendForget',
            method: "post",
            params: {
                mail: mail
            }
        })
    }
}
