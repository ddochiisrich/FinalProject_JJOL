$(document).ready(function () {
    // 로컬 스토리지에서 활성 탭 상태를 가져옴
    var activeTab = localStorage.getItem('activeTab');
    if (activeTab) {
        $('#lectureDetailTab a[href="' + activeTab + '"]').tab('show');
    } else {
        $('#lectureDetailTab a[href="#lectureDetail-description"]').tab('show');
    }

    // 탭 클릭 시 활성 탭 상태를 로컬 스토리지에 저장
    $('#lectureDetailTab a').on('click', function (e) {
        e.preventDefault();
        var tabId = $(this).attr('href');
        localStorage.setItem('activeTab', tabId);
        $(this).tab('show');
    });

    // 공유하기 버튼 클릭 이벤트
    $('#lectureDetailShareButton').on('click', function() {
        var dummy = $('<input>').val(window.location.href).appendTo('body').select();
        document.execCommand('copy');
        dummy.remove();
        
        var $button = $(this);
        $button.text('복사완료');
        $button.addClass('lectureDetail-btn-share copied');
        setTimeout(function() {
            $button.text('공유하기');
            $button.removeClass('lectureDetail-btn-share copied');
        }, 2000); // 2초 후에 원래 상태로 돌아옴
    });
});
