package com.a3xh1.basecore.pojo.response;


import com.google.gson.annotations.SerializedName;

public class Response<T> {
    private boolean status;

    private int erro_code;

    private String desc;

    @SerializedName(value = "data", alternate = {"borders"})
    private T data;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getErro_code() {
        return erro_code;
    }

    public void setErro_code(int erro_code) {
        this.erro_code = erro_code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
