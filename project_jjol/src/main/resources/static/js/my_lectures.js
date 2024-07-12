$(function() {
	
	// 삭제
	for (let i = 1; i <= 20; i++) {
	    $(`#deleteLecture${i}`).on("click", function() {
	        if (confirm("정말로 삭제하시겠습니까?")) {
	            let password = prompt("비밀번호를 다시 입력해주세요.");
	            if (password != null) {
	                $(`#checkForm${i}`).attr("action", "deleteLecture");
	                $(`#checkForm${i}`).attr("method", "post");
	                $(`#checkForm${i}`).append(`<input type="hidden" name="password" value="${password}">`);
	                $(`#checkForm${i}`).submit();
	            }
	        } else {
	            return;
	        }
	    });
	}
	
	// 수정
	for (let i = 1; i <= 20; i++) {
		$(`#updateLecture${i}`).on("click", function() {
			let password = prompt("비밀번호를 다시 입력해주세요.");
			if (password != null) {
				$(`#checkForm${i}`).attr("action", "updateLecture");
                $(`#checkForm${i}`).attr("method", "post");
                $(`#checkForm${i}`).append(`<input type="hidden" name="password" value="${password}">`);
                $(`#checkForm${i}`).submit();
			} else {return;}
		})
	}
})