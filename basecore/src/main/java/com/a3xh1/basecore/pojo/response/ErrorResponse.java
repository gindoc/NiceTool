package com.a3xh1.basecore.pojo.response;


import com.google.gson.annotations.SerializedName;

public class ErrorResponse {
    private boolean status;

    private int erro_code;

    private String desc;

    @SerializedName(value = "data", alternate = {"borders"})
    private String data;

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
