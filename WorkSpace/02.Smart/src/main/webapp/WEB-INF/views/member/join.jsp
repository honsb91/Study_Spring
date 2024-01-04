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
<h3 class="mb-4">회원가입</h3>
<div class="mb-2 text-danger">*는 필수입력 항목입니다.</div>

<form method="post" enctype="multipart/form-data" action="register">
<table class="table tb-row">
<colgroup>
	<col width="180px">
	<col>
</colgroup>
<tr><th><span>*</span>성명</th>
	<td>
		<div class="row">
			<div class="col-auto">
				<input class="form-control" type="text" name="name" autofocus>
			</div>
		</div>
	</td>
</tr>
<tr><th><span>*</span>아이디</th>
	<td>
		<div class="row align-items-center input-check">
			<div class="col-auto">
				<input class="form-control check-item" type="text" name="user_id" title="아이디">
			</div>
			<div class="col-auto">
				<a class="btn btn-primary" id="btn-userid">중복확인</a>
			</div>
			<div class="col-auto">아이디는 영문 소문자나 숫자 5자~10자</div>
			<div class="desc"></div>
		</div>
	</td>
</tr>
<tr><th><span>*</span>비밀번호</th>
	<td>
		<div class="row align-items-center input-check">
			<div class="col-auto">
				<input class="form-control check-item" type="password" name="user_pw" title="비밀번호">
			</div>
			<div class="col-auto">비밀번호는 영문 대/소문자,숫자 조합 5자~10자</div>
			<div class="desc"></div>
		</div>
	</td>
</tr>
<tr><th><span>*</span>비밀번호 확인</th>
	<td>
		<div class="row align-items-center input-check">
			<div class="col-auto">
				<input class="form-control check-item" type="password" name="user_pw_ck" title="비밀번호 확인">
			</div>
			<div class="desc"></div>
		</div>
	</td>
</tr>
<tr><th><span>*</span>이메일</th>
	<td>
		<div class="row align-items-center input-check">
			<div class="col-auto">
				<input class="form-control check-item" type="text" name="email" title="이메일">
			</div>
			<div class="desc"></div>
		</div>
	</td>
</tr>
<tr><th><span>*</span>성별</th>
	<td>
		<div class="form-check form-check-inline">
    		<label class="form-check-label" >
        		<input class="form-check-input" type="radio" name="gender" value="남">남
    		</label>
		</div>
		<div class="form-check form-check-inline">
    		<label class="form-check-label" >
        		<input class="form-check-input" type="radio" name="gender" value="여" checked>여
    		</label>
		</div>
	</td>
</tr>
<tr><th>프로필이미지</th>
	<td>
		<div class="row">
			<div class="col-auto d-flex file-info align-items-center">
				<label>
					<input class="form-control image-only" id="file-single" type="file" name="file" accept="image/*">
					<i role="button" class="me-4 fa-solid fa-id-badge fs-3"></i>
				</label>
				<div class="d-flex">
					<span class="file-preview"></span>
					<i role="button" class="ms-4 d-none text-danger fs-4 fa-regular fa-trash-can file-delete"></i>
				</div>
			</div>
		</div>
	</td>
</tr>
<tr><th>생년월일</th>
	<td>
		<div class="row">
			<div class="col-auto d-flex align-items-center">
				<input class="form-control date" type="text" name="birth"> 
				<i role="button" class="fa-solid fa-xmark  fs-5 text-danger date-delete"></i>
			</div>
		</div>
	</td>
</tr>
<tr><th>전화번호</th>
	<td>
		<div class="row">
			<div class="col-auto">
				<input class="form-control" type="text" name="phone">
			</div>
		</div>
	</td>
</tr>
<tr><th>주소</th>
	<td>
		<div class="row">
		<div class="col-auto">
			<a class="btn btn-primary" id="btn-post">주소찾기</a>
		</div>
			<div class="col-auto">
				<input class="w-px80 form-control" type="text" name="post" readonly>
			</div>
		</div>
		<div class="row mt-2">
			<div class="col-7">
				<input class="form-control" type="text" name="address" readonly>
			</div>
			<div class="col">
				<input class="form-control" type="text" name="address">
			</div>
		</div>
	</td>
</tr>

</table>
</form>
<div class="btn-toolbar justify-content-center gap-2">
	<button class="btn btn-primary" id="btn-join">회원가입</button>
	<button class="btn btn-outline-primary px-4" onclick="history.go(-1)">취소</button>
</div>

<script src="<c:url value='/js/member.js'/>"></script>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>

	// span tag (*) 스타일 지정
	$(function(){
		$("table th span").addClass("me-2 text-danger")
		
		//13세 기준으로 적용한다면
		var today = new Date();
		var endDay = new Date( today.getFullYear()-13 , today.getMonth(), today.getDate()-1 );
		$("[name=birth]").datepicker("option", "maxDate", endDay);
	})
	
	// 다음 우편번호 가져오기
	$("#btn-post").click(function() {
		new daum.Postcode({
			oncomplete : function(data) {
				console.log(data)
				$("[name=post]").val(data.zonecode)
				
				var address = data.userSelectedType == "R" ? data.roadAddress : data.jibunAddress;
				if(data.buildingName !="") address +=  " ("+data.buildingName+")";
				$("[name=address]:eq(0)").val(address)
			}
		}).open();
	})
	
	// 입력항목 체크
	$(".check-item").on('keyup', function(){
		member.showStatus($(this))
	})
	
	// 아이디 중복확인
	$("#btn-userid").click(function(){
		id_check()
	})
	function id_check(){
		var _id = $("[name=user_id]");
		//DB에 입력아이디가 있는지 확인
		//입력이 유효한지부터 확인 후 유효하지 않으면 DB에 확인 불필요
		var status = member.tagStatus(_id);
		if(status.is){
			$.ajax({
				url:"idCheck",
				data : {user_id: _id.val()}
				
			}).done(function(response)){
				console.log(response)
				// 아이디 사용가능 : true       아이디 사용중 : false
				var status = response ? member.userid.usable : member.userid.unUsable;
				_id.closest(".input-check").find(".desc")
				   .text( _id.attr("title") + " " + status.desc)
				   .removeClass("text-success text-danger")
				   .addClass(status.is ? "text-success" : "text-danger")
			})
			
		}else{
			alert("아이디 중복확인 불필요! \n " + status.desc)
			_id.focus()
		}
	}
	
	$("#btn-join").click(function(){
		if($("[name=name]").val()==""){
			alert("성명을 입력하세요")
			$("[name=name]").focus()
			return;
		}
		
		/*  if( invalidStatus($("[name=user_pw]"))) return;*/
		if( invalidStatus($("[name=user_pw_ck]"))) return;
		if( invalidStatus($("[name=email]"))) return;
		
		$("form").submit()
	})
	
	function invalidStatus( tag ){
		var status = member.tagStatus(tag);
		if(status.is)
			return false;
		else{
			alert("회원가입 불가!\n" + tag.attr("title") + " " +  status.desc)
			return true;	
		}
	}
</script>

</body>
</html>