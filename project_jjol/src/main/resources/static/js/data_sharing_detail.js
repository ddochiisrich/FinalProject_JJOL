$(function () {
    $("#datasharingDelete").on("click", function () {
        $("#checkForm").attr("action", "/deleteDataSharing");
        $("#checkForm").attr("method", "post");
        $("#checkForm").submit();
    });

    $("#datasharingUpdateForm").on("click", function () {
        var dataNo = $("input[name='dataNo']").val(); // 데이터 번호 가져오기
        // 수정하기 폼으로 이동
        window.location.href = '/updateDataSharingForm?no=' + dataNo; // 수정 폼의 URL
    });

    $("#commentForm").submit(function (event) {
        event.preventDefault(); // 기본 동작 방지

        var formData = $(this).serialize(); // 폼 데이터 직렬화

        $.ajax({
            type: "POST",
            url: "/comments/datacommentadd",
            data: formData,
            success: function (response) {
                // 성공적으로 댓글을 추가한 경우, 댓글 목록을 업데이트
                updateCommentList();
                // 폼 초기화
                $("#commentContent").val("");
            },
            error: function (e) {
                console.log("Error: ", e);
            }
        });
    });

    // 댓글 목록 업데이트 함수
    function updateCommentList() {
        $.get("/comments/byDataNo/" + $("input[name='dataNo']").val(), function (data) {
            // 댓글 목록을 업데이트
            var listItems = "";
            if (data.length > 0) {
                $.each(data, function (index, comment) {
                    listItems += '<li class="list-group-item">';
                    listItems += '<p>' + comment.commentContent + '</p>';
                    listItems += '<small>작성자: ' + comment.commentWriter + '</small>';
                    listItems += '</li>';
                });
            } else {
                listItems += '<li class="list-group-item">';
                listItems += '<p>등록된 댓글이 없습니다.</p>';
                listItems += '</li>';
            }
            $(".data-sharing-detail-comment-list").html(listItems);
        });
    }

    // 페이지 로드 시 댓글 목록 초기화
    updateCommentList();
});
