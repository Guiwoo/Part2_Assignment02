package com.guiwoo.weather.repository;

import com.guiwoo.weather.domain.Memo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class JpaMemoRepositoryTest {

    @Autowired
    JpaMemoRepository jpaMemoRepository;

    @Test
    void insertMemoTest(){
        //given
        Memo m = new Memo();
        m.setText("holymoly");
        //when
        Memo save = jpaMemoRepository.save(m);
        //then
        assertEquals(save.getText(),m.getText());
    }

    @Test
    void findById(){
        //given
        Memo m = new Memo();
        m.setId(1);
        m.setText("me");
        jpaMemoRepository.save(m);
        //when
        Optional<Memo> byId = jpaMemoRepository.findById(1);
        //then
        assertNotNull(byId);
        assertEquals(byId.get().getText(),"me");
    }
}