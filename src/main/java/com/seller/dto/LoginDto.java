package com.seller.dto;/**
 * created by sheting on 2018/12/11
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @program: sell
 * @description:
 * @author: ljm
 * @create: 2018-12-11 14:11
 **/
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginDto {
    @JsonProperty(value = "openid")
    private String openid;

    @JsonProperty(value = "password")
    private String password;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
