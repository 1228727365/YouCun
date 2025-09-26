package com.api.you_cun.tool;

public class ResultUtil {

    //成功
    public static Result success(String msg){
        return new Result(200,msg);
    }

    //成功返回数据
    public static Result success(String msg,String token,Object data){
        return new Result(200,msg,token,data);
    }

    public static Result_content success_content(String msg,String content){
        return new Result_content(200,msg,content);
    }


    //成功并返回token
    public static Result success(String msg,String token){
        return new Result(200,msg,token);
    }


    public static Result success(String msg,Object data){
        return new Result(200,msg,data);
    }

    //认证失败
    public static Result error(String msg){
        return new Result(401,msg);
    }

}
