<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="/css/style.css" rel="stylesheet" type="text/css">
<title>새글 등록</title>
</head>
<body>
	<div class="container1">
		<h3>새글등록하기</h3>
		<hr>
		<form action="insertBoard" method="post">
			<table>
				<tr>
					<td bgcolor="orange" width="70">
						제목
					</td>
					<td align="left">
						<input type="text" name="title" />
					</td>
				</tr>
				<tr>
					<td bgcolor="orange">
						작성자
					</td>
					<td align="left">
						<input type="text" name="writer" size="10" />
					</td>
				</tr>
				<tr>
					<td bgcolor="orange">
						내용
					</td>
					<td align="left">
						<textarea name="content" cols="40" rows="10"></textarea>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<input type="submit" value="새글등록" />
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>