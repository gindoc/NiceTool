package com.a3xh1.basecore.utils.gson;

import java.io.IOException;

/**
 * Author: GIndoc on 2017/7/18 下午12:53
 * email : 735506583@qq.com
 * FOR   :
 */
public class ResultException extends IOException {
    private boolean status;
    private String errMsg;

    public ResultException(boolean status, String errMsg) {
        this.status = status;
        this.errMsg = errMsg;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
