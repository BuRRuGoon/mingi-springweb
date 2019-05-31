package org.mingi.letter;

import java.util.List;

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
public class LetterController {
	
	@Autowired
	LetterDao letterDao;
	
	static final Logger logger = LogManager.getLogger();
	
	/**
	 * 글 목록
	 */
	@GetMapping("/letter/getlist")
	public void letterGetList(
			@RequestParam(value = "page", defaultValue = "1") int page,
			Model model) {

		// 페이지당 행의 수와 페이지의 시작점
		final int COUNT = 100;
		int offset = (page - 1) * COUNT;

		List<Letter> letterGetList = letterDao.getlistLetter(offset, COUNT);
		int totalCount = letterDao.getLetterCount();
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("getletterList", letterGetList);
}
	
	@GetMapping("/letter/sendlist")
	public void letterSendList(
			@RequestParam(value = "page", defaultValue = "1") int page,
			Model model) {

		// 페이지당 행의 수와 페이지의 시작점
		final int COUNT = 100;
		int offset = (page - 1) * COUNT;

		List<Letter> letterSendList = letterDao.sendlistLetter(offset, COUNT);
		int totalCount = letterDao.getLetterCount();
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("getletterList", letterSendList);
}
	
	@PostMapping("/letter/sendletter")
	public String snedLetter(Letter letter,
			@SessionAttribute("MEMBER") Member member) {
		letter.setReceiverId(member.getMemberId());
		letter.setReceiverName(member.getName());
		letterDao.sendLetter(letter);
		return "redirect:/app/letter/sendlist";
	}
	
}
