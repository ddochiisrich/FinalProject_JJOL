$(document).ready(function() {
	// 폼 제출 시 유효성 검사
    $('#loginForm').on('submit', function(e) {

        var userId = $('#userId').val();
        if (userId === '') {
            alert('아이디를 입력해주세요.');
            e.preventDefault();
            return;
        }

        var password = $('#password').val();
        if (password === '') {
            alert('비밀번호를 입력해주세요.');
            e.preventDefault();
            return;
        }
    });
});
