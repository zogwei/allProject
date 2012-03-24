package com.tydic.sso.service;

public class DbpRemoteException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4085080848086873430L;

	/**
     * Constructs an exception with the supplied message.
     *
     * @param string the message
     */
	public DbpRemoteException(final String msg){
		super(msg);
	}
    /**
     * Constructs an exception with the supplied message and chained throwable.
     *
     * @param string the message
     * @param throwable the original exception
     */
	public DbpRemoteException(final String msg,final Throwable throwable){
		super(msg,throwable);
	}
    /**
     * Constructs an exception with the chained throwable.
     * @param throwable the original exception.                                    
     */	
	public DbpRemoteException(final Throwable throwable){
		super(throwable);
	}
}
