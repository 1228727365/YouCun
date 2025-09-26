//登陆请求参数

export namespace LoginInterface {
  export interface LoginRequest {
    mail: string;
    password?: string;
    token?: string;
    Token?: string;
    km?: string;
    newPassword?: string;
  }

  // 登录响应中的数据部分
  export interface LoginResponseData {
    moreSize: number;
    useSize: string;
    fileNumber: number;
    imgNumber: number;
    vip_time: string;
  }

  // 完整的登录响应
  export interface LoginResponse {
    code: number;
    msg: string;
    token?: string;
    data?: LoginResponseData;
  }
}