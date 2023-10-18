package cn.lp.doTest;

import java.io.*;
import org.apache.poi.xwpf.usermodel.*;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JavaHTMLToWord {
    public static void main(String[] args) {
        try {
            // 读取HTML文件
            File input = new File("D:\\3.html");
            Document doc = Jsoup.parse(input, "UTF-8", "");
            System.out.println(doc.body());

            // 创建Word文档
            XWPFDocument document = new XWPFDocument();

            // 解析HTML代码
            Elements elements = doc.select("*");
            for (Element element : elements) {
                // 将HTML标签转换为Word标记
                String tag = element.tagName();
                if (tag.equals("p")) {
                    XWPFParagraph paragraph = document.createParagraph();
                    XWPFRun run = paragraph.createRun();
                    run.setText(element.text());

                } else if (tag.equals("h1")) {
                    XWPFParagraph paragraph = document.createParagraph();
                    XWPFRun run = paragraph.createRun();
                    run.setText(element.text());
                    paragraph.setStyle("Heading1");
                } else if (tag.equals("h2")) {
                    XWPFParagraph paragraph = document.createParagraph();
                    XWPFRun run = paragraph.createRun();
                    run.setText(element.text());
                    paragraph.setStyle("Heading2");
                }
            }

            // 将Word文档保存到本地
            FileOutputStream out = new FileOutputStream("output.docx");
            document.write(out);
            out.close();
            System.out.println("Word文档生成完成！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}