package com.xmd.utils.poi;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Description 案例
 * @Author lixiao
 * @Date 2021/12/10 下午2:49
 */
public class ExcelAction {



    public static void main(String[] args) throws IOException {
        XSSFAction();
    }

    private static void XSSFAction() throws IOException {
        // 第一步，创建一个webbook，对应一个Excel文件
        XSSFWorkbook wb = new XSSFWorkbook();
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet,自定义sheetName
        XSSFSheet sheet = wb.createSheet();

        //设置字体
        Font font = ExcelUtils.setFont(wb, (short) 16, "宋体", false, HSSFColor.HSSFColorPredefined.BLACK.getIndex());

        CellStyle cellStyle = ExcelUtils.setCellStypeBorder(wb, BorderStyle.MEDIUM, IndexedColors.AQUA, FillPatternType.SOLID_FOREGROUND);
        cellStyle.setFont(font);

        Font contentFont = ExcelUtils.setFont(wb, (short) 12, "宋体", false, HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        CellStyle contentStyle = ExcelUtils.setCellStypeBorder(wb, BorderStyle.THIN, null, null);
        contentStyle.setFont(contentFont);

        int index = 2;
        for (int i = 0; i < 10000; i++) {
            XSSFRow r = sheet.createRow(index);
            ExcelUtils.setCellValue(r,0, "姓名"+ i,contentStyle);
            ExcelUtils.setCellValue(r,1, "评价"+ i,contentStyle);
            ExcelUtils.setCellValue(r,2, "分值"+ i,contentStyle);
            ExcelUtils.setCellValue(r,3, "亮点"+ i,contentStyle);
            ExcelUtils.setCellValue(r,4, "不足"+ i,contentStyle);
            ExcelUtils.setCellValue(r,5, "分数"+ i,contentStyle);
            index++;
        }


        XSSFRow row1 = sheet.createRow(1);
        ExcelUtils.setCellValue(row1,1, "评价",cellStyle);
        ExcelUtils.setCellValue(row1,2, "分值",cellStyle);

        ExcelUtils.setCellRangeAddress(0,1,0,0,sheet,BorderStyle.MEDIUM,cellStyle,"姓名");
        ExcelUtils.setCellRangeAddress(0,0,1,2,sheet,BorderStyle.MEDIUM,cellStyle,"标签");

        ExcelUtils.setCellRangeAddress(0,1,3,3,sheet,BorderStyle.MEDIUM,cellStyle,"亮点");
        ExcelUtils.setCellRangeAddress(0,1,4,4,sheet,BorderStyle.MEDIUM,cellStyle,"不足");
        ExcelUtils.setCellRangeAddress(0,1,5,5,sheet,BorderStyle.MEDIUM,cellStyle,"总分");


        FileOutputStream out = new FileOutputStream("/Users/three/Downloads/poi/xssf.xlsx");
        wb.write(out);
    }
}
