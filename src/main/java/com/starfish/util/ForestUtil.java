package com.starfish.util;

import com.dtflys.forest.Forest;
import com.dtflys.forest.callback.OnSuccess;
import com.dtflys.forest.http.ForestHeaderMap;

/**
 * ForestUtil
 *
 * @author sunny
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
        final ForestHeaderMap[] result = {null};
        OnSuccess<String> onSuccess = (data, req, res) -> {
            result[0] = res.getHeaders();
        };
        Forest.head(url).onSuccess(onSuccess).execute();
        return result[0];
    }

}
