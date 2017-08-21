package com.yhd.gps.busyservice.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

import com.yhd.gps.schedule.common.ScheduleConstants;

/**
 * Created by zhangshuqiang on 2017/5/17.
 */
public class ScheduleExcelUtil {

    private static final Log logger = LogFactory.getLog(ScheduleExcelUtil.class);

    /**
     * create cell
     * @param row
     * @param cellIndex
     * @param value
     */
    public static void createCell(HSSFRow row, int cellIndex, Object value) {
        if (null == value) {
            row.createCell(cellIndex, HSSFCell.CELL_TYPE_BLANK);
        } else if (value instanceof String) {
            row.createCell(cellIndex, HSSFCell.CELL_TYPE_STRING).setCellValue((String) value);
//        } else if (value instanceof Integer) {
//            row.createCell(cellIndex, HSSFCell.CELL_TYPE_NUMERIC).setCellValue((Integer) value);
        } else if (value instanceof Double) {
            row.createCell(cellIndex, HSSFCell.CELL_TYPE_NUMERIC).setCellValue((Double) value);
//        } else if (value instanceof Long) {
//            row.createCell(cellIndex, HSSFCell.CELL_TYPE_NUMERIC).setCellValue((Long) value);
        } else if (value instanceof BigDecimal) {
            row.createCell(cellIndex, HSSFCell.CELL_TYPE_NUMERIC).setCellValue(((BigDecimal) value).doubleValue());
        } else {
            row.createCell(cellIndex, HSSFCell.CELL_TYPE_STRING).setCellValue(value.toString());
        }
    }

    public static File exportXslFile(Workbook wb, String filePath) {
        FileOutputStream fileOut = null;
        File file = null;
        try {
            file = new File(filePath);
            fileOut = new FileOutputStream(file);
            wb.write(fileOut);
            fileOut.flush();
            fileOut.close();
        }catch (FileNotFoundException ex)
        {
            logger.error("FileNotFoundException:"+filePath);
        } catch (Exception e) {
            logger.error("文件导出-上传至服务器失败:", e);
        } finally {
            if (null != fileOut) {
                try {
                    fileOut.close();
                } catch (IOException e) {

                }
            }
            return file;
        }
    }

    public static HSSFSheet createCityPickSheet(HSSFWorkbook wb, int sheetPage)
    {
        HSSFSheet sheet = wb.createSheet("sheet_"+sheetPage);
        // 创建表头
        HSSFRow row = sheet.createRow(0);
        ScheduleExcelUtil.createCell(row, ScheduleConstants.SCHEDULE_CODE, "定价档期编码");
        ScheduleExcelUtil.createCell(row, ScheduleConstants.SCHEDULE_NAME, "定价档期名称");
        ScheduleExcelUtil.createCell(row, ScheduleConstants.SCHEDULE_TIME, "定价档期时间段");
        ScheduleExcelUtil.createCell(row, ScheduleConstants.MERCHANT, "城市精选商家");
        ScheduleExcelUtil.createCell(row, ScheduleConstants.PRODUCT_CODE, "产品编码");
        ScheduleExcelUtil.createCell(row, ScheduleConstants.PRODUCT_NAME, "产品名称");
        ScheduleExcelUtil.createCell(row, ScheduleConstants.PRODUCT_CATEGORY, "产品类别");
        ScheduleExcelUtil.createCell(row, ScheduleConstants.SUGGES_PRICE, "建议价");
        ScheduleExcelUtil.createCell(row, ScheduleConstants.ACTIVE_PRICE, "促销价");
        ScheduleExcelUtil.createCell(row, ScheduleConstants.CURRENT_PROM_ID, "当前生效促销Id");
        ScheduleExcelUtil.createCell(row, ScheduleConstants.PROM_NAME, "促销名称");

        return sheet;
    }
}
