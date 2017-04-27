package com.chm.myapplication.entity;

import com.chm.myapplication.common.PinYinUtil;

/**
 * Created by ason on 2017/1/23.
 */
public class RegionInfo {
    private String addr;
    private String code;

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public RegionInfo(String addr) {
        this.addr = addr;
        this.code = PinYinUtil.getFirstPinYin(addr);
    }
}
