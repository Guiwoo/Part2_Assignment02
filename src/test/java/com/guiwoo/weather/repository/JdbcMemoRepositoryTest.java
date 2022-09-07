package com.guiwoo.weather.repository;

import com.guiwoo.weather.domain.Memo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class JdbcMemoRepositoryTest {

    @Autowired
    JdbcMemoRepository jdbcMemoRepository;

    @Test
    void insertMemoTest() throws Exception {
        Memo m = new Memo(1,"This is a memo");
        Memo save = jdbcMemoRepository.save(m);
        assertEquals(m,save);
    }

    @Test
    void findAll(){
        List<Memo> list = jdbcMemoRepository.findAll();

        assertEquals(list.size(),0);
    }
}