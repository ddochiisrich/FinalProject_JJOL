$(document).ready(function() {
    $('a[id^="deleteLecture"]').click(function() {
        var lectureId = $(this).attr('id').replace('deleteLecture', '');
        if (confirm('정말로 이 강의를 삭제하시겠습니까?')) {
            $.ajax({
                url: '/lectures/delete/' + lectureId,
                type: 'DELETE',
                success: function(result) {
                    alert('강의가 삭제되었습니다.');
                    location.reload();
                },
                error: function(error) {
                    alert('강의 삭제 중 오류가 발생했습니다.');
                }
            });
        }
    });

    $('a[id^="updateLecture"]').click(function() {
        var lectureId = $(this).attr('id').replace('updateLecture', '');
        window.location.href = '/lectures/update/' + lectureId;
    });
});
