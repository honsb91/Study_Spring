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
<h3 class="mb-4">공지사항</h3>
<table class="table tb-list">
<colgroup>
	<col>
    <col width='120px'>
    <col width='140px'>
</colgroup>
<tr>
    <th>제목</th>
    <th>작성자</th>
    <th>작성일자</th>
</tr>

<c:if test="${empty list }">
<tr><td colspan="3">공지글이 없습니다</td></tr>
</c:if>

<c:forEach items="${list }" var="vo">
<tr>
    <td class="text-start">${vo.title }</td>
    <td>${vo.writer }</td>
    <td>${vo.writedate }</td>
</tr>
</c:forEach>
</table>
</body>
</html>