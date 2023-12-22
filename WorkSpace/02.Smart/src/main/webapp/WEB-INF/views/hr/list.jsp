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
	<h3>사원목록</h3>
	<table class="table tb-list">
	<colgroup>
		<col width='80px'>
		<col width='250px'>
		<col width='300px'>
		<col>
		<col width='120px'>
	</colgroup>
		<tr>
			<th>사번</th>
			<th>사원명</th>
			<th>부서</th>
			<th>업무</th>
			<th>입사일자</th>
		</tr>
		<tr>
			<td>100</td>
			<td>홍길동</td>
			<td>영업부</td>
			<td>영업</td>
			<td>2023-12-22</td>
		</tr>
	</table>
</body>
</html>