package com.utiitsl.DMSAuthService.entity.file;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.utiitsl.DMSAuthService.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "files")
public class FileEntity {

    @Id
    private String id;

    private String fileName;
    private String filePath;
    private String fileType;
    private long size;

    private Date createdAt ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;
}