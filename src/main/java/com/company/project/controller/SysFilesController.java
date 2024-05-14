package com.company.project.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.company.project.common.utils.DataResult;
import com.company.project.entity.SysFilesEntity;
import com.company.project.service.FileStorageService;
import com.company.project.service.SysFilesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * 文件上传
 *
 */
@RestController
@RequestMapping("/sysFiles")
@Api(tags = "文件管理")
public class SysFilesController {
    @Resource
    private SysFilesService sysFilesService;

    @ApiOperation(value = "新增")
    @PostMapping("/upload")
    @RequiresPermissions(value = {"sysFiles:add", "sysContent:update", "sysContent:add"}, logical = Logical.OR)
    public DataResult add(@RequestParam(value = "file") MultipartFile file) {
        // 判断文件是否空
        if (Objects.isNull(file) || Objects.isNull(file.getOriginalFilename())
                || file.getOriginalFilename().trim().isEmpty()) {
            return DataResult.fail("文件为空");
        }

        return sysFilesService.saveFile(file);
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("/delete")
    @RequiresPermissions("sysFiles:delete")
    public DataResult delete(@RequestBody @ApiParam(value = "id集合") List<String> ids) {
        sysFilesService.removeByIdsAndFiles(ids);
        return DataResult.success();
    }

    @ApiOperation(value = "查询分页数据")
    @PostMapping("/listByPage")
    @RequiresPermissions("sysFiles:list")
    public DataResult findListByPage(@RequestBody SysFilesEntity sysFiles) {
        Page page = new Page(sysFiles.getPage(), sysFiles.getLimit());
        IPage<SysFilesEntity> iPage = sysFilesService.page(page, Wrappers.<SysFilesEntity>lambdaQuery()
                .orderByDesc(SysFilesEntity::getCtime));
        return DataResult.success(iPage);
    }


    @Autowired
    FileStorageService fileStorageService;



    @GetMapping("/files")
    public DataResult files() {
        List<Map<String, String>> files = fileStorageService.load()
                .map(path -> {
                    Map<String, String> answer = new HashMap<>();
                    String fileName = path.getFileName().toString();


                    String url = MvcUriComponentsBuilder
                            .fromMethodName(SysFilesController.class,
                                    "getFile",
                                    path.getFileName().toString()
                            ).build().toString();

                    answer.put("fileName", fileName);
                    answer.put("url", url);
                    return answer;
                }).collect(Collectors.toList());

        return DataResult.success(files);
    }

    @GetMapping("/files/{filename:.+}")
    public ResponseEntity<org.springframework.core.io.Resource> getFile(@PathVariable("filename") String filename) {
        org.springframework.core.io.Resource file = fileStorageService.load(filename);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + file.getFilename() + "\"")
                .body(file);
    }



}
