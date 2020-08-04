package com.starfish.util;

import org.springframework.web.util.HtmlUtils;

/**
 * HtmlTool
 *
 * @author sunny
 * @version 1.0.0
 * @since 2015-05-11
 */
public class HtmlTool extends HtmlUtils {

    /**
     * JavaScriptUtils中方法
     *
     * @param input 内容
     * @return 过滤掉特殊字符后的内容
     */
    public static String javaScriptEscape(String input) {
        if (input == null) {
            return null;
        }

        StringBuilder filtered = new StringBuilder(input.length());
        char prevChar = '\u0000';
        char c;
        for (int i = 0; i < input.length(); i++) {
            c = input.charAt(i);
            if (c == '"') {
                filtered.append("\\\"");
            } else if (c == '\'') {
                filtered.append("\\'");
            } else if (c == '\\') {
                filtered.append("\\\\");
            } else if (c == '/') {
                filtered.append("\\/");
            } else if (c == '\t') {
                filtered.append("\\t");
            } else if (c == '\n') {
                if (prevChar != '\r') {
                    filtered.append("\\n");
                }
            } else if (c == '\r') {
                filtered.append("\\n");
            } else if (c == '\f') {
                filtered.append("\\f");
            } else if (c == '\b') {
                filtered.append("\\b");
            }
            // No '\v' in Java, use octal value for VT ascii char
            else if (c == '\013') {
                filtered.append("\\v");
            } else if (c == '<') {
                filtered.append("\\u003C");
            } else if (c == '>') {
                filtered.append("\\u003E");
            }
            // Unicode for PS (line terminator in ECMA-262)
            else if (c == '\u2028') {
                filtered.append("\\u2028");
            }
            // Unicode for LS (line terminator in ECMA-262)
            else if (c == '\u2029') {
                filtered.append("\\u2029");
            } else {
                filtered.append(c);
            }
            prevChar = c;

        }
        return filtered.toString();
    }

}

