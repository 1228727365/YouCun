export namespace WordInterfaces {
  // 获取文档列表请求
  export interface Word_request {
    mail: string;
    token: string;
    page?: number;
    password?: string;
    content?: string;
    Fid?: number;
    state?: number;
    Ftoken?: string;
    Fpassword?: string;
    AesKey?: string;
  }

  // 文档列表响应
  export interface Word_Response {
    code: number;
    msg: string;
    token?: string;
    data?: fileInfo_Response;
  }

  // 文档信息内容
  export interface fileInfo_Response {
    id: number;
    uid: number;
    content: string;
    date: string;
    token: string;
    state: number;
    password: string;
    aesKey: string;
  }

  // 更新文档表单类型
  export interface FormUpdateType {
    id: number;
    content: string;
    aesKey: string;
    password: string;
    token: string;
  }
}
