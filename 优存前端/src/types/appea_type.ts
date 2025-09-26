export namespace AppInterface {
  // 软件图表信息
  export interface StatsItem {
    title: string;
    value: number;
    unit: string;
  }

  //获取用户信息
  export interface userInfo {
    mail: string;
    token: string;
  }


  //获取软件图表信息
  interface globalInfo {
    file_count: number,
    user_count: number,
    image_count: number
  }

  // 完整的登录响应
  export interface globalInfoResponse {
    code: number;
    msg: string;
    data: globalInfo;
  }
}