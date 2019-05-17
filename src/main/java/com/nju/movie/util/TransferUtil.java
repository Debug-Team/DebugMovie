package com.nju.movie.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.DocumentResult;
import org.dom4j.io.DocumentSource;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

public class TransferUtil {
    /**
     * @param source 需要转换的原xml文件
     * @param target 保存的目标文件位置
     * @param xslt   对应的xslt文件
     */
    public static void trans(String source, String target, String xslt) {
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(new FileInputStream(new File(source)));

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer(new StreamSource(new File(xslt)));

            DocumentSource ds = new DocumentSource(document);

            DocumentResult dr = new DocumentResult();

            transformer.transform(ds, dr);

            XMLWriter xmlWriter = new XMLWriter(new FileWriter(new File(target)));
            xmlWriter.write(dr.getDocument());
            xmlWriter.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String strs[] = {"douban", "maoyan", "time"};

        for (String s : strs){
            trans(s+"_movie.xml", s+"_after.xml", s+".xsl");
        }
    }
}
