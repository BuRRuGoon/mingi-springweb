package org.mingi.book.chap11;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DefaultMappingController {
	
	Logger logger = LogManager.getLogger();
	/**러
	 * 다른 컨트롤
	 */
	@RequestMapping("/**")
	public void mapDefault() {
		logger.debug("Default mapping.");
	}
}
