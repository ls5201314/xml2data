package com.emat.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.POIXMLDocumentPart;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPicture;
import org.apache.poi.hssf.usermodel.HSSFPictureData;
import org.apache.poi.hssf.usermodel.HSSFShape;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.PictureData;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFPicture;
import org.apache.poi.xssf.usermodel.XSSFShape;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxmlformats.schemas.drawingml.x2006.spreadsheetDrawing.CTMarker;

public class ExcelUtil {

	public static Map<String, String> exportExcelPicture(File file){
		Map<String, String> map = new HashMap<>();
		try {
			 // 创建流  
	        InputStream input = new FileInputStream(file);  
	          
	        // 获取文件后缀名  
	        String fileExt =  file.getName().substring(file.getName().lastIndexOf(".") + 1);  
	          
	        // 创建Workbook  
	        Workbook wb = null;  
	          
	        // 创建sheet  
	        Sheet sheet = null;  
	  
	        //根据后缀判断excel 2003 or 2007+  
	        if (fileExt.equals("xls")) {  
	            wb = (HSSFWorkbook) WorkbookFactory.create(input);  
	        } else {  
	            wb = new XSSFWorkbook(input);  
	        }  
	          
	        //获取excel sheet总数  
	        int sheetNumbers = wb.getNumberOfSheets();  
	          
	        // sheet list  
	        List<Map<String, PictureData>> sheetList = new ArrayList<Map<String, PictureData>>();  
	          
	        // 循环sheet  
	        for (int i = 0; i < sheetNumbers; i++) {  
	              
	            sheet = wb.getSheetAt(i);  
	            // map等待存储excel图片  
	            Map<String, PictureData> sheetIndexPicMap;   
	              
	            // 判断用07还是03的方法获取图片  
	            if (fileExt.equals("xls")) {  
	                sheetIndexPicMap = getSheetPictrues03(i, (HSSFSheet) sheet, (HSSFWorkbook) wb);  
	            } else {  
	                sheetIndexPicMap = getSheetPictrues07(i, (XSSFSheet) sheet, (XSSFWorkbook) wb);  
	            }  
	            // 将当前sheet图片map存入list  
	            sheetList.add(sheetIndexPicMap);  
	        }  
	        map = printImg(sheetList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
        return map; 
	}
	
	/** 
     * 获取Excel2003图片 
     * @param sheetNum 当前sheet编号 
     * @param sheet 当前sheet对象 
     * @param workbook 工作簿对象 
     * @return Map key:图片单元格索引（0_1_1）String，value:图片流PictureData 
     * @throws IOException 
     */  
    public static Map<String, PictureData> getSheetPictrues03(int sheetNum,  
            HSSFSheet sheet, HSSFWorkbook workbook) {  
  
        Map<String, PictureData> sheetIndexPicMap = new HashMap<String, PictureData>();  
        List<HSSFPictureData> pictures = workbook.getAllPictures();  
        if (pictures.size() != 0) {  
            for (HSSFShape shape : sheet.getDrawingPatriarch().getChildren()) {  
                HSSFClientAnchor anchor = (HSSFClientAnchor) shape.getAnchor();  
                if (shape instanceof HSSFPicture) {  
                    HSSFPicture pic = (HSSFPicture) shape;  
                    int pictureIndex = pic.getPictureIndex() - 1;  
                    HSSFPictureData picData = pictures.get(pictureIndex);  
                    String picIndex = String.valueOf(sheetNum) + "_"  
                            + String.valueOf(anchor.getRow1());  
                    sheetIndexPicMap.put(picIndex, picData);  
                }  
            }  
            return sheetIndexPicMap;  
        } else {  
            return null;  
        }  
    }  
  
    /** 
     * 获取Excel2007图片 
     * @param sheetNum 当前sheet编号 
     * @param sheet 当前sheet对象 
     * @param workbook 工作簿对象 
     * @return Map key:图片单元格索引（0_1_1）String，value:图片流PictureData 
     */  
    public static Map<String, PictureData> getSheetPictrues07(int sheetNum,  
            XSSFSheet sheet, XSSFWorkbook workbook) {  
        Map<String, PictureData> sheetIndexPicMap = new HashMap<String, PictureData>();  
  
        for (POIXMLDocumentPart dr : sheet.getRelations()) {  
            if (dr instanceof XSSFDrawing) {  
                XSSFDrawing drawing = (XSSFDrawing) dr;  
                List<XSSFShape> shapes = drawing.getShapes();  
                for (XSSFShape shape : shapes) {  
                    XSSFPicture pic = (XSSFPicture) shape;  
                    XSSFClientAnchor anchor = pic.getPreferredSize();  
                    CTMarker ctMarker = anchor.getFrom();  
                    String picIndex = String.valueOf(sheetNum) + "_"  
                            + ctMarker.getRow();  
                    sheetIndexPicMap.put(picIndex, pic.getPictureData());  
                }  
            }  
        }  
  
        return sheetIndexPicMap;  
    }  
      
    public static Map<String, String> printImg(List<Map<String, PictureData>> sheetList) throws IOException {  
    	String savePath = CommonUtil.IMAGE_SAVEPATH;
    	String basePath = "/image/";
        File file = new File(savePath + basePath);
    	if (!file.exists())
        {
       	 if(!file.canWrite())
       		 file.setWritable(true);
          file.mkdirs();
        }
    	Map<String, String> hasMap = new HashMap<>();
        for (Map<String, PictureData> map : sheetList) {  
            Object key[] = map.keySet().toArray();  
            for (int i = 0; i < map.size(); i++) {  
                // 获取图片流  
                PictureData pic = map.get(key[i]);  
                // 获取图片索引  
                String picIndex = key[i].toString();  
                // 获取图片格式  
                String ext = pic.suggestFileExtension();  
                  
                byte[] data = pic.getData();  
                
                String uuid = UUIDUtil.getUUID();
                String fileName = uuid + "." + ext; 
                String path = savePath + basePath + fileName;
                FileOutputStream out = new FileOutputStream(path);  
                out.write(data);  
                hasMap.put(picIndex, basePath + fileName);
                out.close();  
            }  
        }  
        
        return hasMap;
          
    }  
    
    /*
     * 解析excel中的数据
     *
     * file:excel文件
     */
    public static List<String> getExcelData(File file, Map<String, String> imageMap){
    	List<String> data = new ArrayList<>();
    	Workbook wbk = null;
    	try {
    		// 创建流  
	        InputStream input = new FileInputStream(file);  
	        
    		 // 获取文件后缀名  
	        String fileExt =  file.getName().substring(file.getName().lastIndexOf(".") + 1);  
	          
	        // 创建sheet  
	        Sheet sheet = null;  
	  
	        //根据后缀判断excel 2003 or 2007+  
	        if (fileExt.equals("xls")) {  
	        	wbk = (HSSFWorkbook) WorkbookFactory.create(input);  
	        } else {  
	        	wbk = new XSSFWorkbook(input);  
	        }  
	        
        	StringBuffer rowValue = new StringBuffer();
        	// 循环所有sheet
            for (int sheetNum = 0; sheetNum < wbk.getNumberOfSheets(); sheetNum++) {
                 sheet = wbk.getSheetAt(sheetNum);
                // 共有多少行
                int rowSize = sheet.getLastRowNum() + 1;
                int cellSize = sheet.getRow(0).getLastCellNum();// 行中有多少个单元格,也就是有多少列
                for(int i=1; i<rowSize; i++){
                	Row row = sheet.getRow(i);
                	if(!checkRowNotNull(row))continue;
                	rowValue = new StringBuffer();
                	String index = String.valueOf(sheetNum) + "_" + i; 
                	 for(int c=0; c<cellSize; c++){
                		String value = "";
                		if(c == 8){
                			value = imageMap.get(index);
                		}else{
                			value = getCellValue(row.getCell(c));
                		}
						rowValue.append(StringUtil.str2dbStr(value)).append(",");
					 }
                	 if(rowValue.toString().endsWith(","))rowValue = new StringBuffer(rowValue.substring(0, rowValue.length()-1));
                	 data.add(rowValue.toString());
                }
            }
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
        	try {
				wbk.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        return data;
    }

    // 判断行为空
 	private static boolean checkRowNotNull(Row row) {
 		if (row != null) {
 			Iterator<Cell> cellItr = row.iterator();
 			while (cellItr.hasNext()) {
 				Cell c = cellItr.next();
 				if (checkCellNotNull(c)) {
 					return true;
 				}
 			}
 		}
 		return false;
 	}
 	
    // 判断行为空
 	private static boolean checkCellNotNull(Cell cell) {
 		return !StringUtils.isEmpty(getCellValue(cell));
 	}
 	
    // 对Excel的各个单元格的格式进行判断并转换
 	@SuppressWarnings("deprecation")
	private static String getCellValue(Cell cell) {
 		String cellValue = "";
 		if (null != cell) {
 			cellValue = cell.toString();
 			 switch (cell.getCellType()) {
 			 case HSSFCell.CELL_TYPE_STRING:
 			 cellValue = cell.getRichStringCellValue().getString().trim();
 			 break;
 			 case HSSFCell.CELL_TYPE_NUMERIC:
 			 cellValue = String.valueOf(cell.getNumericCellValue()).trim();
 			 break;
 			 case HSSFCell.CELL_TYPE_BOOLEAN:
 			 cellValue = String.valueOf(cell.getBooleanCellValue()).trim();
 			 break;
 			 case HSSFCell.CELL_TYPE_FORMULA:
 			 cellValue = cell.getCellFormula();
 			 break;
 			 default:
 			 cellValue = "";
 			 }
 		}
 		return cellValue;
 	}
}
