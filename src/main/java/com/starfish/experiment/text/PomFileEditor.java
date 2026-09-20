package com.starfish.experiment.text;

import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class PomFileEditor {

    public static void replace(String filePath, String oldGroupId, String oldArtifactId, String newGroupId, String newArtifactId, String oldVersion, String newVersion) {
        replaceDependency(filePath, oldGroupId, oldArtifactId, newGroupId, newArtifactId, oldVersion, newVersion);
    }

    public static void replace(String filePath, String element, String text) {
        replaceProperties(filePath, element, text);
    }

    /**
     * 替换 pom.xml 文件中匹配的 dependency 元素。
     * <p>
     * 遍历 filePath，如果是目录则递归查找所有 pom.xml 文件；
     * 如果是文件则直接处理。在 pom.xml 中查找 groupId 和 artifactId
     * 同时匹配旧值的 {@code <dependency>} 元素，替换为新的 groupId、artifactId 和 version。
     * 当 oldVersion 为空时，直接替换 version 值；当 oldVersion 不为空时，
     * 需当前 dependency 的 version 与 oldVersion 一致才进行替换。
     * </p>
     *
     * @param filePath      pom.xml 文件或目录路径
     * @param oldGroupId    要替换的原 groupId
     * @param oldArtifactId 要替换的原 artifactId
     * @param newGroupId    替换后的新 groupId
     * @param newArtifactId 替换后的新 artifactId
     * @param oldVersion    要匹配的原 version，为空则不匹配版本直接替换
     * @param newVersion    替换后的新 version
     */
    public static void replaceDependency(String filePath, String oldGroupId, String oldArtifactId, String newGroupId, String newArtifactId, String oldVersion, String newVersion) {
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            System.err.println("Path does not exist: " + filePath);
            return;
        }

        try {
            if (Files.isDirectory(path)) {
                try (Stream<Path> walk = Files.walk(path)) {
                    walk.filter(Files::isRegularFile).filter(p -> "pom.xml".equalsIgnoreCase(p.getFileName().toString())).forEach(p -> {
                        try {
                            replaceDependencyInPomFile(p, oldGroupId, oldArtifactId, newGroupId, newArtifactId, oldVersion, newVersion);
                        } catch (Exception e) {
                            System.err.println("Error processing file: " + p + " - " + e.getMessage());
                        }
                    });
                }
            } else if (Files.isRegularFile(path)) {
                if ("pom.xml".equalsIgnoreCase(path.getFileName().toString())) {
                    replaceDependencyInPomFile(path, oldGroupId, oldArtifactId, newGroupId, newArtifactId, oldVersion, newVersion);
                } else {
                    System.err.println("File is not pom.xml: " + filePath);
                }
            }
        } catch (Exception e) {
            System.err.println("Error processing path: " + filePath + " - " + e.getMessage());
        }
    }

    /**
     * 在单个 pom.xml 文件中查找并替换匹配的 dependency 元素。
     *
     * @param file          pom.xml 文件路径
     * @param oldGroupId    要匹配的原 groupId
     * @param oldArtifactId 要匹配的原 artifactId
     * @param newGroupId    替换后的新 groupId
     * @param newArtifactId 替换后的新 artifactId
     * @param oldVersion    要匹配的原 version，为空则不匹配版本直接替换
     * @param newVersion    替换后的新 version
     * @throws Exception 解析或写入 XML 时发生的异常
     */
    private static void replaceDependencyInPomFile(Path file, String oldGroupId, String oldArtifactId, String newGroupId, String newArtifactId, String oldVersion, String newVersion) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(false);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(file.toFile());

        Element root = doc.getDocumentElement();
        NodeList dependencyNodes = root.getElementsByTagName("dependency");

        int updatedCount = 0;
        for (int i = 0; i < dependencyNodes.getLength(); i++) {
            Element dependency = (Element) dependencyNodes.item(i);

            Element groupIdElement = findChildElement(dependency, "groupId");
            Element artifactIdElement = findChildElement(dependency, "artifactId");

            if (groupIdElement == null || artifactIdElement == null) {
                continue;
            }

            String currentGroupId = groupIdElement.getTextContent().trim();
            String currentArtifactId = artifactIdElement.getTextContent().trim();

            if (!oldGroupId.equals(currentGroupId) || !oldArtifactId.equals(currentArtifactId)) {
                continue;
            }

            Element versionElement = findChildElement(dependency, "version");

            if (oldVersion != null && !oldVersion.isEmpty()) {
                if (versionElement == null) {
                    continue;
                }
                String currentVersion = versionElement.getTextContent().trim();
                if (!oldVersion.equals(currentVersion)) {
                    continue;
                }
            }

            groupIdElement.setTextContent(newGroupId);
            artifactIdElement.setTextContent(newArtifactId);

            if (versionElement != null) {
                versionElement.setTextContent(newVersion);
            } else {
                versionElement = doc.createElement("version");
                versionElement.setTextContent(newVersion);
                dependency.appendChild(versionElement);
            }

            updatedCount++;
        }

        if (updatedCount > 0) {
            writeXmlDocument(doc, file);
            System.out.println("Updated " + updatedCount + " dependenc" + (updatedCount > 1 ? "ies" : "y") + " in: " + file);
        } else {
            System.out.println("No matching dependency found in: " + file);
        }
    }

    /**
     * 替换 pom.xml 文件中 {@code <properties>} 标签下指定元素的内容。
     * <p>
     * 在 pom.xml 的 {@code <properties>} 节点中查找名为 {@code element} 的子元素，
     * 将其文本内容替换为 {@code text}。
     * 如果 {@code <properties>} 或指定元素不存在，则输出错误信息并跳过。
     * </p>
     *
     * @param filePath pom.xml 文件路径
     * @param element  {@code <properties>} 标签下要替换的子元素名称
     * @param text     替换后的新文本内容
     */
    public static void replaceProperties(String filePath, String element, String text) {
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            System.err.println("Path does not exist: " + filePath);
            return;
        }

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(false);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(path.toFile());

            Element root = doc.getDocumentElement();

            Element propertiesElement = findChildElement(root, "properties");
            if (propertiesElement == null) {
                System.err.println("No <properties> element found in pom.xml");
                return;
            }

            Element targetElement = findChildElement(propertiesElement, element);
            if (targetElement == null) {
                System.err.println("Element <" + element + "> not found in <properties>");
                return;
            }

            targetElement.setTextContent(text);

            writeXmlDocument(doc, path);

            System.out.println("Updated <" + element + "> in pom.xml: " + filePath);
        } catch (Exception e) {
            System.err.println("Error processing pom.xml: " + filePath + " - " + e.getMessage());
        }
    }

    /**
     * 在指定父元素下查找具有给定标签名的直接子元素。
     *
     * @param parent  父元素
     * @param tagName 要查找的标签名
     * @return 找到的子元素，如果不存在则返回 {@code null}
     */
    private static Element findChildElement(Element parent, String tagName) {
        NodeList children = parent.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if (child.getNodeType() == Node.ELEMENT_NODE && tagName.equals(child.getNodeName())) {
                return (Element) child;
            }
        }
        return null;
    }

    /**
     * 将 DOM 文档写回文件，并去除多余空行。
     *
     * @param doc  要写入的 DOM 文档
     * @param file 目标文件路径
     * @throws Exception 写入过程中发生的异常
     */
    private static void writeXmlDocument(Document doc, Path file) throws Exception {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

        StringWriter writer = new StringWriter();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(writer);
        transformer.transform(source, result);

        String content = writer.toString().replaceAll("(?m)^[ \\t]*\\r?\\n", "");
        Files.writeString(file, content);
    }

    public static void main(String[] args) {
        PomFileEditor.replace("./pom.xml", "java.version", "21");
        PomFileEditor.replace("./pom.xml", "com.belerweb", "pinyin4j", "com.belerweb.1", "pinyin4j.1", "2.5.1", "2.5.1.1");
    }

}