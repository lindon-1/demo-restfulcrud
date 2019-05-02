package com.example.demo.entities;

import com.alibaba.druid.support.monitor.annotation.MTable;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @param :
 * @author : lindonglin
 * @Description :
 * @ate : 9:14  2019/5/2
 * @return :
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "video")
public class Video implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "size")
    private Long size;
    @Column(name = "filePath")
    private String filePath;
    @Column(name = "type")
    private String type;
    @Column(name = "uploadTime")
    private Date uploadTime;
}
