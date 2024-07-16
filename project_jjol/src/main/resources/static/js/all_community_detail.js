$(document).ready(function () {
    // 게시물 삭제 버튼 클릭 시 처리
    $("#allcommunityDelete").on("click", function () {
        $("#checkForm").attr("action", "/deleteAllCommunity");
        $("#checkForm").attr("method", "post");
        $("#checkForm").submit();
    });

    // 게시물 수정 폼으로 이동
    $("#allcommunityUpdateForm").on("click", function () {
        var allcNo = $("input[name='allcNo']").val();
        window.location.href = '/updateAllCommunityForm?no=' + allcNo;
    });

    // 댓글 등록 폼 제출 처리 (AJAX)
    $("#cmccommentForm").submit(function (event) {
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
            success: function (response) {
                updateCommentList(response);
                $("#commentContent").val("");
            },
            error: function (e) {
                console.log("Error: ", e);
            }
        });
    });

    // 댓글 삭제 처리 (이벤트 위임 사용)
    $(document).on('click', '.delete-comment', function () {
        var commentId = $(this).data('comment-id');
        let no = $(this).data('bbs-no');

        $.ajax({
            type: 'POST',
            url: '/deleteComment',
            data: { commentId: commentId, no: no },
            success: function (response) {
                updateCommentList(response);
            },
            error: function (xhr, status, error) {
                console.error('댓글 삭제 중 오류 발생:', error);
            }
        });
    });

    // 댓글 목록 업데이트 함수
    function updateCommentList(comments) {
        var $commentsList = $("#comments");
        $commentsList.empty();

        if (comments && comments.length > 0) {
            $.each(comments, function (index, comment) {
                var listItem = '<li class="list-group-item comment-item">';
                listItem += '<div>';
                listItem += '<p class="mb-1">' + comment.cmcContent + '</p>';
                if (comment.cmcWriter) {
                    listItem += '<small>작성자: ' + (comment.cmcWriter === loggedInUserName ? loggedInUserName : comment.cmcWriter) + '</small>';
                }
                listItem += '</div>';
                if (comment.cmcWriter === loggedInUserName) {
                    listItem += '<i class="fas fa-trash-alt delete-comment text-danger" data-comment-id="' + comment.cmcNo + '" data-bbs-no="' + comment.ccNo + '" style="cursor: pointer;"></i>';
                }
                listItem += '</li>';
                $commentsList.append(listItem);
            });
        } else {
            $commentsList.html('<li class="list-group-item"><p>등록된 댓글이 없습니다.</p></li>');
        }
    }
});
