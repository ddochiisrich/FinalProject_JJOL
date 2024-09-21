$(document).ready(function() {
    // 실시간 유효성 검사 이벤트 리스너 추가
    $('[name="userId"], [name="password"], [name="name"], [name="email"], [name="phone"]').on('input', function() {
        var field = $(this).attr('name');
        var value = $(this).val();
        var form = $(this).closest('form');
        var role = $(form).find('[name="role"]').val();

        if (field === 'userId') {
            checkUserId(form, role, value);
        } else if (field === 'password') {
            checkPassword(form, role, value);
        } else if (field === 'name') {
            checkName(form, role, value);
        } else if (field === 'email') {
            checkEmail(form, role, value);
        } else if (field === 'phone') {
            checkPhone(form, role, value);
        }
    });

    // 핸드폰 인증 요청 이벤트 리스너 추가
    $('#sendVerificationCodeStudent').on('click', function (e) {
        e.preventDefault();
        const phone = $('[name="phone"]').val();
        if (!isValidPhoneNumber(phone)) {
            showFeedback(null, 'studentPhoneFeedback', '전화번호는 000-0000-0000 형식으로 입력해주세요.', false);
            return;
        }
        $.ajax({
            url: '/api/auth/send-verification-code',
            type: 'POST',
            data: JSON.stringify({ phoneNumber: phone }),
            contentType: 'application/json',
            success: function (response) {
                $('#verificationCodeFieldStudent').show(); // 인증 코드 입력 필드를 보이게 함
                showFeedback(null, 'studentPhoneFeedback', '인증 코드가 전송되었습니다.', true);
            },
            error: function () {
                showFeedback(null, 'studentPhoneFeedback', '인증 코드 전송에 실패했습니다.', false);
            }
        });
    });

    // 학생 폼 제출 시 인증 코드 확인 및 유효성 검사 후 회원가입 진행
    $('#studentRegisterForm').on('submit', function(e) {
        e.preventDefault(); // 폼 제출 막기

        // 유효성 검사 변수
        var form = this;
        var role = $(form).find('[name="role"]').val(); // Form 역할
        var userId = $(this).find('[name="userId"]').val();
        var password = $(this).find('[name="password"]').val();
        var name = $(this).find('[name="name"]').val();
        var email = $(this).find('[name="email"]').val();
        var phone = $(this).find('[name="phone"]').val();
        var verificationCode = $('#studentVerificationCode').val();

        resetFeedback(form);

        var valid = true;

        // 각 필드에 대한 유효성 검사 수행
        if (userId === '') {
            showFeedback(form, `${role}UserIdFeedback`, '아이디를 입력해주세요.', false);
            valid = false;
        } else if (!isValidUserId(userId)) {
            showFeedback(form, `${role}UserIdFeedback`, '아이디는 영어 소문자와 숫자만 사용 가능하며, 4자에서 12자 사이여야 합니다.', false);
            valid = false;
        }

        if (password === '') {
            showFeedback(form, `${role}PasswordFeedback`, '비밀번호를 입력해주세요.', false);
            valid = false;
        } else if (!isValidPassword(password)) {
            showFeedback(form, `${role}PasswordFeedback`, '비밀번호는 최소 8자리 이상이고 특수기호 하나를 포함해야 합니다.', false);
            valid = false;
        }

        if (name === '') {
            showFeedback(form, `${role}NameFeedback`, '이름을 입력해주세요.', false);
            valid = false;
        } else if (!isValidName(name)) {
            showFeedback(form, `${role}NameFeedback`, '이름은 2글자에서 5글자 사이여야 합니다.', false);
            valid = false;
        }

        if (email === '') {
            showFeedback(form, `${role}EmailFeedback`, '이메일을 입력해주세요.', false);
            valid = false;
        } else if (!isValidEmail(email)) {
            showFeedback(form, `${role}EmailFeedback`, '올바른 이메일 형식이 아닙니다.', false);
            valid = false;
        }

        if (phone === '') {
            showFeedback(form, `${role}PhoneFeedback`, '전화번호를 입력해주세요.', false);
            valid = false;
        } else if (!isValidPhoneNumber(phone)) {
            showFeedback(form, `${role}PhoneFeedback`, '전화번호는 000-0000-0000 형식으로 입력해주세요.', false);
            valid = false;
        }

        if (verificationCode === '') {
            showFeedback(null, 'studentVerificationFeedBack', '인증 코드를 입력해주세요.', false);
            valid = false;
        }

        // 유효성 검사 실패 시 폼 전송 중단
        if (!valid) {
            return false; // 폼 전송 중단
        }

        // 서버에 인증 코드 확인 요청
        $.ajax({
            url: '/api/auth/verify-phone',
            type: 'POST',
            data: JSON.stringify({ phoneNumber: phone, verificationCode: verificationCode }),
            contentType: 'application/json',
            success: function (response) {
			    if (response.message === '휴대폰 번호 인증이 완료되었습니다.') {
			        $('#studentRegisterForm')[0].submit(); // 인증이 성공하면 폼을 제출하여 회원가입을 완료합니다.
			    } else {
			        showFeedback(null, 'studentVerificationFeedBack', '인증 번호가 틀립니다.', false); // 인증 실패 시 메시지 표시
			    }
			},

            error: function(xhr, status, error) {
			    console.log("Error status: " + xhr.status); // 상태 코드 출력
			    console.log("Error response: " + xhr.responseText); // 응답 내용 출력
			    console.log("Error message: " + error); // 오류 메시지 출력
			    showFeedback(null, 'studentVerificationFeedBack', '인증 번호가 틀립니다.', false);
			}

        });

        return false; // 폼 전송을 항상 중단합니다.
    });

    // 유효성 검사 및 피드백 표시 함수들
    function checkUserId(form, role, userId) {
        if (!isValidUserId(userId)) {
            showFeedback(form, `${role}UserIdFeedback`, '아이디는 영어 소문자와 숫자만 사용 가능하며, 4자에서 12자 사이여야 합니다.', false);
        } else {
            $.ajax({
                url: '/checkUserId',
                type: 'GET',
                data: { userId: userId },
                success: function(data) {
                    if (!data.available) {
                        showFeedback(form, `${role}UserIdFeedback`, '이미 사용중인 아이디입니다.', false);
                    } else {
                        showFeedback(form, `${role}UserIdFeedback`, '사용 가능한 아이디입니다.', true);
                    }
                }
            });
        }
    }

    function checkPassword(form, role, password) {
        if (!isValidPassword(password)) {
            showFeedback(form, `${role}PasswordFeedback`, '비밀번호는 최소 8자리 이상이고 특수기호 하나를 포함해야 합니다.', false);
        } else {
            showFeedback(form, `${role}PasswordFeedback`, '사용 가능한 비밀번호입니다.', true);
        }
    }

    function checkName(form, role, name) {
        if (!isValidName(name)) {
            showFeedback(form, `${role}NameFeedback`, '이름은 2글자에서 5글자 사이여야 합니다.', false);
        } else {
            showFeedback(form, `${role}NameFeedback`, '사용 가능한 이름입니다.', true);
        }
    }

    function checkEmail(form, role, email) {
        if (!isValidEmail(email)) {
            showFeedback(form, `${role}EmailFeedback`, '올바른 이메일 형식이 아닙니다.', false);
        } else {
            $.ajax({
                url: '/checkEmail',
                type: 'GET',
                data: { email: email },
                success: function(data) {
                    if (!data.available) {
                        showFeedback(form, `${role}EmailFeedback`, '이미 사용중인 이메일입니다.', false);
                    } else {
                        showFeedback(form, `${role}EmailFeedback`, '사용 가능한 이메일입니다.', true);
                    }
                }
            });
        }
    }

    function checkPhone(form, role, phone) {
        if (!isValidPhoneNumber(phone)) {
            showFeedback(form, `${role}PhoneFeedback`, '전화번호는 000-0000-0000 형식으로 입력해주세요.', false);
        } else {
            $.ajax({
                url: '/checkPhone',
                type: 'GET',
                data: { phone: phone },
                success: function(data) {
                    if (!data.available) {
                        showFeedback(form, `${role}PhoneFeedback`, '이미 사용중인 전화번호입니다.', false);
                    } else {
                        showFeedback(form, `${role}PhoneFeedback`, '사용 가능한 전화번호입니다.', true);
                    }
                }
            });
        }
    }

    // 유효성 검사 함수들
    function isValidUserId(userId) {
        var re = /^[a-z0-9]{4,12}$/;
        return re.test(userId);
    }

    function isValidPassword(password) {
        var re = /^(?=.*[!@#$%^&*(),.?":{}|<>]).{8,}$/;
        return re.test(password);
    }

    function isValidName(name) {
        return name.length >= 2 && name.length <= 5;
    }

    function isValidEmail(email) {
        var re = /\S+@\S+\.\S+/;
        return re.test(email);
    }

    function isValidPhoneNumber(phone) {
        var re = /^\d{3}-\d{4}-\d{4}$/;
        return re.test(phone);
    }

    // 피드백 표시 함수
    function showFeedback(form, feedbackElementId, message, isValid) {
	    var feedbackElement = $(`#${feedbackElementId}`); // 정확하게 요소를 선택하도록 수정
	    feedbackElement.text(message);
	    if (isValid) {
	        feedbackElement.removeClass('text-danger shake').addClass('text-success fadeIn');
	    } else {
	        feedbackElement.removeClass('text-success fadeIn').addClass('text-danger shake');
	    }
	    feedbackElement.show();
	}


    // 피드백 리셋 함수
    function resetFeedback(form) {
        $(form).find('.form-text').text('').removeClass('text-success text-danger shake fadeIn').hide();
    }
});
