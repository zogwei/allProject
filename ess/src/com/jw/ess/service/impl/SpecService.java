package com.jw.ess.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jw.ess.dao.ISpecDao;
import com.jw.ess.entity.Spec;
import com.jw.ess.service.ISpecService;
import com.jw.ess.util.ex.EssException;
import com.jw.ess.util.ex.MessageCode;

/**
 * 规格表服务操作接口实现类
 * 
 * @author j&w
 * 
 */
@Service("specService")
public class SpecService implements ISpecService
{
	private static final Log logger = LogFactory.getLog(SpecService.class);

	@Resource(name = "specDao")
	private ISpecDao specDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = EssException.class)
	public void addSpec(Spec spec) throws EssException
	{
		spec.setIsValid(1);
		String matchedSpecName = specDao.findSpecName(spec.getTenantId(), spec
				.getName());
		if (matchedSpecName != null)
		{
			logger.error("spec name " + spec.getName() + " is already exists");
			throw new EssException("spec name " + spec.getName()
					+ " is already exists", MessageCode.SPEC_NAME_ALREADY_EXISTS);
		}
		specDao.insertSpec(spec);
	}

	@Override
	public List<Spec> getfindAllSpecs(int tenantId) throws EssException
	{
		return specDao.findAllSpecs(tenantId);
	}

}
