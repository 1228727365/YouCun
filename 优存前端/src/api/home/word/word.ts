import { WordAPI } from "./word_api";
import type { WordInterfaces } from "@/types/word_type";
import { ElMessage } from 'element-plus';
import routes from "@/router";
import type { Ref } from 'vue'
import { AppeaAPI_click } from "../appea/appea";
export namespace WordAPI_click {
    //获取文档列表
    export function get_word_all_dh(Prams: WordInterfaces.Word_request, tableData: Ref<WordInterfaces.fileInfo_Response[]>) {
        return WordAPI.get_word_all(Prams).then(res => {
            if (res.code === 200) {
                if (Array.isArray(res.data)) {
                    tableData.value = res.data;
                } else {
                    ElMessage.warning('获取的数据格式不正确');
                    tableData.value = [];
                }
            } else {
                ElMessage.error(res.msg);
                routes.push('/login');
                return null;
            }
        }).catch(err => {
            console.log(err);
            ElMessage.error('请求失败，请重新登录');
            routes.push('/login');
            return null;
        });
    }




    //创建文档
    export function create_word_dh(params: WordInterfaces.Word_request, tableData: Ref<WordInterfaces.fileInfo_Response[]>, word_sum_page: Ref<Number>) {
        return WordAPI.create_word(params).then(res => {
            if (res.code == 200) {
                //更新文档列表
                get_word_all_dh({
                    mail: params.mail,
                    token: params.token,
                    page: 1
                }, tableData)
                AppeaAPI_click.useStatsStore().updateStat(
                    0, AppeaAPI_click.useStatsStore().statsData[0].value + 1)
                word_sum_page.value = AppeaAPI_click.useStatsStore().statsData[0].value
            } else {
                ElMessage.error(res.msg);
            }
        })
    }

    //删除文档
    export function delete_word_dh(params: WordInterfaces.Word_request, tableData: Ref<WordInterfaces.fileInfo_Response[]>, word_sum_page: Ref<Number>) {
        return WordAPI.delete_word(params).then(res => {
            if (res.code == 200) {
                //更新文档列表
                get_word_all_dh({
                    mail: params.mail,
                    token: params.token,
                    page: 1
                }, tableData)
                ElMessage.success(res.msg);
                AppeaAPI_click.useStatsStore().updateStat(
                    0, AppeaAPI_click.useStatsStore().statsData[0].value - 1)
                    word_sum_page.value = AppeaAPI_click.useStatsStore().statsData[0].value
            } else {
                ElMessage.error(res.msg);
            }
        })
    }

    //更新文档状态
    export function update_state_file(params: WordInterfaces.Word_request) {
        return WordAPI.update_state_word(params).then(res => {
            return res
        })
    }


    //更新文档token
    export function update_word_token_dh(params: WordInterfaces.Word_request, form_update: WordInterfaces.FormUpdateType, tableData: Ref<WordInterfaces.fileInfo_Response[]>) {
        return WordAPI.update_word_token(params).then(res => {
            if (res.code == 200) {
                ElMessage.success(res.msg);
                form_update.token = res.token || '';
                const index = tableData.value.findIndex(item => item.id === form_update.id);
                if (index !== -1) {
                    tableData.value[index].token = form_update.token
                }
            } else {
                ElMessage.error(res.msg);
            }
        })
    }


    //更新文档内容
    export const update_file_content_dh = (params: WordInterfaces.Word_request, tableData: Ref<WordInterfaces.fileInfo_Response[]>) => {
        return WordAPI.update_file_content(params).then(res => {
            if (res.code == 200) {
                ElMessage.success(res.msg);
                const index = tableData.value.findIndex(item => item.id === params.Fid);
                if (index !== -1) {
                    tableData.value[index].content = params.content || '';
                }
            } else {
                ElMessage.error(res.msg);
            }
        })
    }

    //更新文档密码
    export const update_word_Fpassword_dh = (params: WordInterfaces.Word_request, tableData: Ref<WordInterfaces.fileInfo_Response[]>) => {
        return WordAPI.update_word_Fpassword(params).then(res => {
            if (res.code == 200) {
                ElMessage.success(res.msg);
                const index = tableData.value.findIndex(item => item.id === params.Fid);
                if (index !== -1) {
                    tableData.value[index].password = params.Fpassword || '';
                }
            } else {
                ElMessage.error(res.msg);
            }
        })
    }

    //更新文档密钥
    export const update_word_FaesKey_dh = (params: WordInterfaces.Word_request, tableData: Ref<WordInterfaces.fileInfo_Response[]>) => {

        return WordAPI.update_word_FaesKey(params).then(res => {
            if (res.code == 200) {
                ElMessage.success(res.msg);
                const index = tableData.value.findIndex(item => item.id === params.Fid);
                if (index !== -1) {
                    tableData.value[index].aesKey = params.AesKey || '';
                }
            } else {
                ElMessage.error(res.msg);
            }
        })
    }
}