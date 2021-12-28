package com.starfish.util;

import com.dtflys.forest.Forest;
import com.dtflys.forest.callback.OnSuccess;
import com.dtflys.forest.http.ForestHeaderMap;
import org.springframework.util.CollectionUtils;

/**
 * ForestUtil
 *
 * @author neacle
 * @version 1.0.0
 * @since 2021-10-21
 */
public class ForestUtil {

    /**
     * 获取header
     *
     * @param url 链接
     * @return 结果
     */
    public static ForestHeaderMap head(String url) {
        ForestHeaderMap forestHeaderMap = new ForestHeaderMap();
        OnSuccess<String> onSuccess = (data, req, res) -> {
            ForestHeaderMap headers = res.getHeaders();
            if (!CollectionUtils.isEmpty(headers)) {
                forestHeaderMap.putAll(headers);
            }
        };
        Forest.head(url).onSuccess(onSuccess).execute();
        return forestHeaderMap;
    }

}
