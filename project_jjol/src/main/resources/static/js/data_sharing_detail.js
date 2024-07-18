$(document).ready(function () {
    loadComments();

    $("#data-sharing-detail-datasharingDelete").on("click", function () {
        var dataNo = $("input[name='dataNo']").val(); // 데이터 번호 가져오기
        $.ajax({
            type: "POST",
            url: "/deleteDataSharing",
            data: { dataNo: dataNo },
            success: function () {
                showModal("삭제되었습니다.", "/DataSharingView");
            },
            error: function (e) {
                console.log("Error: ", e);
                showModal("삭제에 실패했습니다.");
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
            },
            error: function (e) {
                console.log("Error: ", e);
            }
        });
    });

    // 파일 다운로드 링크 클릭 이벤트
    $("a[data-file-name]").on("click", function(event) {
        event.preventDefault(); // 기본 동작 방지
        var fileName = $(this).data("fileName");
        downloadFile(fileName);
    });

    // 모달 창 닫기 버튼 이벤트
    $(".data-sharing-detail-modal-close").on("click", function() {
        $("#myModal").modal('hide');
    });
});

function loadComments() {
    var dataNo = $("input[name='dataNo']").val(); // 데이터 번호 가져오기
    $.ajax({
        type: "GET",
        url: "/comments/byDataNo/" + dataNo,
        success: function (response) {
            updateCommentList(response);
        },
        error: function (e) {
            console.log("Error: ", e);
        }
    });
}

function deleteComment(commentId) {
    var currentUser = $("#data-sharing-detail-commentWriter").val();
    $.ajax({
        type: 'DELETE',
        url: '/comments/' + commentId,
        data: { currentUser: currentUser },
        success: function(response) {
            showModal('댓글이 삭제되었습니다.');
            loadComments();
        },
        error: function(e) {
            showModal('댓글 삭제에 실패했습니다.');
            console.log(e);
        }
    });
}

function updateCommentList(data) {
    // 댓글 목록을 업데이트
    var listItems = "";
    if (data.length > 0) {
        $.each(data, function (index, comment) {
            // Create a new Date object with the UTC time
            var utcDate = new Date(comment.dscTime);

            // Use Intl.DateTimeFormat to format the date in KST
            var options = {
                timeZone: 'Asia/Seoul',
                year: 'numeric',
                month: '2-digit',
                day: '2-digit',
                hour: '2-digit',
                minute: '2-digit',
                second: '2-digit',
                hour12: false
            };
            var kstDate = new Intl.DateTimeFormat('ko-KR', options).format(utcDate);

            listItems += '<li class="list-group-item">';
            listItems += '<div>';
            listItems += '<p>' + comment.dscContent + '</p>';
            listItems += '<small>작성자: ' + comment.dscWriter + '</small><br>';
            listItems += '<small>작성일: ' + kstDate + '</small>';
            listItems += '</div>';
            if (comment.dscWriter === $("#data-sharing-detail-commentWriter").val()) {
                listItems += '<div><button type="button" class="btn btn-outline-danger btn-sm data-sharing-detail-delete-comment-btn" onclick="deleteComment(' + comment.dscNo + ')"><i class="fas fa-trash-alt"></i></button></div>';
            }
            listItems += '</li>';
        });
    } else {
        listItems += '<li class="list-group-item">';
        listItems += '<p>등록된 댓글이 없습니다.</p>';
        listItems += '</li>';
    }
    $(".data-sharing-detail-comment-list").html(listItems); // 댓글 목록 업데이트
}

function showModal(message, redirectUrl) {
    $("#modal-message").text(message);
    $("#myModal").modal('show');
    if (redirectUrl) {
        $("#myModal").on('hidden.bs.modal', function () {
            window.location.href = redirectUrl;
        });
    } else {
        $("#myModal").on('hidden.bs.modal', function () {
            loadComments();
        });
    }
}

function downloadFile(fileName) {
    const url = '/downloadFile/' + fileName;
    const link = document.createElement('a');
    link.href = url;
    link.download = fileName;
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
}
