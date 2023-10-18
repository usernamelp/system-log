package cn.lp.doTest;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.template.Template;
import cn.hutool.extra.template.TemplateConfig;
import cn.hutool.extra.template.TemplateEngine;
import cn.hutool.extra.template.TemplateUtil;
import cn.lp.entity.SysUserDTO;
import com.spire.doc.CssStyleSheetType;
import com.spire.doc.Document;
import com.spire.doc.FileFormat;
import com.spire.doc.Section;
import com.spire.doc.collections.SectionCollection;
import com.spire.doc.documents.StyleType;
import com.spire.doc.documents.XHTMLValidationType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.swing.text.html.HTML;
import java.awt.geom.Dimension2D;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

@Slf4j
public class HTMLtoWord {

    public static void casePxToCm(String px){
        BigDecimal divide = new BigDecimal(px).divide(new BigDecimal("28.35"), 2, RoundingMode.HALF_UP);
    }

   public static void main(String[] args) throws Exception {


       System.out.println(DateUtil.beginOfDay(new Date()).toString());
       System.out.println(DateUtil.endOfDay(new Date()));
       Document document = new Document();
       File file = new File("D:\\tmp\\5.邮单2.docx");
       document.loadFromFile(file.getAbsolutePath(), FileFormat.Xml);

       SectionCollection sections = document.getSections();
//       for (int i = 0; i < sections.getCount(); i++) {
//           Section section = sections.get(i);
//           section.getPageSetup()
//       }

       double width = document.getSections().get(0).getPageSetup().getPageSize().getWidth();
       double height = document.getSections().get(0).getPageSetup().getPageSize().getHeight();
       float left = document.getSections().get(0).getPageSetup().getMargins().getLeft();
       float right = document.getSections().get(0).getPageSetup().getMargins().getRight();
       float top = document.getSections().get(0).getPageSetup().getMargins().getTop();
       float bottom = document.getSections().get(0).getPageSetup().getMargins().getBottom();
       BigDecimal bigDecimal = new BigDecimal(width).divide(new BigDecimal("28.35"), 2, RoundingMode.HALF_UP);
       BigDecimal bigDecimal1 = new BigDecimal(height).divide(new BigDecimal("28.35"), 2, RoundingMode.HALF_UP);
       BigDecimal bigDecimal2 = new BigDecimal(left).divide(new BigDecimal("28.35"), 2, RoundingMode.HALF_UP);
       BigDecimal bigDecimal3 = new BigDecimal(right).divide(new BigDecimal("28.35"), 2, RoundingMode.HALF_UP);
       BigDecimal bigDecimal4 = new BigDecimal(top).divide(new BigDecimal("28.35"), 2, RoundingMode.HALF_UP);
       BigDecimal bigDecimal5 = new BigDecimal(bottom).divide(new BigDecimal("28.35"), 2, RoundingMode.HALF_UP);
       System.out.println(bigDecimal);
       System.out.println(bigDecimal1);
       System.out.println(bigDecimal2);
       System.out.println(bigDecimal3);
       System.out.println(bigDecimal4);
       System.out.println(bigDecimal5);
       System.out.println(new BigDecimal(width).divide(new BigDecimal("28.35"), 2, RoundingMode.HALF_UP));
       System.out.println(document.getSections().getCount());
       System.out.println(document.getSections().get(0).getPageSetup().getPageSize().getWidth());
       System.out.println(document.getSections().get(0).getPageSetup().getPageSize().getHeight());
       System.out.println(document.getSections().get(0).getPageSetup().getMargins().getLeft());
       System.out.println(document.getSections().get(0).getPageSetup().getMargins().getRight()/28.3476);
       System.out.println(document.getSections().get(0).getPageSetup().getMargins().getAll());
       System.out.println(document.getPageCount());
//       System.out.println(document.getText());

       //设置嵌入css样式
       document.getHtmlExportOptions().setCssStyleSheetType(CssStyleSheetType.Internal);

       document.saveToFile("D:\\tmp\\调解协议.html", FileFormat.Html);
       document.dispose();
/*
       List<Map<String, Object>> dataList = new ArrayList<>();
       SysUserDTO userDTO = new SysUserDTO(1, "张三", "124", 1, 1);
       SysUserDTO user = new SysUserDTO();
       initData(dataList,userDTO,"ah",userDTO.getUsername(),"s",user.getUsername());
       System.out.println(dataList);
       String first = FileUtil.readString("D:\\tmp\\1.xml", StandardCharsets.UTF_8);
       String second = FileUtil.readString("D:\\tmp\\2.xml", StandardCharsets.UTF_8);
       ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(first.getBytes("utf-8"));
       ByteArrayInputStream byteArrayInputStream2 = new ByteArrayInputStream(second.getBytes("utf-8"));
       List<ByteArrayInputStream> list = new LinkedList<>();
       list.add(byteArrayInputStream);
       list.add(byteArrayInputStream2);
       File desFile = new File("D:\\tmp\\调解协议2.xml");

       for (ByteArrayInputStream inputStream : list) {
           FileUtil.writeFromStream(inputStream,desFile,false);
       }
       String three = FileUtil.readString("D:\\tmp\\3.xml", StandardCharsets.UTF_8);
       Map<String, Object> dataMap = new TreeMap<>();
       dataMap.put("ah", "ah1");
       dataMap.put("bgsj","17638263001");
       dataMap.put("bgmc", "张三");
       dataMap.put("zsdz","长沙");
       Map<String, Object> dataMap2 = new TreeMap<>();
       dataMap.put("ah", "ah2");
       dataMap.put("bgsj","17638263002");
       dataMap.put("bgmc", "张三2");
       dataMap.put("zsdz","长沙2");
       ByteArrayInputStream documentInput = getFreemarkerContentInputStream(three, dataMap);
       ByteArrayInputStream documentInput2 = getFreemarkerContentInputStream(three, dataMap2);
       List<ByteArrayInputStream> streamList = new LinkedList<>();
       streamList.add(documentInput);
       streamList.add(documentInput2);

       File docxFile = new File("D:\\tmp\\3.docx");

       ZipFile zipFile = new ZipFile(docxFile);
       Enumeration<? extends ZipEntry> zipEntrys = zipFile.entries();


//       BufferedOutputStream finalOutputStream = FileUtil.getOutputStream("D:\\tmp\\4.docx");
       FileOutputStream finalOutputStream = new FileOutputStream("D:\\tmp\\4.docx");


       ZipOutputStream zipout = new ZipOutputStream(finalOutputStream);
       int len = -1;
       byte[] buffer = new byte[1024];
       while (zipEntrys.hasMoreElements()) {
           ZipEntry next = zipEntrys.nextElement();
           InputStream is = zipFile.getInputStream(next);
           if (next.toString().indexOf("media") < 0) {
               zipout.putNextEntry(new ZipEntry(next.getName()));
               // 如果是word/document.xml由我们输入
               if ("word/document.xml".equals(next.getName())) {
                   if (documentInput != null) {
                       if (documentInput != null) {
                           while ((len = documentInput.read(buffer)) != -1) {
                               zipout.write(buffer, 0, len);
                           }
                           documentInput.close();
                       }
                   }
               } else {
                   while ((len = is.read(buffer)) != -1) {
                       zipout.write(buffer, 0, len);
                   }
                   is.close();
               }
           }
       }*/

   }

  /* public static void xmlToWord(String xmlPath){
       InputStream templateDocIn = null;
       OutputStream newTemplateDcoOut = null;
       ZipOutputStream zipout = null;
       OutputStream finalOutputStream = null;
       File newTemplateFile = null;
       File docxFile = null;
       ZipFile zipFile = null;
       String newFilePath = "";
       String finalFilePath = "";
       try {
           String docxTemplateFilepath = StrUtil.removeSuffix(xmlPath, ".xml") + ".docx";

           templateDocIn = new FileInputStream(docxTemplateFilepath);
           //生成临时docx模板文件
           String newFileName = IdUtil.fastSimpleUUID() +  ".docx";
           newFilePath = PropertiesConstants.commonUploadPath + File.separator + newFileName;
           newTemplateFile = new File(newFilePath);
           newTemplateFile.setLastModified(System.currentTimeMillis());
           newTemplateDcoOut = new FileOutputStream(newTemplateFile);
           byte[] templateBuffer = new byte[1024];
           int templateLen;
           while ((templateLen = templateDocIn.read(templateBuffer)) > 0) {
               newTemplateDcoOut.write(templateBuffer, 0, templateLen);
           }
       } catch (Exception e) {
           e.printStackTrace();
       }
   }*/

    public static void initData(List<Map<String, Object>> dataList, SysUserDTO dsr, String ah, String... lxdz) {
        for (String dz : lxdz) {
            if (StringUtils.isNotEmpty(dz)) {
                Map<String, Object> dataMap = new TreeMap<>();
                dataMap.put("ah", ah);
                dataMap.put("bgsj", dsr.getUsername());
                dataMap.put("bgmc", dsr.getId());
                dataMap.put("zsdz", dz);
                dataList.add(dataMap);
            }
        }
    }

    public static ByteArrayInputStream getFreemarkerContentInputStream(String templateStr,Map<String, Object> dataMap) {
        ByteArrayInputStream in = null;
        try {
            TemplateEngine engine = TemplateUtil.createEngine(new TemplateConfig());

            Template template = engine.getTemplate(templateStr);

            // 当前日期放入变量
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            dataMap.put("nowUpper", DateUtil.formatChineseDate(calendar, true));

            String result = template.render(dataMap);
            // 这里一定要设置utf-8编码 否则导出的word中中文会是乱码
            in = new ByteArrayInputStream(result.getBytes("utf-8"));
        } catch (Exception e) {
            log.error("模板生成错误！", e);
        }
        return in;
    }


}