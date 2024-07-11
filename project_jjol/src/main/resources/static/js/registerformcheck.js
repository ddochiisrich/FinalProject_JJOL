$(document).ready(function() {
    // 폼 제출 시 유효성 검사
    $('#studentRegisterForm, #instructorRegisterForm').on('submit', function(e) {
        var userId = $(this).find('#userId').val();
        if (userId === '') {
            alert('사용자 아이디를 입력해주세요.');
            e.preventDefault();
            return;
        }

        var password = $(this).find('#password').val();
        if (password === '') {
            alert('비밀번호를 입력해주세요.');
            e.preventDefault();
            return;
        }

        var name = $(this).find('#name').val();
        if (name === '') {
            alert('이름을 입력해주세요.');
            e.preventDefault();
            return;
        }

        var email = $(this).find('#email').val();
        if (email === '') {
            alert('이메일을 입력해주세요.');
            e.preventDefault();
            return;
        }
        if (!isValidEmail(email)) {
            alert('올바른 이메일 형식이 아닙니다.');
            e.preventDefault();
            return;
        }

        var phone = $(this).find('#phone').val();
        if (phone === '') {
            alert('전화번호를 입력해주세요.');
            e.preventDefault();
            return;
        }
        if (!isValidPhoneNumber(phone)) {
            alert('전화번호는 000-0000-0000 형식으로 입력해주세요.');
            e.preventDefault();
            return;
        }
    });

    // 이메일 유효성 검사 함수
    function isValidEmail(email) {
        var re = /\S+@\S+\.\S+/;
        return re.test(email);
    }

    // 전화번호 형식 검사 함수
    function isValidPhoneNumber(phone) {
        var re = /^\d{3}-\d{4}-\d{4}$/;
        return re.test(phone);
    }
});
