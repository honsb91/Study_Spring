<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h3 class="mb-4">고객정보</h3>
	<table class="table">
	<colgroup>
		<col width="180px">
		<col>
	</colgroup>
	<tr><th>고객명</th>
		<td>${vo.name }</td>
	</tr>
	<tr><th>성별</th>
		<td>${vo.gender }</td>
	</tr>
	<tr><th>이메일</th>
		<td>${vo.email }</td>
	</tr>
	<tr><th>전화번호</th>
		<td>${vo.phone }</td>
	</tr>
	</table>
</body>
</html>