package com.example.demo.Controller;

import com.example.demo.entities.Video;
import com.example.demo.service.VideoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.util.*;

/**
 * @param :
 * @author : lindonglin
 * @Description :
 * @ate : 10:06  2019/5/2
 * @return :
 */
@Controller
@RequestMapping("/video")
public class VideoController {
    @Resource
    private VideoService videoService;

    @RequestMapping("/list")
    public String list(Model model) {
        List<Video> list = videoService.list();
        System.out.println(list.size());
        model.addAttribute("list",Optional.ofNullable(list).orElse(new ArrayList<>()));
        return "video/list";
    }

    @RequestMapping("/upload")
    public String uploadVideo(@RequestParam(value = "file") MultipartFile multipartFile,Model model) {
        Map<String,Object> result = new HashMap<>();
        Video upload = videoService.upload(multipartFile);
        if (upload != null && upload.getSize() != 0) {
            result.put("msg",true);
        } else {
            result.put("msg",false);
        }
        model.addAttribute("result",result);
        return "video/list";
    }

    @RequestMapping("/download/{id}")
    public void downloadVideo(HttpServletRequest request, HttpServletResponse response,@PathVariable("id") Integer id) {
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding(request.getCharacterEncoding());
        videoService.download(response,id);

    }
}
