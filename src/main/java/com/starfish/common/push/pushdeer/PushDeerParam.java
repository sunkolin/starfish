package com.starfish.common.push.pushdeer;

import lombok.Data;

/**
 * PushDeerParam
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2026-01-06
 */
@Data
public class PushDeerParam {

    /**
     * PushKey
     */
    private String pushKey;

    /**
     * 推送消息内容
     */
    private String text;

    /**
     * 消息内容第二部分，选填
     */
    private String desp;

    /**
     * 格式，文本=text，markdown，图片=image，默认为markdown,选填
     */
    private String type;

}
