<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="/css/style.css" rel="stylesheet" type="text/css">
<title>글 목록</title>
</head>
<body>
	<div class="container">
		<h1>게시글목록</h1>
		<table>
			<tr bgcolor="orange" >
				<th width="100">번호</th><th width="200">제목</th>
				<th width="150">작성자</th><th width="150">등록일</th>
				<th width="100">조회수</th>
			</tr>
			
			<c:forEach var="board" items="${boardList}">
			<tr>
				<td>${board.seq}</td>
				<td>
					<a href="getBoard?seq=${board.seq}">${board.title}</a>
				</td>
				<td>${board.writer}</td>
				<td><fmt:formatDate value="${board.createDate}" pattern="yyyy-MM-dd"/></td>
				<td>${board.cnt}</td>
			</tr>
			</c:forEach>
			
		</table>
		<br>
		<a href="insertBoard">새글등록</a>
	</div>
</body>
</html>