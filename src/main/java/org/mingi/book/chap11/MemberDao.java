package org.mingi.book.chap11;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDao {

	static final String INSERT = "INSERT member(email, password, name) VALUES(?, sha2(?,256), ?)";

	static final String SELECT_ALL = "SELECT memberId, email, name, left(cdate,19) cdate FROM member ORDER BY memberId desc LIMIT ?,?";

	static final String COUNT_ALL = "SELECT count(memberId) count FROM member";

	static final String SELECT_BY_LOGIN = "SELECT memberId, email, password, name FROM member WHERE (email,password) = (?,sha2(?,256))";

	@Autowired
	JdbcTemplate jdbcTemplate;

	final RowMapper<Member> memberRowMapper = new BeanPropertyRowMapper<>(
			Member.class);

	public void insert(Member member) {
		jdbcTemplate.update(INSERT, member.getEmail(), member.getPassword(),
				member.getName());
	}

	public List<Member> selectAll(int offset, int count) {
		return jdbcTemplate.query(SELECT_ALL, memberRowMapper, offset, count);
	}


	public int countAll() {
		return jdbcTemplate.queryForObject(COUNT_ALL, Integer.class);
	}

	public Member selectByLogin(String email, String password) {
		return jdbcTemplate.queryForObject(SELECT_BY_LOGIN, memberRowMapper,
				email, password);
	}
}