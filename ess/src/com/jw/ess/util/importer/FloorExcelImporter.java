package com.jw.ess.util.importer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import com.jw.ess.entity.ColorCode;
import com.jw.ess.entity.Floor;
import com.jw.ess.entity.FloorCategory;
import com.jw.ess.entity.Spec;
import com.jw.ess.entity.Supplier;
import com.jw.ess.entity.Vein;
import com.jw.ess.util.ex.EssException;
import com.jw.ess.util.ex.MessageCode;

/**
 * 用excel文件导入地板信息工具类
 * 
 * @author j&w
 * 
 */
@Component("floorExcelImporter")
public class FloorExcelImporter<T> implements Importer<T> {
	private static final Log logger = LogFactory.getLog(FloorExcelImporter.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<T> importFrom(InputStream in ,String fileName) throws EssException {
		Floor floor = null;
		List<Floor> list = new ArrayList<Floor>();
		
		Workbook wb;
		try {
			
			wb = createWorkBook(in ,fileName);
			Sheet sheet = wb.getSheetAt(0);		
			Iterator rows = sheet.rowIterator();
			while (rows.hasNext()) {
				floor = new Floor();
				floor.setSupplier(new Supplier());
				floor.setCategory(new FloorCategory());
				floor.setSpec(new Spec());
				floor.setColorCode(new ColorCode());
				floor.setVein(new Vein());
				floor.getVein().setName("vein");
				Row row = (Row) rows.next();
				if (row.getRowNum() <= 0)					
					continue;		
				int flag = 0; 
				Iterator cells = row.cellIterator();
				while (cells.hasNext()) {
					
					Cell cell = (Cell) cells.next();
					switch (flag) {
					case 0:
						floor.setNumber(cell.getStringCellValue());
						
					    break;
					case 1:
						floor.setName(cell.getStringCellValue());
						break;
					case 2:
						floor.getSupplier().setName(cell.getStringCellValue());
						break;
					case 3:
						floor.getCategory().setName(cell.getStringCellValue());
						break;
					case 4:
						floor.getSpec().setName(cell.getStringCellValue());
						break;
					case 5:
						floor.getColorCode().setName(cell.getStringCellValue());
						break;
					/*case 6:
						floor.getVein().setName(cell.getStringCellValue());
						break;
					*/
					case 6:
						floor.setSellPrice((float) cell.getNumericCellValue());
						break;
					case 7:
						floor.setDesc(cell.getStringCellValue());
						break;
					default:
						System.out.println("unsuported cell type");
						break;
					}
					flag++;
				}
				list.add(floor);
			}
	     
			for (Floor floor1 : list) {
				System.out.println(floor1);
			}
			
		
		} catch (IOException e) {
			logger.error("failed to importExcelFile", e);
			throw new EssException(e, MessageCode.FLOOR_EXCEL_FILE_ERROR);
		}
		catch(IllegalStateException e){
			logger.error("failed to importExcelFile", e);
			throw new EssException(e, MessageCode.FLOOR_EXCEL_FILE_ERROR);
		}
		
		return (List<T>) list;
		
		

	}
	
	public Workbook createWorkBook(InputStream in ,String fileName) throws IOException{
		if(fileName.toLowerCase().endsWith("xls")){
            return new HSSFWorkbook(in);
        }
        if(fileName.toLowerCase().endsWith("xlsx")){
            return new XSSFWorkbook(in);
        }
        return null;
      
		
	}
	
	
	
	public static void main(String[] args) {

		FloorExcelImporter importer = new FloorExcelImporter();
		try {
			importer.importFrom(new FileInputStream(
					"C:\\Documents and Settings\\issuser\\桌面\\地板资料用例2.xlsx"),"C:\\Documents and Settings\\issuser\\桌面\\地板资料用例2.xlsx");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EssException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}

	
}