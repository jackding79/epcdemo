package cn.jackding.rpc.core.serializa;

import cn.jackding.rpc.core.common.ResponseData;
import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class Encoder extends MessageToByteEncoder<ResponseData> {
    @Override
    protected void encode(ChannelHandlerContext ctx, ResponseData msg, ByteBuf out) throws Exception {
        byte[]bytes;
        bytes=JSONObject.toJSONString(msg).getBytes();
        out.writeBytes(bytes);
    }
}
