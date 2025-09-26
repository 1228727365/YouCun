import http from "@/http/reque";
import type { WordInterfaces } from '@/types/word_type'
export namespace WordAPI {
    //获取所有文档
    export const get_word_all = (Prams: WordInterfaces.Word_request): Promise<WordInterfaces.Word_Response> => {
        return http({
            url: '/user/getAllFile',
            method: "post",
            params: {
                mail: Prams.mail,
                token: Prams.token,
                page: Prams.page
            }
        })
    }

    //创建文档
    export const create_word = (prams: WordInterfaces.Word_request): Promise<WordInterfaces.Word_Response> => {
        return http({
            url: '/user/newfile',
            method: "post",
            params: prams
        })
    }



    //删除文档内容
    export const delete_word = (params: WordInterfaces.Word_request): Promise<WordInterfaces.Word_Response> => {
        return http({
            url: '/user/deleteFile',
            method: "post",
            params: params
        })
    }

    //更新文档状态
    export const update_state_word = (params: WordInterfaces.Word_request): Promise<WordInterfaces.Word_Response> => {
        return http({
            url: '/user/setFileState',
            method: "post",
            params: params
        })
    }

    //更新文档token
    export const update_word_token = (params: WordInterfaces.Word_request): Promise<WordInterfaces.Word_Response> => {
        return http({
            url: '/user/setFileToken',
            method: "post",
            params: params
        })
    }



    //修改文档内容
    export const update_file_content = (params: WordInterfaces.Word_request): Promise<WordInterfaces.Word_Response> => {
        return http({
            url: '/user/updateFile',
            method: "post",
            params: params
        })
    }

    //更新文档密码
    export const update_word_Fpassword = (params: WordInterfaces.Word_request): Promise<WordInterfaces.Word_Response> => {
        return http({
            url: '/user/setFilePassword',
            method: "post",
            params: params
        })
    }

    //更新文档密钥
    export const update_word_FaesKey = (params: WordInterfaces.Word_request): Promise<WordInterfaces.Word_Response> => {
        return http({
            url: 'user/setFileAesKey',
            method: "post",
            params: params
        })
    }
}