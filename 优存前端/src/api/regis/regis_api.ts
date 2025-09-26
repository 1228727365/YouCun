import http from "@/http/reque";
import type { RegisInterface } from "@/types/regis_type";

export namespace RegisAPI {
    export const code_api = (params: RegisInterface.regisRequest): Promise<RegisInterface.regisResponse> => {
        return http({
            url: '/user/code',
            method: "post",
            params: params,
        })
    }


    export const regis_api = (params: RegisInterface.regisRequest): Promise<RegisInterface.regisResponse> => {
        return http({
            url: '/user/regis',
            method: "post",
            params: params,
        })
    }
}