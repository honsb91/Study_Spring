/**
 *  회원관련 처리 함수
 */
 
 var member = {
	// 태그별로 상태확인
	tagStatus: function(tag, input){
		if(tag.is("[name=user_pw]")) return this.userpw_status( tag.val(), input);
		else if(tag.is("[name=user_pw_ck]")) return this.userpw_ck_status( tag.val());
		else if(tag.is("[name=user_id]")) return this.userid_status(tag.val());
		else if(tag.is("[name=email]")) return this.email_status(tag.val());
	},
	
	email_status : function(email){
		var reg = /[a-z0-9]+@[a-z]+\.[a-z]{2,3}/g;
		if(email=="") return this.common.empty;
		else if(reg.test(email)) return this.email.valid;
		else return this.email.invalid;
	},
	
	email : {
		valid:{ is:true, desc : "유효합니다"},
		invalid:{ is:false, desc : "유효하지않습니다"},
	},
	
	userid_status : function(id){
		var reg = /[^a-z0-9]/g
		if(id=="") return this.common.empty;
		else if(reg.test(id)) return this.userid.invalid;
		else if(id.length < 5) return this.common.min;
		else if(id.length > 10) return this.common.max;
		else return this.userid.valid;
	},
	
	userid : {
		invalid: {is:false, desc: "영문 소문자, 숫자만 입력하세요."},
		valid : {is:true, desc: "중복확인하세요."},
		usable : {is:true, desc: "사용가능한 아이디입니다."},
		unUsable : {is:false, desc: "이미 사용중인 아이디입니다."},
	},
	
	common: {
		empty:{is:false, desc:"입력하세요!"},
		min : {is:false, dese:"5자이상 입력하세요!"},
		max : {is:false, dese:"10자이내 입력하세요!"},
		space : {is:false, dese:"공백없이 입력하세요!"},
	},
	
	userpw: {
		valid : {is:true, desc:"사용가능합니다!"},
		lack : {is:false, desc:"영문 대/소문자,숫자를 모두 포함해야 합니다!"},
		invalid : {is:false, desc:"영문 대/소문자,숫자만 입력하세요!"},
		equal : {is:true, desc:"비밀번호가 일치합니다!"},
		notEqual : {is:false, desc:"비밀번호가 일치하지 않습니다!"},
			
	},
	
	space : /\s/g,
	
	userpw_ck_status : function( pw_ck ){
		if( pw_ck == $("[name=user_pw]").val()) return this.userpw.equal;
		else									return this.userpw.notEqual;
	},
	
	showStatus : function(tag){
		var status = this.tagStatus ( tag, true )
		tag.closest(".input-check").find(".desc")
								   .text( tag.attr("title") + " " + status.desc)
								   .removeClass("text-success text-danger")
								   .addClass(status.is ? "text-success" : "text-danger")
	},
	
	userpw_status : function(pw , input){
		if(input){
			$("[name=user_pw_ck]").val("");
			$("[name=user_pw_ck]").closest(".input-check").find(".desc")
								  .removeClass("text-success text-danger")
								  .text("");
		}
		
		var upper = /[A-Z]/g, lower=/[a-z]/g, digit=/[0-9]/g, reg=/[^A-Za-z0-9]/g;
		
		if( pw == "") return this.common.empty;
		else if( pw.match(this.space) ) return this.common.space;
		else if(reg.test(pw)) return this.userpw.invalid;
		else if( pw.length < 5) return this.common.min;
		else if( pw.length > 10) return this.common.max;
		else if( !upper.test(pw) || !lower.test(pw) || !digit.test(pw)) return this.userpw.lack;
		else 		  return this.userpw.valid;
	}
}