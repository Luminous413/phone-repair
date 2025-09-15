package com.lmscr.phonerepair.service.util;

import lombok.Data;

/**
 * 通用返回结果类
 *
 * @param <T> 数据类型
 */
@Data
public class Result<T> {
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 消息
     */
    private String msg;
    /**
     * 数据
     */
    private T data;
    /**
     * 时间戳
     */
    private long timestamp;

    /**
     * 失败结果构造方法
     *
     * @param msg  消息
     * @param code 状态码
     * @return 失败结果
     */
    public static <T> Result<T> fail(String msg, Integer code) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(null);
        result.setTimestamp(System.currentTimeMillis());
        return result;
    }

    /**
     * 成功结果构造方法
     *
     * @param data 数据
     * @return 成功结果
     */
    public static <T> Result<T> success(String msg, T data) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMsg(msg);
        result.setData(data);
        result.setTimestamp(System.currentTimeMillis());
        return result;
    }

}
