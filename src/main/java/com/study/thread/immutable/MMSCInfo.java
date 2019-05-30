package com.study.thread.immutable;

/**
 * 彩信中心信息
 * 模式角色：ImmutableObject.ImmutableObject
 */
public final class MMSCInfo {

    /**
     * 设备编号
     */
    private final String deviceId;

    /**
     * 彩信中心URL
     */
    private final String url;

    /**
     * 改彩信中心允许的最大附件大小
     */
    private final int maxAttachementSizeInBytes;

    public MMSCInfo(String deviceId, String url, int maxAttachementSizeInBytes) {
        this.deviceId = deviceId;
        this.url = url;
        this.maxAttachementSizeInBytes = maxAttachementSizeInBytes;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getUrl() {
        return url;
    }

    public int getMaxAttachementSizeInBytes() {
        return maxAttachementSizeInBytes;
    }
}
