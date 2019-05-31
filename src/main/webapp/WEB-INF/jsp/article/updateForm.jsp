<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html>
<head>
<base href="${pageContext.request.contextPath }/" />
<title>게시판</title>
</head>
<body>
	<%@ include file="/WEB-INF/jsp/header.jsp"%>
	<h2>글 수정</h2>
	<p>
		<a href="./app/article/list">글 목록</a>
	</p>
	<c:choose>
		<c:when test="${sessionScope.MEMBER.memberId==article.userId }">
			<form action="./app/article/update" method="post">
	<p>회원이름 : ${MEMBER.name }</p>
	<p>글번호 : <input type=hidden name="articleId" id="articleId" size="20" value=${article.articleId }> ${article.articleId }
		<p>제목 :</p>
		<p>
			<input type="text" name="title" maxlength="100" style="width: 100%;" required value=${article.title }>
		</p>
		<p>내용 :</p>
		<p>
			<textarea name="content" style="width: 100%; height: 200px;" required>${article.contentHtml }</textarea>
		</p>
		<p>
			<button type="submit">수정</button>
			<button type="button" onclick="history.back();">취소</button>
		</p>
			</form>
		</c:when>
		<c:otherwise>
			<p>해당 게시글에 수정 권한이 없습니다.</p>
			<button type="button" onclick="history.back();">돌아가기</button>
		</c:otherwise>
	</c:choose>
</body>