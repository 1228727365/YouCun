import http from "@/http/reque";
import type { AppInterface } from "@/types/appea_type";
import type { LoginInterface } from "@/types/login_type";

export namespace AppeaAPI {
    export const get_All_info = (): Promise<AppInterface.globalInfoResponse> => {
        return http({
            url: '/admin/Count',
            method: "get",
        })

    }


    export const get_user_info = (params: LoginInterface.LoginRequest):Promise<LoginInterface.LoginResponse>  => {
        return http({
            url: '/user/getUserData',
            method: "get",
            params: {
                mail: params.mail,
                token: params.token
            },
        })

    }
}