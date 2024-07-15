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
        var feedbackElement = $(form).find(`#${feedbackElementId}`);
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

    // 폼 제출 시 유효성 검사
    $('#studentRegisterForm, #instructorRegisterForm').on('submit', function(e) {
        var form = this;
        var role = $(form).find('[name="role"]').val(); // Form 역할
        var userId = $(this).find('[name="userId"]').val();
        var password = $(this).find('[name="password"]').val();
        var name = $(this).find('[name="name"]').val();
        var email = $(this).find('[name="email"]').val();
        var phone = $(this).find('[name="phone"]').val();

        resetFeedback(form);

        var valid = true;

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

        if (!valid) {
            e.preventDefault(); // 폼 전송 막기
        } else {
            e.preventDefault(); // 폼 전송 막기
            $.ajax({
                url: '/checkUserId',
                type: 'GET',
                data: { userId: userId },
                async: false,
                success: function(data) {
                    if (!data.available) {
                        showFeedback(form, `${role}UserIdFeedback`, '이미 사용중인 아이디입니다.', false);
                        valid = false;
                    } else {
                        showFeedback(form, `${role}UserIdFeedback`, '사용 가능한 아이디입니다.', true);
                    }
                }
            });

            $.ajax({
                url: '/checkEmail',
                type: 'GET',
                data: { email: email },
                async: false,
                success: function(data) {
                    if (!data.available) {
                        showFeedback(form, `${role}EmailFeedback`, '이미 사용중인 이메일입니다.', false);
                        valid = false;
                    } else {
                        showFeedback(form, `${role}EmailFeedback`, '사용 가능한 이메일입니다.', true);
                    }
                }
            });

            $.ajax({
                url: '/checkPhone',
                type: 'GET',
                data: { phone: phone },
                async: false,
                success: function(data) {
                    if (!data.available) {
                        showFeedback(form, `${role}PhoneFeedback`, '이미 사용중인 전화번호입니다.', false);
                        valid = false;
                    } else {
                        showFeedback(form, `${role}PhoneFeedback`, '사용 가능한 전화번호입니다.', true);
                    }
                }
            });

            if (valid) {
                form.submit();
            }
        }
    });
});
