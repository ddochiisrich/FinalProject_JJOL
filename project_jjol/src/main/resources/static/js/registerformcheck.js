$(document).ready(function() {
    $('#registerForm').on('submit', function(e) {
        // 사용자 아이디 검사
        var userId = $('#userId').val();
        if (userId === '') {
            alert('사용자 아이디를 입력해주세요.');
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

        // 이름 검사
        var name = $('#name').val();
        if (name === '') {
            alert('이름을 입력해주세요.');
            e.preventDefault();
            return;
        }

        // 이메일 검사 (간단한 형식 검사)
        var email = $('#email').val();
        if (email === '') {
            alert('이메일을 입력해주세요.');
            e.preventDefault();
            return;
        }
        // 이메일 형식 검사
        if (!isValidEmail(email)) {
            alert('올바른 이메일 형식이 아닙니다.');
            e.preventDefault();
            return;
        }

        // 전화번호 검사 (000-0000-0000 형식)
        var phone = $('#phone').val();
        if (phone === '') {
            alert('전화번호를 입력해주세요.');
            e.preventDefault();
            return;
        }
        // 전화번호 형식 검사
        if (!isValidPhoneNumber(phone)) {
            alert('전화번호는 000-0000-0000 형식으로 입력해주세요.');
            e.preventDefault();
            return;
        }

        // 모든 검사 통과 시 폼 제출
        // 추가적인 검사 로직이 필요하다면 여기에 추가

    });

    // 이메일 유효성 검사 함수
    function isValidEmail(email) {
        // 간단한 이메일 형식 검사 예시
        // 실제 필요에 따라 더 엄격한 검사를 추가할 수 있음
        var re = /\S+@\S+\.\S+/;
        return re.test(email);
    }

    // 전화번호 형식 검사 함수 (000-0000-0000)
    function isValidPhoneNumber(phone) {
        var re = /^\d{3}-\d{4}-\d{4}$/;
        return re.test(phone);
    }

});
