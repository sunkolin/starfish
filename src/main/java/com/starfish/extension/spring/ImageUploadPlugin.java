package com.starfish.extension.spring;

import com.starfish.enums.ResultEnum;
import com.starfish.exception.CustomException;
import com.starfish.util.FileUtil;
import com.starfish.util.StringUtil;
import com.starfish.validator.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Image File Upload
 *
 * @author sunny
 * @version 1.0.0
 * @since 2015-05-14
 */
@Slf4j
public class ImageUploadPlugin {

    public static String storage(HttpServletRequest request, MultipartFile file) throws IOException {
        //convert to multipart file

        //check if the file is empty
        if (file.isEmpty()) {
            throw new CustomException(ResultEnum.PARAM_ERROR);
        }

        // check the file type
        String filename = file.getOriginalFilename();
        String fileType = getFileType(filename);

        //generate the new file name
        String realPath = request.getSession().getServletContext().getRealPath("/");
        String relativePath = "upload";
        String newFilename = new SimpleDateFormat("yyyyMMdd").format(new Date()) + "_" + UUID.randomUUID().toString().replaceAll("-", "") + "_" + StringUtil.randomString("0123456789", 8) + fileType;
        String relativeFilename = File.separator + relativePath + File.separator + newFilename;
        String finalPath = realPath + relativeFilename;
        File output = new File(finalPath);

        //create the directory
        boolean result = output.mkdirs();
        if (result) {
            log.info("{} parent directory is not exist,create it success.", finalPath);
        } else {
            log.info("{} parent directory is exist", finalPath);
        }

        file.transferTo(output);
        return relativeFilename;
    }

    public static String getFileType(String fileName) {
        Validator.validateString(fileName, 1, 1000, -1, "file name error,file name length is too long");

        // to lower case
        String lowerFileName = fileName.toLowerCase();

        // file type
        String fileType = FileUtil.getFileType(lowerFileName);

        //check the type
        if (fileType == null || fileType.length() == 0) {
            throw new CustomException(ResultEnum.SYSTEM_EXCEPTION.getCode(), "upload file type error.");
        }

        return fileType;
    }

}
