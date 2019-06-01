<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<header>
	<p>
		<span><a href="./">홈</a></span> |
		<span><a href="./app/members">회원목록</a></span> |
		<span><a href="./app/article/list">게시판</a></span> |
			<c:choose>
				<c:when test="${!empty sessionScope.MEMBER }">
					<span><a href="./app/member/memberInfo">${MEMBER.name }</a></span> |
					<span><a href="./app/logout">로그아웃</a></span>| <span>
					<a href="./app/letter/listReceived">받은메일함</a></span> | 
					<span><a href="./app/letter/listSent">보낸메일함</a></span>
				</c:when>
			<c:otherwise>
				<span><a href="./app/login/loginForm">로그인</a></span> |
				<span><a href="./app/register/step1">회원가입</a></span>
			</c:otherwise>
		</c:choose>
</header>