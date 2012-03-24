package com.jw.ess.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jw.ess.dao.IVeinDao;
import com.jw.ess.entity.Vein;
import com.jw.ess.service.IVeinService;
import com.jw.ess.util.ex.EssException;
import com.jw.ess.util.ex.MessageCode;

/**
 * 纹理表服务操作接口实现类
 * 
 * @author chenxiangbin
 * 
 */
@Service("veinService")
public class VeinService implements IVeinService
{
	private static final Log logger = LogFactory.getLog(VeinService.class);

	@Resource(name = "veinDao")
	private IVeinDao veinDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = EssException.class)
	public void addVein(Vein vein) throws EssException
	{
		vein.setIsValid(1);
		String matchedName = veinDao.findVeinName(vein.getTenantId(), vein
				.getName());
		if (matchedName != null)
		{
			logger.error("vein name " + vein.getName() + " is already exists");
			throw new EssException("vein name " + vein.getName()
					+ " is already exists",
					MessageCode.VEIN_NAME_ALREADY_EXISTS);
		}
		veinDao.insertVein(vein);
	}

	@Override
	public List<Vein> getAllVeins(int tenantId) throws EssException
	{
		// TODO Auto-generated method stub
		return veinDao.findAllVeins(tenantId);
	}

}
