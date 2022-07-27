package com.starfish.extension.jwt;

import lombok.Data;

import java.io.Serializable;

/**
 * Token
 * JWT是由三段信息构成的，将这三段信息文本用.链接一起就构成了Jwt字符串。
 * 就像这样:eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjk5fQ.7DwG61h2GjZWA-sSUQA2jFVIenyOhz310rCUbCi09hY
 * 第一部分我们称它为头部（header),第二部分我们称其为载荷（payload, 类似于飞机上承载的物品)，第三部分是签证（signature).
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2022-07-27
 */
@Data
public class Token implements Serializable {

    private Header header;

    private Payload payload;

    private Signature signature;

}
