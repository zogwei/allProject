package com.netty.test.hex;

public class HexTable {

	private static final char[] HEXDUMP_TABLE = new char[256 * 4];

    static {
        final char[] DIGITS = "0123456789abcdef".toCharArray();
        for (int i = 0; i < 256; i ++) {
            HEXDUMP_TABLE[ i << 1     ] = DIGITS[i >>> 4 & 0x0F];
            HEXDUMP_TABLE[(i << 1) + 1] = DIGITS[i       & 0x0F];
        }
    }
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.print(HEXDUMP_TABLE);
	}

}
