package com.user.login.web;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResultMsg {
    /**
     * 错误码
     */
    private int resultCode;
    private String resultMessage;
    private Object resultObject;
    public ResultMsg() {
    }

    public ResultMsg(int resultCode, String resultMessage) {
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
    }

    public ResultMsg(int resultCode, String resultMessage, Object resultObject) {
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
        this.resultObject = resultObject;
    }


    @Override
    public String toString() {
        return "ResultMsg{" +
                "resultCode=" + resultCode +
                ", resultMessage='" + resultMessage + '\'' +
                ", resultObject=" + resultObject +
                '}';
    }


}
