export namespace RegisInterface {
  export interface regisRequest {
    mail: string;
    password?: string;
    code?: string;
  }


  export interface regisResponse {
    code: number;
    msg: string;
  }
}