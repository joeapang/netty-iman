package com.joe.netty.packet;/**
 * @author joe
 * @date 2018/10/23/023
 */

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author joe
 * @description: 服务端或者客户端每收到一种指令都会有相应的处理逻辑,
 * 所有的指令数据包都必须实现这个方法，这样我们就可以知道某种指令的含义
 * @date 2018/10/23/023
 */
@Data
public abstract class BasePacket {
    @JSONField(serialize = false, deserialize = false)
    private Byte version = 1;

    @JSONField(serialize = false)
    public abstract Byte command();
}
