package cn.sunline.myrpc.serializa;

import cn.sunline.myrpc.common.ResponseData;
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
        ResponseData responseData= JSONObject.parseObject(in.toString(CharsetUtil.UTF_8),ResponseData.class);
        out.add(responseData);
        in.skipBytes(in.readableBytes());
    }
}
