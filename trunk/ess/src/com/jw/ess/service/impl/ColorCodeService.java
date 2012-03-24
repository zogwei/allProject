package com.jw.ess.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jw.ess.dao.IColorCodeDao;
import com.jw.ess.entity.ColorCode;
import com.jw.ess.service.IColorCodeService;
import com.jw.ess.util.ex.EssException;
import com.jw.ess.util.ex.MessageCode;

/**
 * 色号表服务操作接口实现类
 * 
 * @author j&w
 * 
 */
@Service("colorCodeService")
public class ColorCodeService implements IColorCodeService
{
	private static final Log logger = LogFactory.getLog(ColorCodeService.class);

	@Resource(name = "colorCodeDao")
	private IColorCodeDao colorCodeDao;

	@Override
	public List<ColorCode> getAllColorCodes(int tenantId) throws EssException
	{
		return colorCodeDao.findAllColorCodes(tenantId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = EssException.class)
	public void addColorCode(ColorCode colorCode) throws EssException
	{
		String matchedName = colorCodeDao.findColorCodeName(colorCode
				.getTenantId(), colorCode.getName());
		if (matchedName != null)
		{
			logger.error("color code name " + colorCode.getName()
					+ " is already exists");
			throw new EssException("color code name " + colorCode.getName()
					+ " is already exists", MessageCode.SPEC_NAME_ALREADY_EXISTS);
		}
		colorCode.setIsValid(1);
		colorCodeDao.insertColorCode(colorCode);
	}

}
