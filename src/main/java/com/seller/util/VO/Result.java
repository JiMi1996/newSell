package com.seller.util.VO;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: newsell
 * @description:
 * @author: fbl
 * @create: 2018-07-15 00:01
 **/
@Data
public class Result<T>  implements Serializable{

    private static final long serialVersionUID = -3091645016044780691L;

    private Integer code;
    private Boolean success;
    private String msg;
    private T data;

    public static Result error(Integer code,String msg){
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setSuccess(false);
        return result;
    }

    public static Result success(Integer code,String msg){
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setSuccess(true);
        return result;
    }
}
