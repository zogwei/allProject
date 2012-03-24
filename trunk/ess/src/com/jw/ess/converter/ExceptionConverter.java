package com.jw.ess.converter;

import org.springframework.stereotype.Component;

import com.jw.ess.converter.constant.Commons;
import com.jw.ess.util.ex.EssException;
import com.jw.ess.util.msg.MessageHelper;

@Component(value = "exceptionConverter")
public class ExceptionConverter extends DefaultXmlConverter<EssException> {
	@Override
	public EssException fromXml(String xml) {
		throw new UnsupportedOperationException();
	}

	@Override
	protected String toContent(EssException t) {
		StringBuilder content = new StringBuilder();
		content.append(Commons.ERROR_START);
		content.append(Commons.ERROR_CODE_START);
		content.append(t.getErrorCode());
		content.append(Commons.ERROR_CODE_END);
		content.append(Commons.ERROR_TEXT_START);
		content.append(MessageHelper.getMessageText(t.getErrorCode()));
		content.append(Commons.ERROR_TEXT_END);
		content.append(Commons.ERROR_END);
		return content.toString();
	}

}
