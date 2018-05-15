package com.ifly.shendong.smarthomelayout;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by shendong on 2017/3/31.
 */

public class DeviceItem implements Serializable {

    public static final int DEVICE_LIGHT = 1;
    public static final int DEVICE_DIMMER = 2;
    public static final int DEVICE_FAN = 3;
    public static final int DEVICE_AIR_CONDITIONING = 4;
    public static final int DEVICE_HUMIDIFIER = 5;
    public static final int DEVICE_AIR_PURIFIER = 6;
    public static final int DEVICE_HEATING = 7;
    public static final int DEVICE_WINDOWCURTAIN = 8;
    public static final int DEVICE_SHUTTER = 9;
    public static final int DEVICE_CURTAIN = 10;
    public static final int DEVICE_PROJECTOR = 11;
    public static final int DEVICE_CLOTHES_RACK = 12;
    public static final int DEVICE_DOOR = 13;
    private static final long serialVersionUID = 1L;
    public String name = "";
    public int type = -1;
    public String room = "";
    public static HashMap<Integer, String> mHashMap = new HashMap<Integer, String>();
    static {
        // 灯、调光灯、风扇、空调、加湿器、空气净化器、暖气、窗帘、百叶窗、幕布、投影机、晾衣架、门
        mHashMap.put(DeviceItem.DEVICE_LIGHT, "灯");
        mHashMap.put(DeviceItem.DEVICE_DIMMER, "调光灯");
        mHashMap.put(DeviceItem.DEVICE_FAN, "风扇");
        mHashMap.put(DeviceItem.DEVICE_AIR_CONDITIONING, "空调");
        mHashMap.put(DeviceItem.DEVICE_HUMIDIFIER, "加湿器");
        mHashMap.put(DeviceItem.DEVICE_AIR_PURIFIER, "空气净化器");
        mHashMap.put(DeviceItem.DEVICE_HEATING, "暖气");
        mHashMap.put(DeviceItem.DEVICE_WINDOWCURTAIN, "窗帘");
        mHashMap.put(DeviceItem.DEVICE_SHUTTER, "百叶窗");
        mHashMap.put(DeviceItem.DEVICE_CURTAIN, "幕布");
        mHashMap.put(DeviceItem.DEVICE_PROJECTOR, "投影机");
        mHashMap.put(DeviceItem.DEVICE_CLOTHES_RACK, "晾衣架");
        mHashMap.put(DeviceItem.DEVICE_DOOR, "门");
    }

    public DeviceItem() {
    }

}
