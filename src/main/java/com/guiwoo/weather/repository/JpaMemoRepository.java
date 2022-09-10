package com.guiwoo.weather.repository;

import com.guiwoo.weather.domain.Memo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;

import javax.transaction.Transactional;

@Repository
public interface JpaMemoRepository extends JpaRepository<Memo, Integer> {

}
