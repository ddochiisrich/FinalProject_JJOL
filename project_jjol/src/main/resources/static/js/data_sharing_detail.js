$(function () {
    $("#data-sharing-detail-datasharingDelete").on("click", function () {
        var dataNo = $("input[name='dataNo']").val(); // 데이터 번호 가져오기
        $.ajax({
            type: "POST",
            url: "/deleteDataSharing",
            data: { dataNo: dataNo },
            success: function () {
                alert("삭제되었습니다.");
                window.location.href = "/DataSharingView"; // 삭제 후 목록 페이지로 리다이렉트
            },
            error: function (e) {
                console.log("Error: ", e);
                alert("삭제에 실패했습니다.");
            }
        });
    });

    $("#data-sharing-detail-datasharingUpdateForm").on("click", function () {
        var dataNo = $("input[name='dataNo']").val(); // 데이터 번호 가져오기
        window.location.href = '/updateDataSharingForm?no=' + dataNo; // 수정 폼의 URL
    });

    $("#data-sharing-detail-commentForm").submit(function (event) {
        event.preventDefault(); // 기본 동작 방지

        var formData = {
            ddNo: $("input[name='dataNo']").val(),
            dscContent: $("#data-sharing-detail-commentContent").val(),
            dscWriter: $("#data-sharing-detail-commentWriter").val()
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
                $("#data-sharing-detail-commentContent").val("");
                $("#data-sharing-detail-commentWriter").val("");
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
    $(".data-sharing-detail-comment-list").html(listItems); // 댓글 목록 업데이트
}
