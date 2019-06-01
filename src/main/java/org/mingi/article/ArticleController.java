package org.mingi.article;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mingi.book.chap11.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class ArticleController {
	@Autowired
	ArticleDao articleDao;

	static final Logger logger = LogManager.getLogger();
	/**
	 * 글 목록
	 */
	@GetMapping("/article/list")
	public void articleList(
			@RequestParam(value = "page", defaultValue = "1") int page,
			Model model) {

		// 페이지당 행의 수와 페이지의 시작점
		final int COUNT = 100;
		int offset = (page - 1) * COUNT;

		List<Article> articleList = articleDao.listArticles(offset, COUNT);
		int totalCount = articleDao.getArticlesCount();
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("articleList", articleList);
	}

	/**
	 * 글 보기
	 */
	@GetMapping("/article/view")
	public void articleView(@RequestParam("articleId") String articleId,
			Model model) {
		
		Article article = articleDao.getArticle(articleId);
		article.getUserId();
		model.addAttribute("article", article);
	}

	/**
	 * 글 등록 화면
	인터셉터/디폴트맵핑 사용으로 
	@GetMapping("/article/addForm")
	public String articleAddForm(HttpSession session) {
		Object memberObj = session.getAttribute("MEMBER");
		if(memberObj == null)
			return "redirect:/app/loginForm";
		return "article/addForm";
	}
 */
	/**
	 * 글 등록
	 */
	@PostMapping("/article/add")
	public String articleAdd(Article article,HttpSession session,
			@SessionAttribute("MEMBER") Member member) {
	
		article.setUserId(member.getMemberId());
		article.setName(member.getName());
		articleDao.addArticle(article);
		return "redirect:/app/article/list";
}
	
	/**
	 * 글 수정
	 */
	
	@GetMapping("/article/updateForm")
	public void articleupdateForm(Article article,HttpSession session,
			@RequestParam("articleId") String articleId,
			@SessionAttribute("MEMBER") Member member, Model model) {
		article = articleDao.getArticle(articleId);
		article.getUserId();
		
		if(!member.getMemberId().equals(article.getUserId()))
			throw new RuntimeException("수정 권한이 없습니다");
		
		model.addAttribute("article", article);
		
	}
	
	@PostMapping("/article/update")
	public String articleUpdate(Article article,HttpSession session,
			@SessionAttribute("MEMBER") Member member) {
		article.setUserId(member.getMemberId());

		int updatedRows = articleDao.updateArticle(article);
		
		// 권한 체크 : 글이 수정되었는지 확인
				if (updatedRows == 0)
					// 글이 수정되지 않음. 자신이 쓴 글이 아님
					throw new RuntimeException("수정 권한이 없습니다");

		return "redirect:/app/article/view?articleId=" + article.getArticleId();
	}
	
	/**
	 * 글 삭제
	 */
	@GetMapping("/article/delete")
	public String articleDelete(Article article,HttpSession session,
			@RequestParam("articleId") String articleId,
			@SessionAttribute("MEMBER") Member member) {
		int updatedRows = articleDao.deleteArticle(articleId,
				member.getMemberId());
		
		// 권한 체크 : 글이 삭제되었는지 확인
				if (updatedRows == 0)
					// 글이 삭제되지 않음. 자신이 쓴 글이 아님
					throw new RuntimeException("삭제 권한이 없습니다");


				logger.debug("글을 삭제했습니다. articleId={}", articleId);
				return "redirect:/app/article/list";
	}
}
