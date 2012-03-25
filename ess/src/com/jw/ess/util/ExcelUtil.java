package com.jw.ess.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;


import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class ExcelUtil {
//    public static final String CONFIG_LOACTION = "/META-INF/";
//    
//    public static void main(String[] args) throws RowsExceededException, WriteException,IOException ,Exception{
//        List inputParam = new ArrayList();
//        List rowList = new ArrayList();
//        List list = null;
//        for(int index = 0;index<3000;index++){
//            list = new ArrayList();
//            
//            list.add("1");
//            list.add("1000000000020000");
//            list.add("北京");
//            list.add("集团公司");
//            list.add("集团公司");
//            list.add("国家开发银行");
//            list.add("金融");
//            list.add("全球");
//            list.add("4A");
//            list.add("0.64988");
//            list.add("1.530263");
//            list.add("0.245959");
//            list.add("0.030146");
//            list.add("0");
//            list.add("0");
//            list.add("0");
//            list.add("0");
//            list.add("1.300672");
//            list.add("0");
//            list.add("3.901228");
//            list.add("0");
//            list.add("0");
//            list.add("0");
//            list.add("0.146285");
//           
//            rowList.add(list);
//        }
//        List headList = new ArrayList();
//        headList.add("第一列标题");
//        headList.add("第二列标题");
//        headList.add("第三列标题");
////        HSSFWorkbook exportxls = createExcelBook(rowList,headList);
//        HSSFWorkbook exportxls = createExcelBookToStringForReport(rowList,6);
//        FileOutputStream fileOut = new FileOutputStream("e:\\book1.xls");  
//        exportxls.write(fileOut);
//        fileOut.flush();   
//        fileOut.close();   
//        
//
//    }
    
    /**
     * 
     * @param inputParam
     * @return HSSFWorkbook
     * @desc 根据一个双List数据结构生成一个表格
     *       需要手动传入表头，否则生成的excel表格没有表头
     * @version1.0
     */
    public  static HSSFWorkbook createExcelBook(List inputParam,List headList) throws IOException{
        HSSFWorkbook workBook = new HSSFWorkbook();
        List diviList = divisionList(inputParam,65000);
        short defautwidth=30;
        if(diviList!=null&&diviList.size()>0){
            for(int sheetIndex = 0;sheetIndex<diviList.size();sheetIndex++){
                List sheetList = (List)diviList.get(sheetIndex);
                HSSFSheet sheet = workBook.createSheet("exportsheet");
                sheet.setDefaultColumnWidth(defautwidth);
                int yenum = sheetIndex+1;
                workBook.setSheetName(sheetIndex, "第"+yenum+"页");
                List oneRowList=null;
                HSSFRow row=null;
                //自定义一个样式
                HSSFCellStyle style = workBook.createCellStyle();
                style.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
                if(headList!=null){
                    row=sheet.createRow((short)0);     //创建表头
                    HSSFCell cellHead=null;
                    for(int i=0,n=headList.size();i<n;i++){
                        cellHead=row.createCell((short)i);
//                        cellHead.setEncoding(HSSFCell.ENCODING_UTF_16);
                        cellHead.setCellValue((String)headList.get(i)); 
                    }
                }
                for(int i=0,n=sheetList.size();i<n;i++){
                    oneRowList=(List)sheetList.get(i);//获取一行数据
                    row = sheet.createRow((short)(i+1));   //创建一行表格
                    HSSFCell cell=null;
                    for(int j=0,m=oneRowList.size();j<m;j++){
                        cell   = row.createCell((short)j);
                        //设置中文编码
//                        cell.setEncoding(HSSFCell.ENCODING_UTF_16);
                        /*** update by xiongyh 将每个单元格的空格去掉 start ****/
        //              cell.setCellValue((String)oneRowList.get(j));
                        //cell.setCellValue(oneRowList.get(j)==null?"":((String)oneRowList.get(j)).trim());
                        /*** update by xiongyh 将每个单元格的空格去掉 end ****/
                        Pattern p =Pattern.compile("^(-?\\d+)(\\.\\d+)?$"); 
                        Matcher mm = p.matcher(oneRowList.get(j ).toString().trim());
                        boolean b = mm.matches();
                        if(!b){
                            cell.setCellValue(oneRowList.get(j ) == null ? ""
                                    : ((String) oneRowList.get(j )).trim());
                        }
                        else
                        {
                            StringBuffer sb = new StringBuffer();
                            sb.append( oneRowList.get(j ).toString().trim());
                            if(sb.charAt(0) == '0' && sb.indexOf(".")== -1 && !"0".equals(sb.toString())){
                                cell.setCellValue(oneRowList.get(j ) == null ? ""
                                        : ((String) oneRowList.get(j )).trim());
                            
                            }
                            else
                            {
                                StringBuffer sb1 = new StringBuffer();
                                sb1.append( oneRowList.get(j ).toString().trim());
                                if(sb1.indexOf(".") != -1){
                                    cell.setCellValue(Double.parseDouble(oneRowList.get(j ) == null ? ""
                                            : ((String) oneRowList.get(j )).trim()));
                                    cell.setCellStyle(style);
                                }
                                else{
                                    cell.setCellValue(Long.valueOf(oneRowList.get(j ) == null ? ""
                                            : ((String) oneRowList.get(j )).trim()));
                                }
                            }
                        }
                    }
                }
            }
        }
        return workBook;
    }
    
    /**
     * 
     * @param inputParam
     * @return
     */
    private static List divisionList(List inputParam,int listCount){
        List returnList = null;
        if(inputParam==null||inputParam.size()==0){
            return null;
        }

        int allNum = inputParam.size();
        returnList = new ArrayList();
        List temp = null;
        //计算分割后的个数
        int sheetNum = allNum/listCount;
        int leaveNum = allNum%listCount;
        if(leaveNum!=0){
            sheetNum++;
        }
                
        for(int i = 0;i<sheetNum;i++){
            //最后一页，取剩下的
            if(i==sheetNum-1){
                temp = inputParam.subList(i*listCount, allNum);
            }
            else{
                temp = inputParam.subList(i*listCount, (i+1)*listCount);
            }
            returnList.add(temp);
        }
        
        return returnList;
    }
    
//    /**
//     * 
//     * @param inputParam
//     * @return HSSFWorkbook
//     * @desc 根据一个双List数据结构生成一个表格
//     *       需要手动传入表头，否则生成的excel表格没有表头
//     * @version1.0
//     */
//    public  static HSSFWorkbook createExcelBookToString(List inputParam,List headList) throws IOException{
//        HSSFWorkbook workBook = new HSSFWorkbook();
//        List diviList = divisionList(inputParam,65000);
//        if(diviList!=null&&diviList.size()>0){
//            for(int sheetIndex = 0;sheetIndex<diviList.size();sheetIndex++){
//                List sheetList = (List)diviList.get(sheetIndex);
//                HSSFSheet sheet = workBook.createSheet("exportsheet");
//                int yenum = sheetIndex+1;
//                workBook.setSheetName(sheetIndex, "第"+yenum+"页", HSSFWorkbook.);
//                List oneRowList=null;
//                HSSFRow row=null;
//                //自定义一个样式
//                HSSFCellStyle style = workBook.createCellStyle();
//                style.setDataFormat(HSSFDataFormat.getBuiltinFormat("@")); 
//                if(headList!=null){
//                    row=sheet.createRow((short)0);     //创建表头
//                    HSSFCell cellHead=null;
//                    for(int i=0,n=headList.size();i<n;i++){
//                        cellHead=row.createCell((short)i);
//                        cellHead.setCellStyle(CellStyle.)
//                        cellHead.setEncoding(HSSFCell.ENCODING_UTF_16);
//                        cellHead.setCellValue((String)headList.get(i)); 
//                    }
//                }
//                for(int i=0,n=sheetList.size();i<n;i++){
//                    oneRowList=(List)sheetList.get(i);//获取一行数据
//                    row = sheet.createRow((short)(i+1));   //创建一行表格
//                    HSSFCell cell=null;
//                    for(int j=0,m=oneRowList.size();j<m;j++){
//                        cell   = row.createCell((short)j);
//                        //设置中文编码
//                        cell.s
//                        cell.setEncoding(HSSFCell.ENCODING_UTF_16);
//                        cell.setCellStyle(style);
//                        cell.setCellType(HSSFCell.CELL_TYPE_STRING); 
//                        if(oneRowList.get(j)!=null){
//                            cell.setCellValue(oneRowList.get(j).toString());
//                        }
//                        else{
//                            cell.setCellValue("");
//                        }
//                    }
//                }
//            }
//        }
//        return workBook;
//    }
//    
//    /**
//     * 
//     * @param inputParam
//     * @return HSSFWorkbook
//     * @desc 根据一个双List数据结构生成一个表格
//     *       需要手动传入表头，否则生成的excel表格没有表头
//     * @version1.0
//     */
//    public  static HSSFWorkbook createExcelBookToStringForReport(List inputParam,int firstRow) throws IOException,Exception{
//        File file =new File(ExcelUtil.class.getResource(CONFIG_LOACTION+"report34.xls").toURI());
//        System.out.println("--file path:"+ file.getAbsolutePath());
//        POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(file.getAbsolutePath()));
//        HSSFWorkbook workBook = new HSSFWorkbook(fs);
//        HSSFSheet sheet = workBook.getSheetAt(0);
//        workBook.setSheetName(0, ""+inputParam.size()+"家集团公司级管控客户收入统计表", HSSFWorkbook.ENCODING_UTF_16);
//        List oneRowList=null;
//        HSSFRow row=null;
////      //自定义一个样式
//        HSSFCellStyle style = workBook.createCellStyle();
//        style.setDataFormat(HSSFDataFormat.getBuiltinFormat("@")); 
//        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);//下边框     
//        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框     
//        style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框     
//        style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框    
//        for(int i=0,n=inputParam.size();i<n;i++){
//            oneRowList=(List)inputParam.get(i);//获取一行数据
//            row = sheet.createRow((short)(i+firstRow-1));   //创建一行表格
//            HSSFCell cell=null;
//            for(int j=0,m=oneRowList.size();j<m;j++){
//                cell   = row.createCell((short)j);
//                cell.setCellStyle(style);
//                if(j==1)
//                {
//                    //客户编码设为字符
//                    cell.setEncoding(HSSFCell.ENCODING_UTF_16);
//                    cell.setCellValue(oneRowList.get(j).toString());
//                    continue;
//                }
//                //设置中文编码
//                cell.setEncoding(HSSFCell.ENCODING_UTF_16);
//                /*** update by xiongyh 将每个单元格的空格去掉 start ****/
////              cell.setCellValue((String)oneRowList.get(j));
//                //cell.setCellValue(oneRowList.get(j)==null?"":((String)oneRowList.get(j)).trim());
//                /*** update by xiongyh 将每个单元格的空格去掉 end ****/
//                Pattern p =Pattern.compile("^(-?\\d+)(\\.\\d+)?$"); 
//                Matcher mm = p.matcher(oneRowList.get(j ).toString().trim());
//                boolean b = mm.matches();
//                if(!b){
//                    cell.setCellValue(oneRowList.get(j ) == null ? ""
//                            : ((String) oneRowList.get(j )).trim());
//                }
//                else
//                {
//                    StringBuffer sb = new StringBuffer();
//                    sb.append( oneRowList.get(j ).toString().trim());
//                    if(sb.charAt(0) == '0' && sb.indexOf(".")== -1 && !"0".equals(sb.toString())){
//                        cell.setCellValue(oneRowList.get(j ) == null ? ""
//                                : ((String) oneRowList.get(j )).trim());
//                    
//                    }
//                    else
//                    {
//                        StringBuffer sb1 = new StringBuffer();
//                        sb1.append( oneRowList.get(j ).toString().trim());
//                        if(sb1.indexOf(".") != -1){
//                            cell.setCellValue(Double.parseDouble(oneRowList.get(j ) == null ? ""
//                                    : ((String) oneRowList.get(j )).trim()));
//                            
//                        }
//                        else{
//                            cell.setCellValue(Long.valueOf(oneRowList.get(j ) == null ? ""
//                                    : ((String) oneRowList.get(j )).trim()));
//                        }
//                    }
//                }
//            }
//        }
//        return workBook;
//    }
    

//  
//    /**
//     * 
//     * 传入
//     * heads   String[] String[0]key,String[1]headName
//     * dataList 数据集,通过key取值 
//     * 返回
//     *  HSSWorkbook 对象
//     * 
//     */
//    public static HSSFWorkbook getHSSFWorkbook(List<Map<String,Object>> dataList, List<ExcelHeader> headers) throws IOException
//    {
//        HSSFWorkbook workBook = new HSSFWorkbook();
//        int sheetIndex = 0;
//        HSSFSheet sheet = workBook.createSheet("exportsheet");        
//        workBook.setSheetName(sheetIndex, "第" + (sheetIndex+1) + "页", HSSFWorkbook.ENCODING_UTF_16);    
//        HSSFRow row = null;
//        // 自定义一个样式
//        HSSFCellStyle style = workBook.createCellStyle();
//        style.setDataFormat(HSSFDataFormat.getBuiltinFormat("@"));
//        row = sheet.createRow((short) 0); // 创建表头
//        HSSFCell cell = null;
//        
//        //头
//        short k = 0;
//        for (ExcelHeader header : headers)
//        {
//            cell = row.createCell(k++);
//            cell.setEncoding(HSSFCell.ENCODING_UTF_16);
//            cell.setCellValue(header.getValue());
//        }
//        //体
//        for (short i = 0; i < dataList.size(); i++)
//        {
//            Map<String, Object> rowMap = dataList.get(i);// 获取一行数据
//            row = sheet.createRow(i + 1); // 创建一行表格
//            k = 0;
//            for (ExcelHeader header : headers)
//            {
//                cell = row.createCell(k++);
//                // 设置中文编码
//                cell.setEncoding(HSSFCell.ENCODING_UTF_16);
//                cell.setCellStyle(style);
//                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
//                Object colObj = rowMap.get(header.getKey());
//                if (colObj == null)
//                {
//                    cell.setCellValue("");
//                }
//                else if (colObj instanceof String)
//                {
//                    cell.setCellValue(colObj.toString());
//                }
//                else
//                {
//                    cell.setCellValue(colObj.toString());
//                }                
//            }
//        }
//        return workBook;
//    }
//    
//    /**
//     * 传入
//     * heads   String[] String[0]key,String[1]headName
//     * dataList 数据集,通过key取值 
//     * 返回
//     * HSSWorkBook数据流
//     */
//    public static InputStream getHSSFWorkbookInputStream(List<Map<String,Object>> dataList, List<ExcelHeader> heads) throws IOException
//    {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        getHSSFWorkbook(dataList,heads).write(baos);
//        baos.close();
//        return  new ByteArrayInputStream(baos.toByteArray());
//    }
//    

}
