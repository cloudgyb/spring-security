package io.github.cloudgyb.springsecurity02.common.po;

import java.io.Serializable;

/**
 * API统一响应
 *
 * @author cloudgyb
 * 2021/9/5 20:14
 */
public final class ApiResult implements Serializable {
    private final static int okCode = 200;
    private final static int errorCode = 500;

    private Integer code;
    private String msg;
    private Object data;

    public ApiResult() {
    }

    public static ApiResult ok() {
        return new ApiResult(okCode, "", null);
    }

    public static ApiResult ok(Object data) {
        return new ApiResult(okCode, "", data);
    }

    public static ApiResult ok(String msg) {
        return new ApiResult(okCode, msg, null);
    }

    public static ApiResult ok(int code, String msg) {
        return new ApiResult(code, msg, null);
    }

    public static ApiResult ok(int code, String msg, Object data) {
        return new ApiResult(code, msg, data);
    }

    public static ApiResult error(String msg) {
        return new ApiResult(errorCode, msg, null);
    }

    public static ApiResult error(int code) {
        return new ApiResult(code, "", null);
    }

    public static ApiResult error(Object data) {
        return new ApiResult(errorCode, "", data);
    }

    public static ApiResult error(int code, String msg) {
        return new ApiResult(code, msg, null);
    }

    public static ApiResult error(int code, String msg, Object data) {
        return new ApiResult(code, msg, data);
    }

    public ApiResult(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
