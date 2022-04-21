package com.xmd.utils.poi;

import cn.hutool.core.collection.CollectionUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlToken;
import org.openxmlformats.schemas.drawingml.x2006.main.CTNonVisualDrawingProps;
import org.openxmlformats.schemas.drawingml.x2006.main.CTPositiveSize2D;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.CTInline;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WordUtils {


    /**
     * 释放段落属性校验
     * @param targetParagraph
     * @return
     */
    public static CTPPr getUnLockCTPPR(XWPFParagraph targetParagraph) {
        CTPPr oldPPr = targetParagraph.getCTP().getPPr();//获取段落对象的样式的ppr标签

        if (oldPPr!=null) {
            //获取ppr标签里的rpr标签
            CTParaRPr oldPPrRpr = oldPPr.getRPr();
            //获取pprchange标签
//            CTPPrChange oldPPrChange = oldPPr.getPPrChange();

            CTTrackChange rprIns = null;
            CTParaRPrChange oldPraRprChange = null;

            if (oldPPrRpr!=null) {
                //获取ins标签
                rprIns = oldPPrRpr.getIns();
                //获取ppr标签中的rpr标签里的rprchange标签
                oldPraRprChange = oldPPrRpr.getRPrChange();
            }

            //如果有change标签证明是修订内容，设置为取消修订
//            if (oldPPrChange!=null) {
//                oldPPr.unsetPPrChange();
//            }

            if (oldPraRprChange!=null) {
                oldPPrRpr.unsetRPrChange();
            }
            //ins标签代表着修订线，如果有修订线则取消修订线
            if (rprIns!=null) {
                oldPPrRpr.unsetIns();
            }
        }
        return oldPPr;
    }


    /**
     * 设置行距
     * @param size
     * @param paragraph
     */
    public static void setLineSpacing(int size,XWPFParagraph paragraph){
        CTP ctp = paragraph.getCTP();
        CTPPr ppr = ctp.isSetPPr() ? ctp.getPPr() : ctp.addNewPPr();
        CTSpacing spacing = ppr.isSetSpacing()? ppr.getSpacing() : ppr.addNewSpacing();
        spacing.setAfter(BigInteger.valueOf(0));
        spacing.setBefore(BigInteger.valueOf(0));
        //设置行距类型为 EXACT
        spacing.setLineRule(STLineSpacingRule.EXACT);
        //1磅数是20
        spacing.setLine(BigInteger.valueOf(size * 20));
    }

    /**
     * 获取最后个段落的光标
     * @param doc
     * @return
     */
    public static XmlCursor getBottomCursor(XWPFDocument doc){
        List<XWPFParagraph> paragraphList = doc.getParagraphs();
        return paragraphList.get(paragraphList.size()-1).getCTP().newCursor();
    }

    /**
     * 替换表格对象方法
     *
     * @param document  docx解析对象
     * @param textMap   需要替换的信息集合
     */
    public static void changeTableText(XWPFDocument document, Map<String, Object> textMap) throws IOException, InvalidFormatException {
        //获取表格对象集合
        List<XWPFTable> tables = document.getTables();
        for (int i = 0; i < tables.size(); i++) {
            //只处理行数大于等于2的表格
            XWPFTable table = tables.get(i);
            if (table.getRows().size() > 1) {
                //判断表格是需要替换还是需要插入，判断逻辑有$为替换，表格无$为插入
                if (checkText(table.getText())) {
                    List<XWPFTableRow> rows = table.getRows();
                    //遍历表格,并替换模板
                    eachTable(rows, textMap,document);
                }
            }
        }
    }


    /**
     * 判断文本中时候包含 '${'
     *
     * @param text 文本
     * @return 包含返回true, 不包含返回false
     */
    public static boolean checkText(String text) {
        boolean check = false;
        if (text.indexOf("${") != -1) {
            check = true;
        }
        return check;
    }

    /**
     * 遍历表格,并替换模板
     *
     * @param rows    表格行对象
     * @param textMap 需要替换的信息集合
     */
    public static void eachTable(List<XWPFTableRow> rows, Map<String, Object> textMap,XWPFDocument document) throws IOException, InvalidFormatException {
        for (XWPFTableRow row : rows) {
            List<XWPFTableCell> cells = row.getTableCells();
            for (XWPFTableCell cell : cells) {
                //判断单元格是否需要替换
                if (checkText(cell.getText())) {
                    List<XWPFParagraph> paragraphs = cell.getParagraphs();
                    for (XWPFParagraph paragraph : paragraphs) {
                        List<XWPFRun> runs = paragraph.getRuns();
                        for (XWPFRun run : runs) {

                            String value = changeValue(run.toString(), textMap);
                            if(StringUtils.isNotBlank(value)){
                                String[] text = value.split("\n");
                                for (int i = 0; i < text.length; i++) {
                                    if (i == 0) {
                                        run.setText(text[i].trim(),0);
                                    } else {
                                        //硬回车
//                                            run.addCarriageReturn();
                                        // 换行
                                        run.addBreak();
                                        run.setText(text[i].trim());
                                    }

                                }
                            }else {
                                run.setText(changeValue(run.toString(), textMap), 0);
                            }
                        }
                    }
                }
            }
        }
    }


    /**
     * 根据图片类型，取得对应的图片类型代码
     * @param imgFile 文件名，带文件类型
     * @return int
     */
    public static int getPictureType(String imgFile){
        // 设置图片默认类型
        int format = XWPFDocument.PICTURE_TYPE_JPEG;
        // 判断图片类型
        if (imgFile.endsWith(".emf")) {
            format = XWPFDocument.PICTURE_TYPE_EMF;
        } else if (imgFile.endsWith(".wmf")) {
            format = XWPFDocument.PICTURE_TYPE_WMF;
        } else if (imgFile.endsWith(".pict")) {
            format = XWPFDocument.PICTURE_TYPE_PICT;
        } else if (imgFile.endsWith(".jpeg") || imgFile.endsWith(".jpg")) {
            format = XWPFDocument.PICTURE_TYPE_JPEG;
        } else if (imgFile.endsWith(".png")) {
            format = XWPFDocument.PICTURE_TYPE_PNG;
        } else if (imgFile.endsWith(".dib")) {
            format = XWPFDocument.PICTURE_TYPE_DIB;
        } else if (imgFile.endsWith(".gif")) {
            format = XWPFDocument.PICTURE_TYPE_GIF;
        } else if (imgFile.endsWith(".tiff")) {
            format = XWPFDocument.PICTURE_TYPE_TIFF;
        } else if (imgFile.endsWith(".eps")) {
            format = XWPFDocument.PICTURE_TYPE_EPS;
        } else if (imgFile.endsWith(".bmp")) {
            format = XWPFDocument.PICTURE_TYPE_BMP;
        } else if (imgFile.endsWith(".wpg")) {
            format = XWPFDocument.PICTURE_TYPE_WPG;
        }
        return format;
    }


    /**因POI 3.8自带的BUG 导致添加进的图片不显示，只有一个图片框，将图片另存为发现里面的图片是一个PNG格式的透明图片
     * 这里自定义添加图片的方法
     * 往Run中插入图片(解决在word中不显示的问题)
     * @param run
     * @param blipId    图片的id
     * @param id	    图片的类型
     * @param width     图片的宽
     * @param height    图片的高
     * @author lgj
     */
    public static void addPicture(XWPFRun run, String blipId, int id, int width, int height){
        final int EMU = 9525;
        width *= EMU;
        height *= EMU;

        CTInline inline =run.getCTR().addNewDrawing().addNewInline();

        String picXml = "" +
                "<a:graphic xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">" +
                "   <a:graphicData uri=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">" +
                "      <pic:pic xmlns:pic=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">" +
                "         <pic:nvPicPr>" +
                "            <pic:cNvPr id=\"" + id + "\" name=\"Generated\"/>" +
                "            <pic:cNvPicPr/>" +
                "         </pic:nvPicPr>" +
                "         <pic:blipFill>" +
                "            <a:blip r:embed=\"" + blipId + "\" xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\"/>" +
                "            <a:stretch>" +
                "               <a:fillRect/>" +
                "            </a:stretch>" +
                "         </pic:blipFill>" +
                "         <pic:spPr>" +
                "            <a:xfrm>" +
                "               <a:off x=\"0\" y=\"0\"/>" +
                "               <a:ext cx=\"" + width + "\" cy=\"" + height + "\"/>" +
                "            </a:xfrm>" +
                "            <a:prstGeom prst=\"rect\">" +
                "               <a:avLst/>" +
                "            </a:prstGeom>" +
                "         </pic:spPr>" +
                "      </pic:pic>" +
                "   </a:graphicData>" +
                "</a:graphic>";

        //CTGraphicalObjectData graphicData = inline.addNewGraphic().addNewGraphicData();
        XmlToken xmlToken = null;
        try {
            xmlToken = XmlToken.Factory.parse(picXml);
        } catch(XmlException xe) {
            xe.printStackTrace();
        }
        inline.set(xmlToken);
        //graphicData.set(xmlToken);

        inline.setDistT(0);
        inline.setDistB(0);
        inline.setDistL(0);
        inline.setDistR(0);

        CTPositiveSize2D extent = inline.addNewExtent();
        extent.setCx(width);
        extent.setCy(height);

        CTNonVisualDrawingProps docPr = inline.addNewDocPr();
        docPr.setId(id);
        docPr.setName("Picture " + id);
        docPr.setDescr("Generated");
    }

    /**
     * 匹配传入信息集合与模板
     *
     * @param value   模板需要替换的区域
     * @param textMap 传入信息集合
     * @return 模板需要替换区域信息集合对应值
     */
    public static String changeValue(String value, Map<String, Object> textMap) {
        Set<Map.Entry<String, Object>> textSets = textMap.entrySet();
        for (Map.Entry<String, Object> textSet : textSets) {
            //匹配模板与替换值 格式${key}
            String key = "${" + textSet.getKey() + "}";
            if (value.indexOf(key) != -1) {
                value = (String)textSet.getValue();
            }
        }
        //模板未匹配到区域替换为空
        if (checkText(value)) {
            value = "";
        }
        return value;
    }

    public static void mergeCellsVertically(XWPFTable table, int col, int fromRow, int toRow) {
        for (int rowIndex = fromRow; rowIndex <= toRow; rowIndex++) {
            XWPFTableCell cell = table.getRow(rowIndex).getCell(col);
            if ( rowIndex == fromRow ) {
                // The first merged cell is set with RESTART merge value
                cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.RESTART);
            } else {
                // Cells which join (merge) the first one, are set with CONTINUE
                cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.CONTINUE);
            }
        }
    }

    /**
     * word 复制表中的行
     * @param targetRow
     * @param sourceRow
     */
    public static void createCellsAndCopyStyles(XWPFTableRow targetRow, XWPFTableRow sourceRow) {
        targetRow.getCtRow().setTrPr(sourceRow.getCtRow().getTrPr());

        List<XWPFTableCell> tableCells = sourceRow.getTableCells();

        if (CollectionUtil.isEmpty(tableCells)) {
            return;

        }

        for (XWPFTableCell sourceCell : tableCells) {
            XWPFTableCell newCell = targetRow.addNewTableCell();

            newCell.getCTTc().setTcPr(sourceCell.getCTTc().getTcPr());

            List sourceParagraphs = sourceCell.getParagraphs();

            if (CollectionUtil.isEmpty(sourceParagraphs)) {
                continue;

            }

            XWPFParagraph sourceParagraph = (XWPFParagraph) sourceParagraphs.get(0);

            List targetParagraphs = newCell.getParagraphs();

            if (CollectionUtil.isEmpty(targetParagraphs)) {
                XWPFParagraph p = newCell.addParagraph();

                p.getCTP().setPPr(sourceParagraph.getCTP().getPPr());

                XWPFRun run = p.getRuns().isEmpty() ? p.createRun() : p.getRuns().get(0);

                run.setFontFamily(sourceParagraph.getRuns().get(0).getFontFamily());

            } else {
                XWPFParagraph p = (XWPFParagraph) targetParagraphs.get(0);

                p.getCTP().setPPr(sourceParagraph.getCTP().getPPr());

                XWPFRun run = p.getRuns().isEmpty() ? p.createRun() : p.getRuns().get(0);
                if (sourceParagraph.getRuns().size() > 0) {
                    run.setFontFamily(sourceParagraph.getRuns().get(0).getFontFamily());
                }
            }
        }
    }
}
