package org.mingi.book.chap11;

import java.util.List;

public interface MemberDao {
	Member selectByEamil(String email);
	
	void insert(Member member);
	
	void update(Member memeber);
	
	List<Member> selectAll(int offset,int count);
	
	int countAll();
}
