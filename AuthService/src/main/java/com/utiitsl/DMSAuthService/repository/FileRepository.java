package com.utiitsl.DMSAuthService.repository;

import com.utiitsl.DMSAuthService.entity.User;
import com.utiitsl.DMSAuthService.entity.file.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, String> {

    List<FileEntity> findByUser(User user);
}