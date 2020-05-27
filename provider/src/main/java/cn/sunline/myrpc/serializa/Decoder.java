package cn.sunline.myrpc.serializa;

import cn.sunline.myrpc.common.RequestData;
import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.CharsetUtil;

import java.io.ObjectInputStream;
import java.util.List;

public class Decoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
       RequestData requestData= JSONObject.parseObject(in.toString(CharsetUtil.UTF_8),RequestData.class);
       out.add(requestData);
       in.skipBytes(in.readableBytes());
    }
}
