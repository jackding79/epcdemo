package cn.sunline.myrpc.serializa;

import cn.sunline.myrpc.common.RequestData;
import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.CharsetUtil;

import java.io.ObjectOutputStream;

public class Encoder extends MessageToByteEncoder<RequestData> {
    @Override
    protected void encode(ChannelHandlerContext ctx, RequestData msg, ByteBuf out) throws Exception {
        byte[] bytes=JSONObject.toJSONString(msg).getBytes();
        out.writeBytes(bytes);
    }
}
