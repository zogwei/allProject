package com.ifinanceweb.mvc;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

public class CRUDActionSupport<T>  extends com.opensymphony.xwork2.ActionSupport implements ModelDriven<T>,Preparable,TargetAware
{

	 	public static final String RELOAD = "reload";

	    public static final String RESULT_JSP = "jsp";

	    public static final String RESULT_JSON = "json";

	    public static final String RESULT_FTL = "ftl";

	    public static final String RESULT_VM = "vm";

	    public static final String RESULT_SYS = "sys";

	    public static final String RESULT_ACTION = "redirectAction";

	    public static final String DEFAULT_RESULT_MESSAGE_DISPLAY = "messageDisplay";

	    public static final String DEFAULT_BACK_INPUT = "backInput";

	    public static final String DEFAULT_PARE_CURRENT = "paras.currentPage";

	    public static final String DEFAULT_PARE_SIZE = "paras.pageSize";

	    public static final String SUCCESS = "success";

	    public static final String ACTION_OK = "actionOk";
	    
	    
	    protected String targetUrl;


		public String getTargetUrl() {
			return targetUrl;
		}


		public void setTargetUrl(String targetUrl) {
			this.targetUrl = targetUrl;
		}


		@Override
		public void prepare() throws Exception {
			// TODO Auto-generated method stub
			
		}


		@Override
		public T getModel() {
			// TODO Auto-generated method stub
			return null;
		}
	    
	    

}
