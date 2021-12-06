package com.xmd.utils.pdf;

import cn.hutool.core.collection.CollectionUtil;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.xmd.utils.TimeUtils;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author lixiao
 * @Date 2021/11/8 上午11:06
 */
public class PdfTableCellProcessor {

    /**------------------------全局属性start----------------------------*/
    // 定义全局的字体静态变量
    public static Font titlefont;
    public static Font headfont;
    public static Font keyfont;
    public static Font textfont;
    // 最大宽度
    public static int maxWidth = 520;
    // 静态代码块
    static {
        try {
            // 不同字体（这里定义为同一种字体：包含不同字号、不同style）
            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            titlefont = new Font(bfChinese, 16, Font.BOLD);
            headfont = new Font(bfChinese, 14, Font.BOLD);
            keyfont = new Font(bfChinese, 10, Font.BOLD);
            textfont = new Font(bfChinese, 10, Font.NORMAL);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**------------------------全局属性end----------------------------*/

    /**------------------------表格内容填充start----------------------------*/

    /**
     * 填充表格（表格为，标题、值相间）
     * 要求h3的数量和value的数量一致
     * @param table
     * @param h2 标题
     * @param h3 属性
     * @param value 值
     */
    public static void intervalValue(PdfPTable table, String h2, List<String> h3,List<String> value){
        if(StringUtils.isNotBlank(h2)){
            table.addCell(createCell(h2, headfont, Element.ALIGN_LEFT, table.getAbsoluteWidths().length, false));
        }
        if(CollectionUtil.isNotEmpty(h3)){
            for (int i = 0; i < h3.size(); i++) {
                table.addCell(createCell(h3.get(i), keyfont, Element.ALIGN_CENTER));
                table.addCell(createCell(value.get(i), textfont));
            }
        }
    }

    public static void intervalValue(PdfPTable table, String h2, List<String> h3, Map<String,Object> map, List<String> name){
        if(StringUtils.isNotBlank(h2)){
            table.addCell(createCell(h2, headfont, Element.ALIGN_LEFT, table.getAbsoluteWidths().length, false));
        }

        for (int i = 0; i < h3.size(); i++) {
            table.addCell(createCell(h3.get(i), keyfont, Element.ALIGN_CENTER));
            Object value = map.get(name.get(i));

            String str = "";
            if(value != null){
                if(value instanceof LocalDateTime){
                    str = TimeUtils.getTimeString((LocalDateTime) value,"yyyy.MM.dd");
                }else {
                    str = String.valueOf(value);
                }
            }
            table.addCell(createCell(str, textfont));
        }

    }



    /**
     * 创建表格标题
     * @param table
     * @param h2
     * @param h3
     */
    public static void title(PdfPTable table,String h2,List<String> h3){
        if(StringUtils.isNotBlank(h2)){
            table.addCell(createCell(h2, headfont, Element.ALIGN_LEFT, table.getAbsoluteWidths().length, false));
        }
        if(CollectionUtil.isNotEmpty(h3)){
            for (String str : h3) {
                table.addCell(createCell(str, keyfont, Element.ALIGN_CENTER));
            }
        }
    }

    /**
     * 合并单元格，并填充内容（文本或图片）
     * @param table
     * @param colspan
     * @param rowspan
     * @param value
     */
    public static void mergeCells(PdfPTable table,
                                  Integer colspan, Integer rowspan,
                                  Object value){

        PdfPCell cell = table.getDefaultCell();
        if(colspan != null){
            cell.setColspan(colspan);
        }
        if(rowspan != null){
            cell.setRowspan(rowspan);
        }

        cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM | Rectangle.TOP);

        if(value instanceof String){
            cell.setPhrase(new Phrase((String)value,textfont));
        }

        if(value instanceof Image){
            cell.addElement((Image)value);
        }

        table.addCell(cell);
    }


    /**------------------------表格内容填充end----------------------------*/


    /**------------------------创建表格单元格的方法start----------------------------*/

    /**
     * 创建单元格(指定字体)
     * @param value
     * @param font
     * @return
     */
    public static PdfPCell createCell(String value, Font font) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPhrase(new Phrase(value, font));
        return cell;
    }
    /**
     * 创建单元格（指定字体、水平..）
     * @param value
     * @param font
     * @param align
     * @return
     */
    public static PdfPCell createCell(String value, Font font, int align) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(align);
        cell.setPhrase(new Phrase(value, font));
        return cell;
    }
    /**
     * 创建单元格（指定字体、水平居..、单元格跨x列合并）
     * @param value
     * @param font
     * @param align
     * @param colspan
     * @return
     */
    public static PdfPCell createCell(String value, Font font, int align, int colspan) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(align);
        cell.setColspan(colspan);
        cell.setPhrase(new Phrase(value, font));
        return cell;
    }
    /**
     * 创建单元格（指定字体、水平居..、单元格跨x列合并、设置单元格内边距）
     * @param value
     * @param font
     * @param align
     * @param colspan
     * @param boderFlag
     * @return
     */
    public static PdfPCell createCell(String value, Font font, int align, int colspan, boolean boderFlag) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(align);
        cell.setColspan(colspan);
        cell.setPhrase(new Phrase(value, font));
        cell.setPadding(3.0f);
        if (!boderFlag) {
            cell.setBorder(0);
            cell.setPaddingTop(15.0f);
            cell.setPaddingBottom(8.0f);
        } else if (boderFlag) {
            cell.setBorder(0);
            cell.setPaddingTop(0.0f);
            cell.setPaddingBottom(15.0f);
        }
        return cell;
    }
    /**
     * 创建单元格（指定字体、水平..、边框宽度：0表示无边框、内边距）
     * @param value
     * @param font
     * @param align
     * @param borderWidth
     * @param paddingSize
     * @param flag
     * @return
     */
    public static PdfPCell createCell(String value, Font font, int align, float[] borderWidth, float[] paddingSize, boolean flag) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(align);
        cell.setPhrase(new Phrase(value, font));
        cell.setBorderWidthLeft(borderWidth[0]);
        cell.setBorderWidthRight(borderWidth[1]);
        cell.setBorderWidthTop(borderWidth[2]);
        cell.setBorderWidthBottom(borderWidth[3]);
        cell.setPaddingTop(paddingSize[0]);
        cell.setPaddingBottom(paddingSize[1]);
        if (flag) {
            cell.setColspan(2);
        }
        return cell;
    }
    /**------------------------创建表格单元格的方法end----------------------------*/



    /**--------------------------创建表格的方法start------------------- ---------*/
    /**
     * 创建默认列宽，指定列数、水平(居中、右、左)的表格
     * @param colNumber
     * @param align
     * @return
     */
    public static PdfPTable createTable(int colNumber, int align) {
        PdfPTable table = new PdfPTable(colNumber);
        try {
            table.setTotalWidth(maxWidth);
            table.setLockedWidth(true);
            table.setHorizontalAlignment(align);
            table.getDefaultCell().setBorder(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return table;
    }
    /**
     * 创建指定列宽、列数的表格
     * @param widths
     * @return
     */
    public static PdfPTable createTable(float[] widths) {
        PdfPTable table = new PdfPTable(widths);
        try {
            table.setTotalWidth(maxWidth);
            table.setLockedWidth(true);
            table.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.getDefaultCell().setBorder(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return table;
    }
    /**
     * 创建空白的表格
     * @return
     */
    public static PdfPTable createBlankTable() {
        PdfPTable table = new PdfPTable(1);
        table.getDefaultCell().setBorder(0);
        table.addCell(createCell("", keyfont));
        table.setSpacingAfter(20.0f);
        table.setSpacingBefore(20.0f);
        return table;
    }
}
