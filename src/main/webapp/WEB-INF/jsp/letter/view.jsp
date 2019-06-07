<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html>
<head>
<base href="${pageContext.request.contextPath }/" />
<title>메일</title>
<script type="text/javascript">
	function confirmDelete() {
		if (confirm("메일을 삭제하시겠습니까?"))
			return true;
		else
			return false;
	}
</script>
</head>
<body>
	<%@ include file="/WEB-INF/jsp/header.jsp"%>
	<h2>메일보기</h2>
	<p>
		<c:choose>
			<c:when test="${param.mode == 'SENT' }">
				<a href="./app/letter/listSent">목록</a>
			</c:when>
			<c:otherwise>
				<a href="./app/letter/listReceived">목록</a>
			</c:otherwise>
		</c:choose>
		<a href="./app/letter/delete?letterId=${letter.letterId }&mode=${param.mode}"
			onclick="return confirmDelete();">삭제</a>
</p>
	<hr/>
	<p>
		<span style="font-weight: bold;">메일제목 : ${letter.title } </span>
	</p>
	<hr/>
	<p>
        	 <span>발신인 : ${letter.senderName }(${letter.senderId })</span> || <span>수신인 : ${letter.receiverName }(${letter.receiverId })</span>
	<hr/>
	<p>
		<span>내용 : ${letter.content }</span>
	</p>
	<hr/>
	<p>
		<span>등록일시 : ${letter.cdate }</span>
		</p>
		<p>
		<span>발신번호 : ${letter.letterId }</span>
	</p>
</body>
</html>