package com.guiwoo.weather.repository;

import com.guiwoo.weather.domain.Memo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

@Repository

public class JdbcMemoRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcMemoRepository(DataSource d){
        this.jdbcTemplate = new JdbcTemplate(d);
    }

    public Memo save(Memo memo) throws Exception {
        String sql = "insert into memo values(?,?)";
        int update = jdbcTemplate.update(sql, memo.getId(), memo.getText());
        if(update<1){
            throw new Exception("failed to save");
        }
        return memo;
    }

    public List<Memo> findAll(){
        String sql = "select * from memo";
        return jdbcTemplate.query(sql, memoRowMapper());
    }

    public Optional<Memo> findById(int id){
        String sql = "select * from memo where id = ?";
        return jdbcTemplate.query(sql,memoRowMapper(),id).stream().findFirst();
    }

    private RowMapper<Memo> memoRowMapper(){
        return (rs, rowNum) -> new Memo(
                rs.getInt("id"),
                rs.getString("text")
        );
    }
}
