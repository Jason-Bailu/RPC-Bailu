package org.bailu.rpc.constant;

public enum MsgType {
    REQUEST,
    RESPONSE,
    HEARTBEAT;

    public static MsgType findByType(int type) {
        return MsgType.values()[type];
    }
}
