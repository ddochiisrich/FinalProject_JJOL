$(document).ready(function() {
    $('#loginForm').on('submit', function(e) {
        // 사용자 아이디 검사
        var userId = $('#userId').val();
        if (userId === '') {
            alert('아이디를 입력해주세요.');
            e.preventDefault();
            return; // 검사 실패 시 종료
        }

        // 비밀번호 검사
        var password = $('#password').val();
        if (password === '') {
            alert('비밀번호를 입력해주세요.');
            e.preventDefault();
            return;
        }

        // 추가적인 검사 로직이 필요하다면 여기에 추가

    });
});
