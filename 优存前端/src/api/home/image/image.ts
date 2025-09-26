import type { imageinterface } from "@/types/image_type";
import { imageAPI } from "./image_api";
import { ElMessage } from 'element-plus';
import type { Ref } from "vue";
import { AppeaAPI_click } from "../appea/appea";
import router from "@/router";

export namespace ImageAPI_click {

    //上传图片
    export const upload_image_dh = (prams: imageinterface.Image_request, file: File, image_sum_page: Ref<Number>,tableData:Ref<imageinterface.imageInfo_Response[]>) => {
        return imageAPI.upload_image(prams, file).then(res => {
            if (res.code === 200) {
                //更新图片列表

                get_image_list_dh({
                    mail: prams.mail,
                    token: prams.token,
                    page: 1
                },tableData)

                ElMessage.success(res.msg);
                AppeaAPI_click.useStatsStore().updateStat(
                    1, AppeaAPI_click.useStatsStore().statsData[1].value + 1)
                image_sum_page.value = AppeaAPI_click.useStatsStore().statsData[1].value
            } else {
                ElMessage.error(res.msg);
            }
        })
    }

    //获取所有图片
    export const get_image_list_dh = (prams: imageinterface.Image_request, tableData: Ref<imageinterface.imageInfo_Response[]>) => {
        return imageAPI.get_image_list_dh(prams).then(res => {
            if (res.code === 200) {
                if (Array.isArray(res.data)) {
                    tableData.value = res.data;
                } else {
                    ElMessage.warning('获取的数据格式不正确');
                    tableData.value = [];
                }
            } else {
                ElMessage.error(res.msg);
                router.push('/login');
                return null;
            }
        }).catch(err => {
            console.log(err);
            ElMessage.error('请求失败，请重新登录');
            router.push('/login');
            return null;
        })
    }

    //删除图片
    export const delete_image_dh = (prams: imageinterface.Image_request, image_sum_page: Ref<Number>,tableData:Ref<imageinterface.imageInfo_Response[]>) => {
        return imageAPI.delete_image(prams).then(res => {
            if (res.code == 200) {
                ElMessage.success(res.msg);

                get_image_list_dh({
                    mail: prams.mail,
                    token: prams.token,
                    page: 1
                },tableData)


                AppeaAPI_click.useStatsStore().updateStat(
                    1, AppeaAPI_click.useStatsStore().statsData[1].value - 1)
                image_sum_page.value = AppeaAPI_click.useStatsStore().statsData[1].value

            } else {
                ElMessage.error(res.msg);
            }
        })
    }
}
