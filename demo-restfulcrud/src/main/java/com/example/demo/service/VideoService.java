package com.example.demo.service;

import com.example.demo.entities.Video;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @param :
 * @author : lindonglin
 * @Description :
 * @ate : 10:04  2019/5/2
 * @return :
 */
public interface VideoService {
    public List<Video> list();

    public Video upload(MultipartFile multipartFile);

    public void download(HttpServletResponse response,Integer id);
}
