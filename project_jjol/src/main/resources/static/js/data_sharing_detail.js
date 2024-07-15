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

        var formData = {
            ddNo: $("input[name='dataNo']").val(),
            dscContent: $("#commentContent").val(),
            dscWriter: $("#commentWriter").val()
        };

        $.ajax({
            type: "POST",
            url: "/comments/datacommentadd",
            contentType: "application/json",
            data: JSON.stringify(formData),
            success: function (response) {
                console.log("response : ", response);
                // 성공적으로 댓글을 추가한 경우, 댓글 목록을 업데이트
                updateCommentList(response);
                // 폼 초기화
                $("#commentContent").val("");
                $("#commentWriter").val("");
            },
            error: function (e) {
                console.log("Error: ", e);
            }
        });
    });
});

// 댓글을 쓰거나, 수정하거나, 삭제 할 때 - 이벤트 handler로 사용
function updateCommentList(data) {
    // 댓글 목록을 업데이트
    var listItems = "";
    if (data.length > 0) {
        $.each(data, function (index, comment) {
            listItems += '<li class="list-group-item">';
            listItems += '<p>' + comment.dscContent + '</p>'; // 데이터베이스 속성명에 맞추기
            listItems += '<small>작성자: ' + comment.dscWriter + '</small>'; // 데이터베이스 속성명에 맞추기
            listItems += '</li>';
        });
    } else {
        listItems += '<li class="list-group-item">';
        listItems += '<p>등록된 댓글이 없습니다.</p>';
        listItems += '</li>';
    }
    $(".datasharing-detail-comment-list").html(listItems); // 댓글 목록 업데이트
}
