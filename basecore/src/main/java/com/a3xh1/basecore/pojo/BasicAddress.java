package com.a3xh1.basecore.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: GIndoc on 2017/5/25 17:13
 * email : 735506583@qq.com
 * FOR   :
 * "id": 1,
 * "parentid": 0,
 * "status": 0,
 * "createtime": 1460770138000,
 * "pykey": "B",
 * "levelval": 1
 */

public class BasicAddress implements Parcelable {
    private int id;
    private String parentid;
    private String addressname;
    private String pykey;
    private int status;
    private int levelval;
    private long createtime;
    private String lat;
    private String lon;
    @SerializedName("modelList")
    private List<BasicAddress> modelList;

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public String getAddressname() {
        return addressname;
    }

    public void setAddressname(String addressname) {
        this.addressname = addressname;
    }

    public String getPykey() {
        return pykey;
    }

    public void setPykey(String pykey) {
        this.pykey = pykey;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getLevelval() {
        return levelval;
    }

    public void setLevelval(int levelval) {
        this.levelval = levelval;
    }

    public long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }

    public List<BasicAddress> getModelList() {
        return modelList;
    }

    public void setModelList(List<BasicAddress> modelList) {
        this.modelList = modelList;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.parentid);
        dest.writeString(this.addressname);
        dest.writeString(this.pykey);
        dest.writeInt(this.status);
        dest.writeInt(this.levelval);
        dest.writeLong(this.createtime);
        dest.writeString(this.lat);
        dest.writeString(this.lon);
        dest.writeList(this.modelList);
    }

    public BasicAddress() {
    }

    protected BasicAddress(Parcel in) {
        this.id = in.readInt();
        this.parentid = in.readString();
        this.addressname = in.readString();
        this.pykey = in.readString();
        this.status = in.readInt();
        this.levelval = in.readInt();
        this.createtime = in.readLong();
        this.lat = in.readString();
        this.lon = in.readString();
        this.modelList = new ArrayList<BasicAddress>();
        in.readList(this.modelList, BasicAddress.class.getClassLoader());
    }

    public static final Creator<BasicAddress> CREATOR = new Creator<BasicAddress>() {
        @Override
        public BasicAddress createFromParcel(Parcel source) {
            return new BasicAddress(source);
        }

        @Override
        public BasicAddress[] newArray(int size) {
            return new BasicAddress[size];
        }
    };
}
