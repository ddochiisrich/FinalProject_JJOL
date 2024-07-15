$(document).ready(function() {
    // 아이디 입력 필드에 대한 이벤트 리스너 추가
    $('#userId').on('blur input', function() {
        if ($(this).val() === '') {
            showError($(this), '아이디를 입력해주세요.');
        } else {
            hideError($(this));
        }
    });

    // 비밀번호 입력 필드에 대한 이벤트 리스너 추가
    $('#password').on('blur input', function() {
        if ($(this).val() === '') {
            showError($(this), '비밀번호를 입력해주세요.');
        } else {
            hideError($(this));
        }
    });

    // 폼 제출 시 유효성 검사
    $('#loginForm').on('submit', function(e) {
        e.preventDefault(); // 폼 전송 막기

        var isValid = true;

        var userId = $('#userId').val();
        if (userId === '') {
            showError($('#userId'), '아이디를 입력해주세요.');
            isValid = false;
        }

        var password = $('#password').val();
        if (password === '') {
            showError($('#password'), '비밀번호를 입력해주세요.');
            isValid = false;
        }

        if (isValid) {
            $.ajax({
                url: '/login',
                type: 'POST',
                data: { userId: userId, password: password },
                success: function(response) {
                    if (response.status === 'success') {
                        window.location.href = '/lectures';
                    } else {
                        if (response.field === 'userId') {
                            showError($('#userId'), response.error);
                        } else if (response.field === 'password') {
                            showError($('#password'), response.error);
                        }
                    }
                }
            });
        }
    });

    // 피드백 애니메이션 함수
    function animateFeedback(element) {
        element.removeClass('fadeIn').addClass('shake');
        setTimeout(function() {
            element.removeClass('shake');
        }, 500);
    }

    // 오류 메시지 표시 함수
    function showError(element, message) {
        var feedbackElement = element.siblings('.form-text');
        if (feedbackElement.length === 0) {
            feedbackElement = $('<small class="form-text"></small>').insertAfter(element);
        }
        feedbackElement.text(message).css('color', 'red');
        animateFeedback(feedbackElement);
        feedbackElement.show();
    }

    // 오류 메시지 숨기기 함수
    function hideError(element) {
        var feedbackElement = element.siblings('.form-text');
        feedbackElement.hide();
    }
});
