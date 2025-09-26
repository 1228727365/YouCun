package com.api.you_cun.tool;

import com.fasterxml.jackson.annotation.JsonInclude;

public class Result_content {
    private int code;
    private String msg;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String content;

    public Result_content(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result_content(int code, String msg, String content) {
        this.code = code;
        this.msg = msg;
        this.content = content;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
