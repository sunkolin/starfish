package com.starfish.extension.pinyin;

import lombok.Data;

import java.io.Serializable;

/**
 * ChineseKeyValue
 *
 * @author sunny
 * @version 1.0.0
 * @since 2020-10-28
 */
@Data
public class ChineseKeyValue implements Serializable {

    private Object key;

    private String value;

}
