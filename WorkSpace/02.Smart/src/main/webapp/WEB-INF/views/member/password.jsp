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
	<h3 class="mb-4">비밀번호 변경</h3>

	<table class="table tb-row">
		<colgroup>
			<col width="180px">
			<col>
		</colgroup>
		<tr>
			<th>현재 비밀번호</th>
			<td><div class="row">
					<div class="col-auto">
						<input class="form-control" type="password" name="currentPw">
					</div>
				</div>
			</td>
		</tr>
		<tr>
			<th>새 비밀번호</th>
			<td><div class="row input-check">
					<div class="col-auto">
						<input class="form-control check-item" type="password" name="user_pw" title="비밀번호">
					</div>
					<div class="col-auto desc"></div>
					<div class="mt-2">비밀번호는 영문 대/소문자,숫자 조합 5~10자</div>
				</div>
			</td>
		</tr>
		<tr>
			<th>새 비밀번호 확인</th>
			<td><div class="row input-check">
					<div class="col-auto">
						<input class="form-control check-item" type="password" name="user_pw_ck" title="비밀번호 확인">
					</div>
					<div class="col-auto desc"></div>
				</div>
			</td>
		</tr>
	</table>
	<div class="btn-toolbar justify-content-center gap-2">
    	<button class="btn btn-primary px-4" id="btn-change">변경</button>
	</div>
	
	<script type="text/javascript" src="<c:url value='/js/member.js'/>"></script>
	
	<script>
		
		$("#btn-change").click(function(){
			//입력이 잘 되어져있는지 확인 후 DB에 저장	
			if(tagIsValid()){
				//alert("OK")
				// 현재 비밀번호가 DB의 현재비번과 일치하는지 확인
		/* 		$.ajax({
					url : "confirmPassword",
					data : {user_pw: $("[name=user_pw]").val() },
					success : function(){
						
					}
				}) */
				
				$.ajax({
					url : "confirmPassword",
					data : {user_pw: $("[name=currentPw]").val() },
				}).done(function( response){
					console.log( response )
					if( response == -1) location = "login";
					else if ( response == 0) { // 일치
						// 새 비번과 동일한지 확인
						if($("[name=user_pw]").val() == $("[name=currentPw]").val()){
							alert("현재 비밀번호가 새 비밀번호와 같습니다. \n새 비밀번호를 다시 입력하세요")
							$("[name=user_pw]").focus()
						}else{
							resetPassword()
						}
						
					}else if ( response == 1 ){ // 불일치
						alert("현재 비밀번호가 일치하지 않습니다!");
						$("[name=currentPw]").val("");
						$("[name=currentPw]").focus();
					}
				})
			}
		})
		
		function resetPassword(){
			$.ajax({
				url: "updatePassword",
				data: { user_pw: $("[name=user_pw]").val()}
			}).done(function( response ){
				if( response ) { alert("비밀번호가 변경되었습니다."); location = "<c:url value = '/'/>" }
				else		   alert("비밀번호 변경이 실패하였습니다.")
			})	
		}
		
		function tagIsValid(){
			var ok = true;
			if($("[name=currentPw]").val() == ""){
				alert("현재 비밀번호를 입력하세요!")
				$("[name=currentPw]").focus();
				ok = false;
			}else{
				$(".check-item").each(function(){
					// 비번,비번확인 입력상태 확인처리
					var status = member.tagStatus( $(this))
					//console.log(status.is , status.desc)
					if(! status.is){
						alert("비밀번호 변경 불가\n" + status.desc)
						$(this).focus();
						ok = false
						return ok;
					}
				})
			}
			return ok;
		}
		
		$(".check-item").keyup(function(){
			member.showStatus($(this))
		})
	</script>
</body>
</html>