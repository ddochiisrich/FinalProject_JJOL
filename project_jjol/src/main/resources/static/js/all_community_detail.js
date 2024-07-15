$(document).ready(function() {
	// 게시물 삭제 버튼 클릭 시 처리
	$("#allcommunityDelete").on("click", function() {
		$("#checkForm").attr("action", "/deleteAllCommunity");
		$("#checkForm").attr("method", "post");
		$("#checkForm").submit();
	});

	// 게시물 수정 폼으로 이동
	$("#allcommunityUpdateForm").on("click", function() {
		var allcNo = $("input[name='allcNo']").val();
		window.location.href = '/updateAllCommunityForm?no=' + allcNo;
	});

	// 댓글 등록 폼 제출 처리 (AJAX)
	$("#cmccommentForm").submit(function(event) {
		event.preventDefault();

		var formData = {
			ccNo: $("input[name='ccNo']").val(),
			cmcContent: $("#commentContent").val(),
			cmcWriter: $("#commentWriter").val()
		};

		$.ajax({
			type: "POST",
			url: "/comments/cccommentadd",
			contentType: "application/json",
			data: JSON.stringify(formData),
			success: function(response) {
				updateCommentList(response);
				$("#commentContent").val("");
				$("#commentWriter").val("");
			},
			error: function(e) {
				console.log("Error: ", e);
			}
		});
	});

	// 댓글 삭제 처리 (이벤트 위임 사용)
	    $(document).on('click', '.delete-comment', function() {
	        var commentId = $(this).data('comment-id');
			let no = $(this).data('bbs-no');

	        $.ajax({
	            type: 'POST',
	            url: '/deleteComment',
	            data: { commentId: commentId, no: no },
	            success: function(response) {
	                // 서버로부터 정상적인 JSON 데이터가 반환되었는지 확인
					console.log(response);
					updateCommentList(response); // 정상적으로 파싱된 데이터로 업데이트
					
	                try {
	                    //var comments = JSON.parse(response); // JSON 파싱 시도
	                    //
	                } catch (e) {
	                    console.error('서버에서 반환된 데이터 형식이 잘못되었습니다.');
	                }
	            },
	            error: function(xhr, status, error) {
	                console.error('댓글 삭제 중 오류 발생:', error);
	            }
	        });
	    });

	    // 댓글 목록 업데이트 함수
		function updateCommentList(comments) {
		    var $commentsList = $("#comments");
		    $commentsList.empty(); // 기존 목록 비우기
		    
		    if (comments && comments.length > 0) {
		        var listItems = "";
		        $.each(comments, function(index, comment) {
		            listItems += '<li class="list-group-item">';
		            listItems += '<p>' + comment.cmcContent + '</p>';
		            listItems += '<small>작성자: ' + comment.cmcWriter + '</small>';
		            listItems += '<button type="button" class="delete-comment" data-comment-id="' 
						+ comment.cmcNo + '" data-bbs-no="' + comment.ccNo + '">삭제</button>';
		            listItems += '</li>';
		        });
		        $commentsList.append(listItems); // 한 번에 추가
		    } else {
		        $commentsList.html('<li class="list-group-item"><p>등록된 댓글이 없습니다.</p></li>');
		    }
		}
	});