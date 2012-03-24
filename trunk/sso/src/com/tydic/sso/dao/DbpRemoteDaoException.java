package com.tydic.sso.dao;

public class DbpRemoteDaoException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3757162843304089772L;
	/**
     * Constructs an exception with the supplied message.
     *
     * @param string the message
     */
	public DbpRemoteDaoException(final String msg){
		super(msg);
	}
    /**
     * Constructs an exception with the supplied message and chained throwable.
     *
     * @param string the message
     * @param throwable the original exception
     */
	public DbpRemoteDaoException(final String msg,final Throwable throwable){
		super(msg,throwable);
	}
    /**
     * Constructs an exception with the chained throwable.
     * @param throwable the original exception.                                    
     */	
	public DbpRemoteDaoException(final Throwable throwable){
		super(throwable);
	}
}
