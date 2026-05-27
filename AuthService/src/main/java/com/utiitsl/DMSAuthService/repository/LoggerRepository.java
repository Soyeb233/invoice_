package com.utiitsl.DMSAuthService.repository;

import com.utiitsl.DMSAuthService.entity.Logger;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LoggerRepository extends JpaRepository<Logger, Long> {
}