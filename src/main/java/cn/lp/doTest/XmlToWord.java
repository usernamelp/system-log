package cn.lp.doTest;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.spire.doc.Document;
import com.spire.doc.FileFormat;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringEscapeUtils;

@Slf4j
public class XmlToWord {

    public static void main(String[] args) throws Exception {
        Document doc = new Document("D:\\tmp\\快调文书\\调解协议\\调解协议.doc");
        doc.saveToFile("D:\\tmp\\3.调解笔录2.doc",FileFormat.Docx);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        System.out.println(DateUtil.formatChineseDate(calendar, true));

/*        Document doc = new Document("D:\\tmp\\5.邮单2.docx");
        //Document doc = new Document();
        //doc.loadFromFile(filePath);
        //替换文档中的<> 此字符与freemarker语法冲突
        doc.replace("<", "&lt;", false, false);
        doc.replace(">", "&gt;", false, false);
        doc.replace("【案号】", "${ah!}", false, false);
        doc.replace("【手机】", "${bgsj!}", false, false);
        doc.replace("【所有被告】", "${bgmc!}", false, false);
        doc.replace("【户籍地址】", "${zsdz!}", false, false);

        doc.saveToFile("D:\\tmp\\1.邮单.docx", FileFormat.Docx);*/
/*
        readZipFile("D:\\tmp\\5.邮单2.docx");
        Map<String, Object> dataMap = new TreeMap<>();
        dataMap.put("ah", "ah1");
        dataMap.put("bgsj","17638263001");
        dataMap.put("bgmc", "张三");
        dataMap.put("zsdz","长沙");
//        xmlToWord("D:\\tmp\\","3","D:\\tmp\\","5",dataMap);
        xmlToWord("D:\\tmp\\5.邮单2.xml","D:\\tmp\\邮单des.docx",dataMap);*/
/*
//        File desFile = new File("D:\\tmp\\des.docx");

        Document document = new Document();
//        document.loadFromFile(desFile.getAbsolutePath(), FileFormat.Docx_2013);
        BufferedInputStream inputStream = FileUtil.getInputStream("D:\\tmp\\6.docx");
        BufferedInputStream inputStream2 = FileUtil.getInputStream("D:\\tmp\\7.docx");
        document.insertTextFromStream(inputStream, FileFormat.Docx_2013);
        document.insertTextFromStream(inputStream2, FileFormat.Docx_2013);
        document.saveToFile("D:\\tmp\\des.docx", FileFormat.Docx_2013);*/

    }


    public static void  xmlToWord(String xmlPath, String docxPath, Map<String,Object> dataMap){
        try {
            Configuration configuration = new Configuration();
            configuration.setDefaultEncoding("UTF-8");
            //.xml 模板文件所在目录
            configuration.setDirectoryForTemplateLoading(new File(FileUtil.getParent(xmlPath,1)));
            // 输出文档路径及名称 临时文件
            String tempXmlFilePath = StrUtil.removeSuffix(docxPath, ".docx") + "_temp.xml";
            File outTempFile = new File(tempXmlFilePath);
            if(!outTempFile.exists()){
                File dirFile =new File(outTempFile.getParent());
                if(!dirFile.exists()||!dirFile.isDirectory()){
                    dirFile.mkdirs();
                }
            }
            // 以utf-8的编码读取模板文件
            Template t =  configuration.getTemplate(FileUtil.getName(xmlPath),"UTF-8");
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outTempFile), "UTF-8"),1024);
            t.process(dataMap, out);
            out.close();

            File file = new File(tempXmlFilePath);
            String templateWordPath = StrUtil.removeSuffix(xmlPath, ".xml") + ".docx";

           /* FileInputStream templateDocIn = new FileInputStream(templateWordPath);
            File newTemplateFile = new File(templateWordPath);
            FileOutputStream newTemplateDcoOut = new FileOutputStream(newTemplateFile);
            byte[] templateBuffer = new byte[1024];
            int templateLen;
            while ((templateLen = templateDocIn.read(templateBuffer)) > 0) {
                newTemplateDcoOut.write(templateBuffer, 0, templateLen);
            }
            File docxFile = new File(templateWordPath);*/
            File docxFile = new File(templateWordPath);
            ZipFile zipFile = new ZipFile(docxFile);
            Enumeration<? extends ZipEntry> zipEntrys = zipFile.entries();
            ZipOutputStream zipout = new ZipOutputStream(new FileOutputStream(docxPath));
            int len = -1;
            byte[] buffer = new byte[1024];
            while (zipEntrys.hasMoreElements()) {
                ZipEntry next = zipEntrys.nextElement();
                InputStream is = zipFile.getInputStream(next);
                // 把输入流的文件传到输出流中 如果是word/document.xml由我们输入
                zipout.putNextEntry(new ZipEntry(next.toString()));
                if ("word/document.xml".equals(next.toString())) {
                    InputStream in = new FileInputStream(file);
                    while ((len = in.read(buffer)) != -1) {
                        zipout.write(buffer, 0, len);
                    }
                    in.close();
                } else {
                    while ((len = is.read(buffer)) != -1) {
                        zipout.write(buffer, 0, len);
                    }
                    is.close();
                }
            }
            zipout.close();

            //删除临时文件
            file.delete();
            System.out.println("生成成功");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("生成失败"+e.getMessage());
        }
    }
    /**
     * xml转word
     * @param templatePath 模板目录
     * @param templateName 模板名称，不带扩展名
     * @param targetPath 目标文件目录
     * @param targetName 目标文件名称，不带扩展名
     * @param dataMap 替换数据
     */
    public static void  xmlToWord(String templatePath, String templateName, String targetPath, String targetName, Map<String,Object> dataMap){
        try {
            Configuration configuration = new Configuration();
            configuration.setDefaultEncoding("UTF-8");
            //.xml 模板文件所在目录
            configuration.setDirectoryForTemplateLoading(new File(templatePath));
            // 输出文档路径及名称 临时文件
            String tempXmlFilePath = targetPath + "/" + targetName + "_temp.xml";
            File outTempFile = new File(tempXmlFilePath);
            if(!outTempFile.exists()){
                File dirFile =new File(outTempFile.getParent());
                if(!dirFile.exists()||!dirFile.isDirectory()){
                    dirFile.mkdirs();
                }
            }
            // 以utf-8的编码读取模板文件
            String xmlName = templateName + ".xml";
            Template t =  configuration.getTemplate(xmlName,"UTF-8");
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outTempFile), "UTF-8"),10240);
            t.process(dataMap, out);
            out.close();
 
            File file = new File(tempXmlFilePath);
            String templateWordPath = templatePath + "/" + templateName + ".docx";
            File docxFile = new File(templateWordPath);
            ZipFile zipFile = new ZipFile(docxFile);
            Enumeration<? extends ZipEntry> zipEntrys = zipFile.entries();
            ZipOutputStream zipout = new ZipOutputStream(new FileOutputStream(targetPath + "/" + targetName + ".docx"));
            int len = -1;
            byte[] buffer = new byte[1024];
            while (zipEntrys.hasMoreElements()) {
                ZipEntry next = zipEntrys.nextElement();
                InputStream is = zipFile.getInputStream(next);
                // 把输入流的文件传到输出流中 如果是word/document.xml由我们输入
                zipout.putNextEntry(new ZipEntry(next.toString()));
                if ("word/document.xml".equals(next.toString())) {
                    InputStream in = new FileInputStream(file);
                    while ((len = in.read(buffer)) != -1) {
                        zipout.write(buffer, 0, len);
                    }
                    in.close();
                } else {
                    while ((len = is.read(buffer)) != -1) {
                        zipout.write(buffer, 0, len);
                    }
                    is.close();
                }
            }
            zipout.close();
 
            //删除临时文件
            file.delete();
            System.out.println("生成成功");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("生成失败"+e.getMessage());
        }
    }

    /**
     * 同文件目录下生成xml模板文件
     *
     * @param filePath
     * @throws Exception
     */
    public static String readZipFile(String filePath) throws IOException {
        String xmlTemplatePath = filePath.substring(0, filePath.lastIndexOf(".")) + ".xml";
        ZipFile zf = null;
        ZipInputStream zis = null;
        try {
            //若是判决书则保存xml内容提取其它内容
            StringBuilder xmlSb = new StringBuilder();
            File file = new File(filePath);
            zf = new ZipFile(file, Charset.forName("utf8"));
            InputStream in = new BufferedInputStream(new FileInputStream(file));
            zis = new ZipInputStream(in);
            ZipEntry ze;
            while ((ze = zis.getNextEntry()) != null) {
                if (ze.isDirectory()) {
                } else {
                    if ("word/document.xml".equalsIgnoreCase(ze.getName())) {
                        File new_xml_file = new File(xmlTemplatePath);
                        BufferedReader br = new BufferedReader(new InputStreamReader(zf.getInputStream(ze), "UTF-8"));
                        FileOutputStream out = new FileOutputStream(new_xml_file);
                        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
                        String line;
                        while ((line = br.readLine()) != null) {
                            line = StringEscapeUtils.unescapeXml(line);
                            xmlSb.append(line);
                            bw.write(line);
                        }
                        br.close();
                        bw.close();
                        break;
                    }
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            zis.closeEntry();
            zis.close();
            zf.close();
        }
        return xmlTemplatePath;
    }
 
}