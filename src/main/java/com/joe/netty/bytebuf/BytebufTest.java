package com.joe.netty.bytebuf;/**
 * @author joe
 * @date 2018/10/23/023
 */

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

/**
 * @author joe
 * @description:
 * @date 2018/10/23/023
 */
public class BytebufTest {
    private static void print(String action, ByteBuf target) {
        System.out.println("=============after: " + action + "==============");
        System.out.println("============capacity: " + target.capacity() + "==============");
        System.out.println("=============maxCapacity: " + target.maxCapacity() + "==============");
        System.out.println("=============readerIndex: " + target.readerIndex() + "==============");
        System.out.println("=============readableBytes: " + target.readableBytes() + "==============");
        System.out.println("=============writerIndex: " + target.writerIndex() + "==============");
        System.out.println("=============writableBytes: " + target.writableBytes() + "==============");
        System.out.println("=============isWritable: " + target.isWritable() + "==============");
        System.out.println("=============maxWritableBytes: " + target.maxWritableBytes() + "==============");
        System.out.println();
    }

    public static void main(String[] args) {
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(10, 100);
        print("allocate ByteBuf(9, 100)", buffer);

        //write 方法改变写指针，写完之后写指针未到 capacity 的时候，buffer 仍然可写
        buffer.writeBytes(new byte[]{1, 2, 3, 4, 5});
        print("writeBytes", buffer);

        // write 方法改变写指针，写完之后写指针未到 capacity 的时候，buffer 仍然可写, 写完 int 类型之后，写指针增加4
        buffer.writeInt(12);
        print("writeInt", buffer);

        // write 方法改变写指针, 写完之后写指针等于 capacity 的时候，buffer 不可写
        buffer.writeBytes(new byte[]{6});
        print("writeBytes(6)", buffer);

        // write 方法改变写指针，写的时候发现 buffer 不可写则开始扩容，扩容之后 capacity 随即改变
        buffer.writeBytes(new byte[]{7});
        print("writeBytes(7)", buffer);

        // get 方法不改变读写指针
        System.out.println("getByte(3) return: " + buffer.getByte(3));
        System.out.println("getShort(3) return: " + buffer.getShort(3));
        System.out.println("getInt(3) return: " + buffer.getInt(3));
        print("getByte()", buffer);


        // set 方法不改变读写指针
        buffer.setByte(buffer.readableBytes() + 1, 0);
        print("setByte()", buffer);

        // read 方法改变读指针
        byte[] dst = new byte[buffer.readableBytes()];
        buffer.readBytes(dst);
        print("readBytes(" + dst.length + ")", buffer);
    }
}
