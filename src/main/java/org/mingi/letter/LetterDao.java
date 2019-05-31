package org.mingi.letter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class LetterDao {
	//받은목록,보낸목록,보기,추가,삭제
		static final String GET_LIST_LETTER = "select letterId,senderId,senderName, title, content, left(cdate,16) cdate form letter order by receiverId desc limit ?,?";
		static final String GET_COUNT_LETTER = "select count(receiverId) from letter";
		
		static final String SEND_LIST_LETTER = "select letterId,receiverId,receiverName, title, content, left(cdate,16) cdate form letter order by senderId desc limit ?,?";
		static final String SEND_COUNT_LETTER = "select count(senderId) from letter";
		
		static final String GET_LETTER = "select letterId, title, content, receiverId, name, left(cdate,16) cdate from letter where letterId=?";
		
		static final String SEND_LETTER = "insert letter(title,content,senderId,receiverId,senderName,receiverName values(?,?,?,?,?,?)";
		
		static final String DELETE_LETTER = "delete form letter where (letterId, receiverId) = (?,?)";
		
		@Autowired
		JdbcTemplate jdbcTemplate;
		
		RowMapper<Letter> letterRowMapper = new BeanPropertyRowMapper<>(
				Letter.class);
		
		//받은목록
		public List<Letter> getlistLetter(int offset, int count){
			return jdbcTemplate.query(GET_LIST_LETTER, letterRowMapper, offset, count);
		}
		
		public int getLetterCount() {
			return jdbcTemplate.queryForObject(GET_COUNT_LETTER, Integer.class);
		}
		
		//보낸목록
		public List<Letter> sendlistLetter(int offset, int count){
			return jdbcTemplate.query(SEND_LIST_LETTER, letterRowMapper, offset, count);
		}
		
		public int sendLetterCount() {
			return jdbcTemplate.queryForObject(SEND_COUNT_LETTER, Integer.class);
		}
		
		//보기
		public Letter getLetter(String letterId) {
			return jdbcTemplate.queryForObject(GET_LETTER, letterRowMapper, letterId);
		}
		
		//등록
		//letterForm?receiverId=...&receiverName...
		public int sendLetter(Letter letter) {
			return jdbcTemplate.update(SEND_LETTER, letter.getTitle(), letter.getContent(),
					letter.getSenderId(), letter.getReceiverId(), letter.getSenderName(), letter.getReceiverName());
		}
		
		//삭제
		public int deleteLetter(String letterId, String receiverId) {
			return jdbcTemplate.update(DELETE_LETTER, letterId, receiverId);
		}

}
