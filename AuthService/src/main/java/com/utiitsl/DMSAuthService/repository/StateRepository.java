package com.utiitsl.DMSAuthService.repository;

import com.utiitsl.DMSAuthService.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateRepository extends JpaRepository<State,Long> {
}
