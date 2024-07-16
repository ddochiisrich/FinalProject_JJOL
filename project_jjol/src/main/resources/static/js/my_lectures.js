$(function() {
    // 모달이 필요한 경우 열기
    if ($('#errorModal_mylectures').length) {
        $('#errorModal_mylectures').modal('show');
    }
    if ($('#successModal_mylectures').length) {
        $('#successModal_mylectures').modal('show');
    }

    let lectureIdToDelete;
    let lectureIdToUpdate;

    // 삭제
    $('[id^=mylecturesDeleteLecture]').on("click", function() {
        lectureIdToDelete = $(this).attr('id').replace('mylecturesDeleteLecture', '');
        $('#mylectures-passwordModalLabel').text('비밀번호 확인');
        $('#mylectures-passwordModal').modal('show');
        $('#mylectures-passwordModalButton').attr('data-action', 'delete');
    });
    
    // 수정
    $('[id^=mylecturesUpdateLecture]').on("click", function() {
        lectureIdToUpdate = $(this).attr('id').replace('mylecturesUpdateLecture', '');
        $('#mylectures-passwordModalLabel').text('비밀번호 확인');
        $('#mylectures-passwordModal').modal('show');
        $('#mylectures-passwordModalButton').attr('data-action', 'update');
    });

    // 비밀번호 확인 모달 처리
    $('#mylectures-passwordModalButton').on("click", function() {
        const action = $(this).attr('data-action');
        const password = $('#mylectures-passwordInput').val();

        if (action === 'delete' && password) {
            $(`#mylecturesCheckForm${lectureIdToDelete}`).attr("action", "deleteLecture");
            $(`#mylecturesCheckForm${lectureIdToDelete}`).attr("method", "post");
            $(`#mylecturesCheckForm${lectureIdToDelete}`).append(`<input type="hidden" name="password" value="${password}">`);
            $(`#mylecturesCheckForm${lectureIdToDelete}`).submit();
        } else if (action === 'update' && password) {
            $(`#mylecturesCheckForm${lectureIdToUpdate}`).attr("action", "updateLecture");
            $(`#mylecturesCheckForm${lectureIdToUpdate}`).attr("method", "post");
            $(`#mylecturesCheckForm${lectureIdToUpdate}`).append(`<input type="hidden" name="password" value="${password}">`);
            $(`#mylecturesCheckForm${lectureIdToUpdate}`).submit();
        }

        $('#mylectures-passwordModal').modal('hide');
        $('#mylectures-passwordInput').val('');
    });

    // 모달 닫기 버튼 명시적으로 처리
    $('.mylectures-modal-close').on("click", function() {
        $('#errorModal_mylectures, #successModal_mylectures, #mylectures-passwordModal').modal('hide');
    });

    $('.mylectures-btn[data-dismiss="modal"]').on("click", function() {
        $('#errorModal_mylectures, #successModal_mylectures, #mylectures-passwordModal').modal('hide');
    });
});
