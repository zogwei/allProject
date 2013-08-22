package com.netty.test;

public class MessageList {

	public static void main(String[] arg){
		int initialCapacity = -7;
		int temp = initialCapacity >>>  1;;
		initialCapacity |= temp;
		temp = initialCapacity >>>  2;
        initialCapacity |= temp;
        temp = initialCapacity >>>  4;
        initialCapacity |= temp;
        temp = initialCapacity >>>  8;
        initialCapacity |= temp;
        temp = initialCapacity >>>  16;
        initialCapacity |= temp;
        initialCapacity ++;

        if (initialCapacity < 0) {
            initialCapacity >>>= 1;
        }
	}
}
