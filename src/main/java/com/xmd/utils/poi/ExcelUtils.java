package com.xmd.utils.poi;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.*;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Description
 * @Author lixiao
 * @Date 2021/12/10 上午9:38
 */
public class ExcelUtils {

    /**
     * 判断文件类型
     * @param is
     * @param fileName
     * @return
     * @throws IOException
     */
    public static Workbook getWorkBook(InputStream is, String fileName) throws IOException {

        Workbook workbook = null;
        if (fileName.endsWith("xls")) {
            //2003
            workbook = new HSSFWorkbook(is);
        } else if (fileName.endsWith("xlsx")) {
            //2007
            workbook = new XSSFWorkbook(is);
        }
        return workbook;
    }

    /**
     * 判断单位格是否是合并
     *
     * @param sheet
     * @param cell
     * @return
     */
    public static boolean isMergedRegion(Sheet sheet, Cell cell) {
        int row = cell.getRowIndex();
        int column = cell.getColumnIndex();
        return isMergedRegion(sheet, row, column);
    }

    /**
     * 判断指定的单元格是否是合并单元格
     *
     * @param sheet  工作表
     * @param row    行下标
     * @param column 列下标
     * @return
     */
    public static boolean isMergedRegion(Sheet sheet, int row, int column) {
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            int firstColumn = range.getFirstColumn();
            int lastColumn = range.getLastColumn();
            int firstRow = range.getFirstRow();
            int lastRow = range.getLastRow();
            if (row >= firstRow && row <= lastRow) {
                if (column >= firstColumn && column <= lastColumn) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 合并单元格中的值
     * @param sheet
     * @param cell
     * @return
     */
    public static String getMergedRegionValue(Sheet sheet, Cell cell) {
        return getMergedRegionValue(sheet, cell.getRowIndex(), cell.getColumnIndex());
    }

    /**
     * 合并单元格中的值
     * @param sheet
     * @param row
     * @param column
     * @return
     */
    public static String getMergedRegionValue(Sheet sheet, int row, int column) {
        int sheetMergeCount = sheet.getNumMergedRegions();

        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress ca = sheet.getMergedRegion(i);
            int firstColumn = ca.getFirstColumn();
            int lastColumn = ca.getLastColumn();
            int firstRow = ca.getFirstRow();
            int lastRow = ca.getLastRow();

            if (row >= firstRow && row <= lastRow) {

                if (column >= firstColumn && column <= lastColumn) {
                    Row fRow = sheet.getRow(firstRow);
                    Cell fCell = fRow.getCell(firstColumn);
                    return getCellValue(fCell);
                }
            }
        }

        return null;
    }

    public static String getCellValue(Cell cell) {

        if (cell == null)
            return "";

        if (cell.getCellType() == CellType.STRING) {

            return cell.getStringCellValue();

        } else if (cell.getCellType() == CellType.BOOLEAN) {

            return String.valueOf(cell.getBooleanCellValue());

        } else if (cell.getCellType() == CellType.FORMULA) {

            return cell.getCellFormula();

        } else if (cell.getCellType() == CellType.NUMERIC) {
//            DecimalFormat df = new DecimalFormat("#");
//            return String.valueOf(df.format(cell.getNumericCellValue()));
            return String.valueOf(cell.getNumericCellValue());
        }
        return "";
    }

    /**
     * 设置字体
     * @param wb
     * @param size 字体大小
     * @param name 字体类型
     * @param bold 是否加粗
     * @param color 颜色
     * @return
     */
    public static Font setFont(Workbook wb,
                                   short size,
                                   String name,
                                   boolean bold,
                                   Short color){
        Font font = wb.createFont();

        //设置删除线   strikeout（删除线）
//        font.setStrikeout(true);
        //设置是否斜体
//        font.setItalic(true);
        //设置字体颜色
        if(color != null){
            font.setColor(color);
        }

        //字体大小
        font.setFontHeightInPoints(size);
        // 设置字体
        font.setFontName(name);
        // 设置字体粗体
        font.setBold(bold);
        return font;
    }

    /**
     * 设置单元格样式（边框）
     * @param wb
     * @param borderStyle 边框样式
     * @param colors
     * @param fillPatternType
     * @return
     */
    public static CellStyle setCellStyleBorder(XSSFWorkbook wb,
                                               BorderStyle borderStyle,
                                               IndexedColors colors,
                                               FillPatternType fillPatternType){
        CellStyle cellStyle = wb.createCellStyle();

        // 设置上下左右边框
        if(borderStyle != null){
            cellStyle.setBorderBottom(borderStyle);
            cellStyle.setBorderLeft(borderStyle);
            cellStyle.setBorderRight(borderStyle);
            cellStyle.setBorderTop(borderStyle);
        }

        //设置背景颜色
        if(colors != null && fillPatternType != null){
            cellStyle.setFillForegroundColor(colors.getIndex());
            cellStyle.setFillPattern(fillPatternType);
        }

        //设置左对齐
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        return cellStyle;
    }

    /**
     * 创建单元格，设置样式，设置值
     * @param row
     * @param colNum
     * @param value
     * @param style
     */
    public static void setCellValue(Row row,int colNum, String value, CellStyle style){
        Cell cell = row.createCell(colNum);
        cell.setCellStyle(style);
        //设置单元格为字符串类型
        cell.setCellType(CellType.STRING);
        //给每个单元格设置值
        if(StringUtils.isNoneBlank(value)){
            cell.setCellValue(value);
        }else {
            cell.setCellValue("");
        }

    }

    /**
     * 合并单元格设置值、设置样式
     * @param firstRow
     * @param lastRow
     * @param firstCol
     * @param lastCol
     * @param sheet
     * @param borderStyle 单元格边框
     * @param cellStyle 单元格样式
     * @param value 值
     */
    public static Row setCellRangeAddress(int firstRow, int lastRow, int firstCol, int lastCol,
                                               Sheet sheet,BorderStyle borderStyle,
                                               CellStyle cellStyle,String value){

        CellRangeAddress cra = new CellRangeAddress(firstRow, lastRow, firstCol, lastCol);

        //添加合并单元格属性
        sheet.addMergedRegion(cra);


        Row row = sheet.getRow(firstRow);
        if(row == null){
            row = sheet.createRow(firstRow);
        }

        Cell cell = row.getCell(firstCol);
        if(row.getCell(firstCol) == null){
            cell = row.createCell(firstCol);
        }

        //设置样式
        cell.setCellStyle(cellStyle);
        //设置值
        cell.setCellValue(value);

        //合并单元格样式在单元格样式之后

        // 下边框
        RegionUtil.setBorderBottom(borderStyle, cra, sheet);
        // 左边框
        RegionUtil.setBorderLeft(borderStyle, cra, sheet);
        // 有边框
        RegionUtil.setBorderRight(borderStyle, cra, sheet);
        // 上边框
        RegionUtil.setBorderTop(borderStyle, cra, sheet);

        return row;
    }





}
