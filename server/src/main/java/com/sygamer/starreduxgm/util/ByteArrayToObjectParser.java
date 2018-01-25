package com.sygamer.starreduxgm.util;

import com.alibaba.fastjson.JSON;
import com.squareup.okhttp.Response;
import com.sygamer.starreduxgm.buffer.Msg;
import com.sygamer.starreduxgm.buffer.MsgDecoder;

import java.io.IOException;

public class ByteArrayToObjectParser {

    public static Msg parse(byte[] data) throws IOException {
        return MsgDecoder.decode(data);
    }

    public static <T> T parse(byte[] data, Class<T> clazz) throws IOException {
        return JSON.parseObject(data, clazz);
    }

    public static Msg parse(Response rs) throws IOException {
        int len = (int) rs.body().contentLength();
        byte[] data = new byte[len];
        rs.body().byteStream().read(data, 0, len);
        return parse(data);
    }

    public static <T> T parse(Response rs, Class<T> clazz) throws IOException {
        int len = (int) rs.body().contentLength();
        byte[] data = new byte[len];
        rs.body().byteStream().read(data, 0, len);
        return JSON.parseObject(data, clazz);
    }
}
