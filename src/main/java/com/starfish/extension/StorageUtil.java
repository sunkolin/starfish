package com.starfish.extension;

import com.google.common.base.Strings;
import com.starfish.enumeration.ResultEnum;
import com.starfish.exception.CustomException;
import com.starfish.util.CommonUtil;
import com.starfish.util.FileUtil;
import com.starfish.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * StoragePlus
 *
 * @author sunny
 * @version 1.0.0
 * @since 2020-10-28
 */
@Slf4j
@SuppressWarnings("unused")
public class StorageUtil {

    /**
     * 存储文件并返回一个链接地址
     * 使用时根据具体需求改造，不可直接返回服务器文件保存路径
     *
     * @param file                文件
     * @param targetFileDirectory 目标目录
     * @return 链接地址
     */
    public static String storage(MultipartFile file, String targetFileDirectory) {
        // 验证参数
        checkParam(file);

        // 获取文件类型
        String fineName = file.getOriginalFilename();
        String fileType = FileUtil.getExtension(fineName);

        // 获取最终路径
        String targetPath = getTargetPath(targetFileDirectory, fileType);
        File targetFile = new File(targetPath);

        // 保存文件
        try {
            //create the directory
            boolean result = targetFile.mkdirs();
            if (result) {
                log.info("{} parent directory is not exist,create it success.", targetPath);
            } else {
                log.info("{} parent directory is exist", targetPath);
            }
            file.transferTo(targetFile);
        } catch (Exception e) {
            throw new CustomException(ResultEnum.SAVE_FILE_EXCEPTION);
        }

        return targetPath;
    }

    private static void checkParam(MultipartFile file) {
        //check if the file is empty
        if (file.isEmpty()) {
            throw new CustomException(ResultEnum.PARAM_ERROR);
        }

        // check the file name and file type
        String filename = file.getOriginalFilename();
        if (Strings.isNullOrEmpty(filename)) {
            throw new CustomException(ResultEnum.PARAM_ERROR);
        }

        // file type
        String extension = FileUtil.getExtension(filename);

        //check the type
        if (Strings.isNullOrEmpty(extension)) {
            throw new CustomException(ResultEnum.FILE_TYPE_ERROR);
        }
    }

    private static String getTargetPath(String targetFileDirectory, String fileType) {
        //自动生成文件名称，不使用原名称
        // 日期17位，随机数字3位，uuid16位，一共36位
        String date = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        String uuid = CommonUtil.getUuid();
        String number = StringUtil.randomString("0123456789", 3);
        String filename = date + uuid + number;

        // 判断文件路径是否以反斜杠结尾，如果不是，自动加上一个反斜杠
        if (!targetFileDirectory.endsWith(File.separator)) {
            targetFileDirectory = targetFileDirectory + File.separator;
        }

        // 拼接文件路径
        return targetFileDirectory + filename + File.separator + fileType;
    }

}
