package cn.sunline.myrpc.serializa;

import cn.sunline.myrpc.common.ResponseData;
import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.io.ObjectOutputStream;

public class Encoder extends MessageToByteEncoder<ResponseData> {
    @Override
    protected void encode(ChannelHandlerContext ctx, ResponseData msg, ByteBuf out) throws Exception {
        byte[]bytes =JSONObject.toJSONString(msg).getBytes();
        out.writeBytes(bytes);
    }
}
