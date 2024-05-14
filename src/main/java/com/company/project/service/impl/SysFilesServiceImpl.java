package com.company.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.company.project.common.config.FileUploadProperties;
import com.company.project.common.exception.BusinessException;
import com.company.project.common.utils.DataResult;
import com.company.project.common.utils.DateUtils;
import com.company.project.common.utils.ToolUtils;
import com.company.project.entity.SysFilesEntity;
import com.company.project.mapper.SysFilesMapper;
import com.company.project.service.FileStorageService;
import com.company.project.service.SysFilesService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.util.*;

/**
 * 文件上传 服务类
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@EnableConfigurationProperties(FileUploadProperties.class)
@Service("sysFilesService")
public class SysFilesServiceImpl extends ServiceImpl<SysFilesMapper, SysFilesEntity> implements SysFilesService {
    @Resource
    private FileUploadProperties fileUploadProperties;

    @Autowired
    FileStorageService fileStorageService;

    @Override
    public DataResult saveFile(MultipartFile file) {
        Map<String, Object> resultMap = new HashMap<>(4);

        long size = fileStorageService.save(file, (fileName, path) -> {
            // 保存文件记录
            SysFilesEntity sysFilesEntity = new SysFilesEntity();
            sysFilesEntity.setFileName(fileName);
            sysFilesEntity.setFilePath(path);
            sysFilesEntity.setCtime(ToolUtils.timestamp2());

            this.save(sysFilesEntity);
            resultMap.put("path", path);
        });
        resultMap.put("size", size);

        return DataResult.success(resultMap);
    }

    @Override
    public void removeByIdsAndFiles(List<String> ids) {
        List<SysFilesEntity> list = this.listByIds(ids);
        list.forEach(entity -> {
            //如果之前的文件存在，删除
            File file = new File(entity.getFilePath());
            if (file.exists()) {
                file.delete();
            }
        });

        this.removeByIds(ids);
    }

    /**
     * 获取文件后缀名
     *
     * @param fileName 文件名
     * @return 后缀名
     */
    private String getFileType(String fileName) {
        if (fileName != null && fileName.contains(".")) {
            return fileName.substring(fileName.lastIndexOf("."));
        }
        return "";
    }
}