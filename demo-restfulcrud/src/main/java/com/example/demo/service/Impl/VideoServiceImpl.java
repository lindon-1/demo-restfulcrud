package com.example.demo.service.Impl;

import com.example.demo.Mapper.VideoMapper;
import com.example.demo.entities.Video;
import com.example.demo.service.VideoService;
import com.example.demo.utils.VideoUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.util.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @param :
 * @author : lindonglin
 * @Description :
 * @ate : 10:05  2019/5/2
 * @return :
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class VideoServiceImpl implements VideoService {
    private final int fileSize = 100;
    //真实路径
    private final String realPath = "E://video_res";

    private final String path = "\\video\\res";
    @Resource
    private VideoMapper videoMapper;

    @Override
    public List<Video> list() {
        return videoMapper.selectAll();
    }

    @Override
    public Video upload(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            log.error("视频为空");
            return null;
        }

        String fileName = multipartFile.getOriginalFilename().toString();
        log.info("fileName : {}", fileName);
        //获取后缀名
        String fileExt = VideoUtils.getFileExt(fileName);
        boolean isVideo = VideoUtils.checkMediaType(fileExt);
        if (!isVideo) {
            log.error("视频类型不对 ：{}", fileExt);
            return null;
        }
        long size = multipartFile.getSize();
        if (size == 0) {
            log.error("视频大小为0");
            return null;
        }
        String uuids = UUID.randomUUID().toString();

        String realFilePath = realPath + File.separator + uuids + File.separator + fileName;
        String filePath = path + File.separator + uuids + File.separator + fileName;
        String name = fileName.substring(0, fileName.lastIndexOf("."));
        VideoUtils.createParentPath(realFilePath);
        File file = new File(realFilePath);

        // 转入文件
        try {
            //multipartFile.transferTo(file);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
            bufferedOutputStream.write(multipartFile.getBytes());
            bufferedOutputStream.flush();
            bufferedOutputStream.close();
        } catch (IllegalStateException e) {
// TODO Auto-generated catch block
            log.error("报错信息：{}", e.getMessage());
            return null;
        } catch (IOException e) {
// TODO Auto-generated catch block
            log.error("报错信息：{}", e.getMessage());
            return null;
        }
        Video video = new Video();
        video.setFilePath(filePath);
        video.setName(name);
        video.setSize(size);
        video.setType(fileExt);
        videoMapper.add(video);
        return video;
    }

    @Override
    public void download(HttpServletResponse response ,Integer id) {
        Video video = videoMapper.findById(id);
        if (video == null) {
            log.error("don't find this video by : {}", id);
        }
        String filePath = video.getFilePath();
        String realFilePath = filePath.replace(path, realPath);
        FileInputStream fileInputStream = null;
        try {
            File file = new File(realFilePath);
            fileInputStream = new FileInputStream(file);
            response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
            IOUtils.copy(fileInputStream, response.getOutputStream());
            response.flushBuffer();
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
           if (fileInputStream != null) {
               try {
                   fileInputStream.close();
               } catch (IOException e) {
                   log.error(e.getMessage());
               }
           }
        }
    }
}
