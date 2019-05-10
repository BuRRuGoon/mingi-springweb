package org.mingi.book.chap11;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository("MemberDao")
public class MemberDaoImplUsingSpringJdbc implements MemberDao {
	static final String INSERT = "INSERT member(email, password, name) VALUES(?, sha2(?,256), ?)";
	static final String SELECT_ALL = "SELECT memberId, email, name, left(cdate,19) cdate FROM member ORDER BY memberId desc LIMIT ?,?";
	static final String COUNT_ALL = "SELECT count(memberId) count FROM member";

	@Autowired
	JdbcTemplate jdbcTemplate;

	final RowMapper<Member> memberRowMapper = new BeanPropertyRowMapper<>(Member.class);	
	
	@Override
	public Member selectByEamil(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insert(Member member) {
		// TODO Auto-generated method stub
		jdbcTemplate.update(INSERT, member.getEmail(),member.getPassword(),member.getName());
	}

	@Override
	public void update(Member memeber) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Member> selectAll(int offset, int count) {
		// TODO Auto-generated method stub
		return jdbcTemplate.query(SELECT_ALL, memberRowMapper, offset, count);
	}

	@Override
	public int countAll() {
		// TODO Auto-generated method stub
		return jdbcTemplate.queryForObject(COUNT_ALL, Integer.class);
	}
}
