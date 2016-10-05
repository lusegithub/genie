package com.STM.util;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.STM.model.Wish;
import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.MimeUtility;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.view.document.AbstractExcelView;

public class ExcelWishReport extends AbstractExcelView{
	
	@Override
	protected void buildExcelDocument(Map model, HSSFWorkbook workbook,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

        Map<String,Object> wishData=(Map<String,Object>)model.get("wishdata");
		List<Wish> wishes = (List<Wish>) wishData.get("wishes");
		//create a wordsheet
		HSSFSheet sheet = workbook.createSheet("WishExcel");

		HSSFRow header = sheet.createRow(0);
		header.createCell(0).setCellValue("编号");
		header.createCell(1).setCellValue("昵称");
        header.createCell(2).setCellValue("学校");
        header.createCell(3).setCellValue("性别");
        header.createCell(4).setCellValue("心愿类型");
        header.createCell(5).setCellValue("心愿内容");
        header.createCell(6).setCellValue("图片地址");
        header.createCell(7).setCellValue("报答");
        header.createCell(8).setCellValue("许愿时间");
        header.createCell(9).setCellValue("联系类型");
        header.createCell(10).setCellValue("联系方式");
        HSSFCell cell;
        for (int i=0;i<wishes.size();i++){
            Wish wish=wishes.get(i);
            List<Object> list=new ArrayList<Object>();
            list.add(wish.getId()==null?"":wish.getId());
            list.add(wish.getName()==null?"":wish.getName());
            list.add(wish.getSchool()==null?"":wish.getSchool());
            list.add(wish.getGender()==null?"":wish.getGender());
            list.add(wish.getType()==null?"":wish.getType());
            list.add(wish.getContent()==null?"":wish.getContent());
            list.add(wish.getImageUrl()==null?"":wish.getImageUrl());
            list.add(wish.getAward()==null?"":wish.getAward());
            list.add(wish.getPublishTime()==null?"":wish.getPublishTime());
            list.add(wish.getContactType()==null?"":wish.getContactType());
            list.add(wish.getContact()==null?"":wish.getContact());
            for (int j=0;j<list.size();j++){
                cell = getCell(sheet, i+1, j);
                String result=String.valueOf(list.get(j));
                setText(cell,result);
            }
        }

        Date date=new Date();
        SimpleDateFormat dd = new SimpleDateFormat("yyyy/MM/dd");
        String time=dd.format(date);
        String filename = time+"心愿清单.xls";
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