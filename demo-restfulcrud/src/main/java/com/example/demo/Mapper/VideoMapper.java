package com.example.demo.Mapper;

import com.example.demo.entities.Video;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @param :
 * @author : lindonglin
 * @Description :
 * @ate : 9:58  2019/5/2
 * @return :
 */
@Mapper
public interface VideoMapper {
    public List<Video> selectAll();

    public void add(Video video);

    public Video findById(Integer id);
}
