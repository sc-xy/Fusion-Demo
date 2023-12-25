package top.scxy.fusion.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.scxy.fusion.constant.UtilConstant;
import top.scxy.fusion.entity.File;
import top.scxy.fusion.entity.PageResult;
import top.scxy.fusion.mapper.FileMapper;
import top.scxy.fusion.service.FileService;

import java.util.List;

@Service
@Transactional
public class FileServiceImpl implements FileService {
    private final FileMapper fileMapper;
    @Autowired
    public FileServiceImpl(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }
    @Override
    public Integer uploadFile(File file) {
        return fileMapper.uploadFile(file);
    }

    @Override
    public Integer deleteFile(Integer id) {
        return fileMapper.deleteFile(id);
    }

    @Override
    public File getFileById(Integer id) {
        return fileMapper.getFileById(id);
    }

    @Override
    public PageResult<File> getFileListByUserId(Integer id, Integer pageNum) {
        Integer totalCount = fileMapper.getFileCountByUserId(id);
        List<File> fileList = fileMapper.getFileListByUserId(id, (pageNum - 1) * 10, UtilConstant.PageSize);
        return new PageResult<>(pageNum, UtilConstant.PageSize, totalCount, fileList);
    }
}
