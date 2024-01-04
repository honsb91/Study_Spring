/**
 * 공통 적용 함수 선언
 */
 
$(function(){
		
		if($(".date").length > 0){
			
			var today = new Date();
			var range = today.getFullYear()-100 + " : " + today.getFullYear();     // "1990 : 2023" 
			
		   $.datepicker.setDefaults({
			dateFormat: "yy-mm-dd",
			changeYear : true,
			changeMonth : true,
			showMonthAfterYear: true,
			dayNamesMin: ["일","월","화","수","목","금","토"],
			monthNamesShort: ["1월","2월","3월","4월","5월","6월","7월","8월","9월","10월","11월","12월"],
			maxDate: today,
			yearRange : range,
		})	
			
		}
		
		
		$(".date").datepicker();
		$(".date").attr("readonly",true); //읽기전용
	
		// 날짜 선택 후 x 아이콘을 보이게해주는 함수?
		$(".date").change(function(){
			$(this).next(".date-delete").css("display", "inline");
		})
		
		// x 버튼 클릭시 날짜도 같이 지우는 함수
		$(".date-delete").click(function(){
			$(this).prev(".date").val("")
			$(this).css("display", "none");
		})
		
		// 파일선택시
		$("input#file-single").change(function(){
			//console.log($(this))
			//console.log(this.files[0])
			var _preview = $(this).closest(".file-info").find(".file-preview")
			var _delete = $(this).closest(".file-info").find(".file-delete")
			
			var attached = this.files[0];
			if(attached){
				console.log('name>' , attached.name)
				// 파일크기 제한을 두고자 한다면
				if(rejectFile(attached, $(this))) return;
				
				// 이미지만 첨부해야한다면
				if(isImage(attached.name)){
					_delete.removeClass("d-none") // 삭제보이게
					
					if(_preview.length > 0){
					   _preview.html("<img>");
					   
					   var reader = new FileReader();
					   reader.readAsDataURL(attached);
					   reader.onload = function(e){              //e <- event 변수
						_preview.children("img").attr("src", this.result)
					 }
				   }
				}else{
				/*	_preview.empty();
					$(this).val("");*/
					initFileInfo($(this))
				}
				
				console.log( 'attached> ' , $(this).val())
			}
		})
		
		$(".file-delete").click(function(){
			// 선택했던 파일정보 삭제, 미리보기도 안보이게, 삭제버튼도 안보이게
/*			var _info = $(this).closest(".file-info");
			_info.find(".file-preview").empty();
			_info.find("input[type=file]").val("");
			$(this).addClass("d-none")*/
			initFileInfo($(this))
		})
	
})



// 이미지 파일인지 확인
function isImage( filename ) {
	var imgs = ["png", "jpg", "jpeg", "gif", "bmp", "webp"]
	var ext = filename.substr(filename.lastIndexOf(".")+1);
	return imgs.indexOf(ext) == -1 ? false : true;
	
// 파일크기제한
function rejectFile(fileInfo, tag){
	// 1k = 1024, 1024*1024 = 1M
	if(fileInfo.size > 1024*1024*10){ //10M
		alert("10Mb를 넘는 파일은 첨부할 수 없습니다.")
/*		tag.val("");
		// 이전에 이미지선택된게 있었다면 미리보기도 없애야 함
		tag.closest(".file-info").find(".file-preview").empty()
		tag.closest(".file-info").find(".file-delete").addClass("d-none")*/
		initFileInfo(tag)
		return true;
	}else{
		return false;
	}
}

// 선택했던 파일정보 삭제, 미리보기도 안보이게, 삭제버튼도 안보이게
function initFileInfo(tag){
	var _info = $(this).closest(".file-info");
		_info.find(".file-preview").empty();
		_info.find("input[type=file]").val("");
		_info.find(".file-delete").addClass("d-none");
}
	
}