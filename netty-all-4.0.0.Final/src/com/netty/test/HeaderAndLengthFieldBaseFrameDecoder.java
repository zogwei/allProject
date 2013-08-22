package com.netty.test;

import java.nio.ByteOrder;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class HeaderAndLengthFieldBaseFrameDecoder extends
		LengthFieldBasedFrameDecoder {

	public HeaderAndLengthFieldBaseFrameDecoder(ByteOrder byteOrder,
			int maxFrameLength, int lengthFieldOffset, int lengthFieldLength,
			int lengthAdjustment, int initialBytesToStrip, boolean failFast) {
		
		super(byteOrder, maxFrameLength, lengthFieldOffset, lengthFieldLength,
				lengthAdjustment, initialBytesToStrip, failFast);
	}
}
