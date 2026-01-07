package com.starfish.common.push.bark;

import lombok.Data;

import java.util.List;

/**
 * PushDeerParam
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2026-01-06
 */
@Data
public class BarkParam {

    /**
     * 推送标题
     */
    private String title;

    /**
     * 推送副标题
     */
    private String subtitle;

    /**
     * 推送内容
     */
    private String body;

    /**
     * 推送内容，支持基础 Markdown 格式。传递了此参数将忽略 body 字段, 发送时请注意处理特殊字符。
     */
    private String markdown;

    /**
     * 设备 key
     */
    private String device_key;

    /**
     * key 数组，用于批量推送
     */
    private List<String> device_keys;

    /**
     * 推送中断级别。
     * critical: 重要警告, 在静音模式下也会响铃
     * active：默认值，系统会立即亮屏显示通知
     * timeSensitive：时效性通知，可在专注状态下显示通知。
     * passive：仅将通知添加到通知列表，不会亮屏提醒。
     */
    private String level = "active";

    /**
     * 重要警告的通知音量，取值范围：0-10，不传默认值为5
     */
    private String volume = "5";

    /**
     * 推送角标，可以是任意数字
     */
    private String badge;

    /**
     * 传"1"时，通知铃声重复播放
     */
    private String call = "0";

    /**
     * 传"1"时， iOS14.5以下自动复制推送内容，iOS14.5以上需手动长按推送或下拉推送
     */
    private String autoCopy = "1";

    /**
     * 复制推送时，指定复制的内容，不传此参数将复制整个推送内容。
     */
    private String copy;

    /**
     * 可以为推送设置不同的铃声
     */
    private String sound;

    /**
     * 为推送设置自定义图标，设置的图标将替换默认Bark图标。
     * 图标会自动缓存在本机，相同的图标 URL 仅下载一次。
     */
    private String icon;

    /**
     * 推送图片
     */
    private String image;

    /**
     * 对消息进行分组，推送将按group分组显示在通知中心中。
     * 也可在历史消息列表中选择查看不同的群组。
     */
    private String group;

    /**
     * 加密推送的密文
     */
    private String ciphertext;

    /**
     * 传1保存推送，传其他的不保存推送，不传按APP内设置来决定是否保存。
     */
    private String isArchive = "1";

    /**
     * 点击推送时，跳转的URL ，支持URL Scheme 和 Universal Link
     */
    private String url;

    /**
     * 传 "none" 时，点击推送不会弹窗
     */
    private String action;

    /**
     * 使用相同的ID值时，将更新对应推送的通知内容
     * 需 Bark v1.5.2, bark-server v2.2.5 以上，Json传参需使用字符串类型
     */
    private String id;

    /**
     * 传 "1" 时，将从系统通知中心和APP内历史记录中删除通知，需搭配 id 参数使用
     * 需在设置里开启”后台App刷新“，否则无效。
     */
    private String delete;

}
