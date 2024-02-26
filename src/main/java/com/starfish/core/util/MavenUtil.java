package com.starfish.core.util;

import cn.hutool.core.io.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.util.List;

/**
 * MavenUtil
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2024-02-26
 */
@Slf4j
public class MavenUtil {

    /**
     * 框架版本
     */
    public static final String VERSION = "3.2.2";

    /**
     * 新pom文件名称
     */
    public static final String NEW_FILE_NAME = "pom-backup.xml";

    /**
     * 代码目录
     */
    public static final String WORKSPACE = System.getProperty("user.dir");

    public static void main(String[] args) throws IOException, DocumentException {
        FileFilter fileFilter = file -> file.isFile() && "pom.xml".equalsIgnoreCase(file.getName());
        List<File> fileList = FileUtil.loopFiles(WORKSPACE, fileFilter);
        if (!CollectionUtils.isEmpty(fileList)) {
            for (File file : fileList) {
                process(file);
            }
        }
    }

    public static void process(File file) throws IOException, DocumentException {
        // 读取
        SAXReader reader = new SAXReader();
        Document document = reader.read(file);
//        Document document = reader.read(inputStream);

        // 如果parent是框架，修改其版本
        Element root = document.getRootElement();
        Element parentElement = root.element("parent");
        if (parentElement != null) {
            Element parentGroupIdElement = parentElement.element("groupId");
            Element parentArtifactIdElement = parentElement.element("artifactId");
            Element parentVersionElement = parentElement.element("version");

            if (parentGroupIdElement != null) {
                log.info("groupId:" + parentGroupIdElement.getText());
            }
            if (parentArtifactIdElement != null) {
                log.info("artifactId:" + parentArtifactIdElement.getText());
            }
            if (parentVersionElement != null) {
                log.info("version:" + parentVersionElement.getText());
            }

            if (parentGroupIdElement != null && "org.springframework.boot".equalsIgnoreCase(parentGroupIdElement.getText()) && parentArtifactIdElement != null && "spring-boot-starter-parent".equalsIgnoreCase(parentArtifactIdElement.getText()) && parentVersionElement != null) {
                parentVersionElement.setText(VERSION);
            }
        }

        // 如果依赖中有wings，修改其版本
        Element dependencies = root.element("dependencies");
        List<Element> dependenciesList = dependencies.elements();
        for (Element dependency : dependenciesList) {
            Element dependencyGroupIdElement = dependency.element("groupId");
            Element dependencyArtifactIdElement = dependency.element("artifactId");
            Element dependencyVersionElement = dependency.element("version");

            if (dependencyGroupIdElement != null && "cn.hutool".equalsIgnoreCase(dependencyGroupIdElement.getText()) && dependencyArtifactIdElement != null && "hutool-all".equalsIgnoreCase(dependencyArtifactIdElement.getText()) && dependencyVersionElement != null) {
                dependencyVersionElement.setText(VERSION);
            }
        }

        // 重新写入pom.xml文件
        String newFilePath = file.getParent() + File.separator + NEW_FILE_NAME;
        XMLWriter writer = new XMLWriter(new FileWriter(newFilePath));
        writer.write(document);
        writer.close();
    }

}
