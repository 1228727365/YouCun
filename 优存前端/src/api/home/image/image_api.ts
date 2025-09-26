import http from '@/http/reque'
import type { imageinterface } from '@/types/image_type'

export namespace imageAPI {


    //上传图片
    export const upload_image = (prams: imageinterface.Image_request, file: File): Promise<imageinterface.Image_response> => {
        if (!file) {
            return Promise.reject(new Error('文件不能为空'));
        }
        const formData = new FormData();
        formData.append('file', file, file.name);
        return http({
            url: '/user/imgUpload',
            method: 'post',
            params: prams,
            data: formData,
        })
    }

    //获取所有图片
    export const get_image_list_dh = (prams: imageinterface.Image_request): Promise<imageinterface.Image_response> => {
        return http({
            url: '/user/getImageAll',
            method: 'post',
            params: prams,
        })
    }

    //删除图片
    export const delete_image = (prams: imageinterface.Image_request): Promise<imageinterface.Image_response> => {
        return http({
            url: '/user/DelImage',
            method: 'post',
            params: prams,
        })
    }
}