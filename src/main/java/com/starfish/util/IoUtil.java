package com.starfish.util;

import java.io.Closeable;
import java.io.IOException;

/**
 * IoUtil
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2021-05-07
 */
public class IoUtil {

    private IoUtil() {

    }

    /**
     * 关闭
     *
     * @param closeable 对象
     */
    public static void close(final Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (final IOException ioe) {
            // ignore
        }
    }

}
