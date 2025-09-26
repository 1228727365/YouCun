
import * as echarts from 'echarts';
import { AppeaAPI } from '../../home/appea/appea_api';
import { ElMessage } from 'element-plus';
import routes from '@/router';
import type { AppInterface } from '@/types/appea_type'
import type { LoginInterface } from '@/types/login_type';
import { defineStore } from 'pinia'
import { reactive } from 'vue';


export namespace AppeaAPI_click {

    export const useStatsStore = defineStore('stats', () => {
        // 定义响应式统计数据
        const statsData: AppInterface.StatsItem[] = reactive([
            { title: '文档数据', value: 0, unit: '个' },
            { title: '图片数据', value: 0, unit: '张' },
            { title: '储存数据', value: 0, unit: 'MB' },
            { title: '扩展数据', value: 0, unit: 'MB' }
        ]);

        // 更新单个统计项
        const updateStat = (index: number, value: number) => {
            if (statsData[index]) {
                statsData[index].value = value;
            }
        };
        return {
            statsData,
            updateStat
        };
    })

    export function init_word_count(word_count: string, user_number: number, user_text: string, image_number: number, image_text: string, word_number: number, word_text: string) {
        var chartDom = document.getElementById(word_count);
        var myChart = echarts.init(chartDom);
        var option;
        option = {
            color: ['#3B82F6 ', '#10B981 ', '#F97316 '],
            tooltip: {
                trigger: 'item'
            },
            legend: {
                top: '20%',
                left: 'center',
            },
            series: [
                {
                    name: 'Access From',
                    type: 'pie',
                    radius: ['40%', '70%'],
                    center: ['50%', '70%'],
                    startAngle: 180,
                    endAngle: 360,
                    data: [
                        { value: user_number, name: user_text },
                        { value: image_number, name: image_text },
                        { value: word_number, name: word_text },
                    ]
                }
            ]
        };
        option && myChart.setOption(option);
    }



    export function init_appea_info(word_count: string, user_text: string, image_text: string, word_text: string) {
        AppeaAPI.get_All_info().then(res => {
            if (res.code == 200) {
                init_word_count(word_count, res.data.user_count, user_text, res.data.image_count, image_text, res.data.file_count, word_text)

            }
        })
    }

    export function init_get_user_info(params: LoginInterface.LoginRequest, statsData: AppInterface.StatsItem[]) {
        if (!params.mail || !params.token) {
            ElMessage.error("用户信息缺失，请重新登录");
            routes.push('/login');
            return Promise.resolve({ code: 401, msg: "用户信息缺失，请重新登录" });
        }
        AppeaAPI.get_user_info(params).then(res => {
            if (res.code == 200) {
                if (res.data) {
                    localStorage.setItem('Info', JSON.stringify(res.data))
                    statsData[0].value = res.data.fileNumber
                    statsData[1].value = res.data.imgNumber
                    const useSizeInMB = Number(res.data.useSize) / 1024 / 1024;
                    statsData[2].value = isNaN(useSizeInMB) ? 0 : Number(useSizeInMB.toFixed(2));

                    const moreSizeInMB = Number(res.data.moreSize) / 1024 / 1024;
                    statsData[3].value = isNaN(moreSizeInMB) ? 0 : Number(moreSizeInMB.toFixed(2));
                }
            } else {
                ElMessage.error(res.msg)
                routes.push('/login')
            }
        }).catch(err => {
            console.log(err)
            ElMessage.error(err)
            routes.push('/login')
        })
    }
}