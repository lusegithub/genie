package com.STM.util;

import com.STM.model.Whisper;
import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.MimeUtility;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/6 0006.
 */
public class ExcelWhisperReport extends AbstractExcelView {
    @Override
    protected void buildExcelDocument(Map model, HSSFWorkbook workbook,
                                      HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String,Object> whisperData=(Map<String,Object>)model.get("whisperData");
        List<Whisper> whispers = (List<Whisper>) whisperData.get("whispers");
        //create a wordsheet
        HSSFSheet sheet = workbook.createSheet("WhisperExcel");

        HSSFRow header = sheet.createRow(0);
        header.createCell(0).setCellValue("id");
        header.createCell(1).setCellValue("内容");
        header.createCell(2).setCellValue("是否匿名(0表示不匿名)");
        header.createCell(3).setCellValue("联系方式类型");
        header.createCell(4).setCellValue("联系方式");
        header.createCell(5).setCellValue("发布时间");
        header.createCell(6).setCellValue("是否审核通过(1表示通过)");

        HSSFCell cell;
        for (int i=0;i<whispers.size();i++){
            Whisper whisper=whispers.get(i);
            List<Object> list=new ArrayList<Object>();
            list.add(whisper.getId()==null?"":whisper.getId());
            list.add(whisper.getContent()==null?"": StringEscapeUtils.unescapeHtml(whisper.getContent().replaceAll("&amp;","&")));
            list.add(whisper.getAnonymous()==null?"":whisper.getAnonymous());
            list.add(whisper.getContactType().equals("null")||whisper.getContactType()==null?"":whisper.getContactType());
            list.add(whisper.getContact()==null?"":whisper.getContact());
            list.add(whisper.getPublishTime()==null?"":whisper.getPublishTime());
            list.add(whisper.getValid()==null?"":whisper.getValid());

            for (int j=0;j<list.size();j++){
                cell = getCell(sheet, i+1, j);
                String result=String.valueOf(list.get(j));
                setText(cell,result);
            }
        }

        Date date=new Date();
        SimpleDateFormat dd = new SimpleDateFormat("yyyy/MM/dd");
        String time=dd.format(date);
        String filename = time+"悄悄话.xls";
        String newFileName = encodeFilename(filename, request);
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;filename=" + newFileName);
        OutputStream ouputStream = response.getOutputStream();
        workbook.write(ouputStream);
        ouputStream.flush();
        ouputStream.close();

    }
    public static String encodeFilename(String filename, HttpServletRequest request) {
        /**
         * 获取客户端浏览器和操作系统信息
         * 在IE浏览器中得到的是：User-Agent=Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; Maxthon; Alexa Toolbar)
         * 在Firefox中得到的是：User-Agent=Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.7.10) Gecko/20050717 Firefox/1.0.6
         */
        String agent = request.getHeader("USER-AGENT");
        try {
            if ((agent != null) && (-1 != agent.indexOf("MSIE"))) {
                String newFileName = URLEncoder.encode(filename, "UTF-8");
                newFileName = StringUtils.replace(newFileName, "+", "%20");
                if (newFileName.length() > 150) {
                    newFileName = new String(filename.getBytes("GB2312"), "ISO8859-1");
                    newFileName = StringUtils.replace(newFileName, " ", "%20");
                }
                return newFileName;
            }
            if ((agent != null) && (-1 != agent.indexOf("Mozilla")))
                return MimeUtility.encodeText(filename, "UTF-8", "B");

            return filename;
        } catch (Exception ex) {
            return filename;
        }
    }
}
