package com.jw.ess.dao.impl;

import static org.junit.Assert.fail;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;

import com.jw.ess.dao.IOrderStateTraceDao;
import com.jw.ess.entity.OrderStateTrace;
import com.jw.ess.util.CommonConstant;
import com.jw.ess.util.SpringAssisant;
import com.jw.ess.util.ex.EssException;

public class OrderStateTraceDaoTest {
	private static final Log logger = LogFactory.getLog(OrderStateTraceDaoTest.class);

	private IOrderStateTraceDao stateTraceDao;

	@Before
	public void setUp() throws Exception {
		stateTraceDao = (IOrderStateTraceDao) SpringAssisant.getBean("orderStateTraceDao");
	}

	@Test
	public void testInsertOrderStateTrace() {
		OrderStateTrace stateTrace = new OrderStateTrace();
		stateTrace.setStateId(CommonConstant.ORDER_STATE_BOOK);
		stateTrace.setOrderId(2);
		stateTrace.setOperateDate(1324269068);

		try {
			stateTraceDao.insertOrderStateTrace(stateTrace);
		} catch (EssException e) {
			logger.error(e);
			fail("failed to testInsertOrderStateTrace");
		}
	}
	
	@Test
	public void testFindOperateDate()
	{
		try {
			System.out.println(stateTraceDao.findConfirmDate(4));
		} catch (EssException e) {

			e.printStackTrace();
		}
	}
}
