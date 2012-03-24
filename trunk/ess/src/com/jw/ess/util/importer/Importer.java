package com.jw.ess.util.importer;

import java.io.InputStream;
import java.util.List;

import com.jw.ess.util.ex.EssException;

/**
 * 文件导入器
 * 
 * @author j&w
 * 
 */
public interface Importer<T>
{
	List<T> importFrom(InputStream in ,String fileName) throws EssException;
}
