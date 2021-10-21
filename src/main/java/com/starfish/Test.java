package com.starfish;

import com.dtflys.forest.Forest;
import com.dtflys.forest.callback.OnSuccess;
import com.dtflys.forest.http.ForestHeaderMap;
import com.dtflys.forest.http.ForestRequest;
import com.dtflys.forest.http.ForestResponse;

/**
 * Test
 *
 * @author sunny
 * @version 1.0.0
 * @since 2020-10-28
 */
public class Test {

    public static void main(String[] args) {
        String url = "https://vd3.bdstatic.com/mda-mj84pad7qad8hhur/sc/cae_h264_clips/1633750377008601665/mda-mj84pad7qad8hhur.mp4";
//        ForestHeaderMap forestHeaderMap = Forest.head(url).getHeaders();

        final String contentType = null;
        OnSuccess<String> onSuccess = (data, req, res) -> {
            ForestHeaderMap forestHeaderMap =  res.getHeaders();
                     contentType = forestHeaderMap.getValue("Content-Type");
            System.out.println(contentType);
        };

        Forest.head(url).onSuccess(onSuccess).execute();

//        System.out.println(forestRequest);
    }

}
