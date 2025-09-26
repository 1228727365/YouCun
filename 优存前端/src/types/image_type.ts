export namespace imageinterface {
    export interface Image_request {
        mail: string;
        token: string;
        Tid?: number;
        page?:number;
    }

    export interface Image_response {
        code: number;
        msg: string;
        data?:imageInfo_Response;
    }

    export interface imageInfo_Response {
        id: number;
        uid: number;
        name: string;
        size: string;
        date: string;
        url: string;
    }
}