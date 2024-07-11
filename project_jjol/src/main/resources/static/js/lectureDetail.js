$(document).ready(function () {
    // 로컬 스토리지에서 활성 탭 상태를 가져옴
    var activeTab = localStorage.getItem('activeTab');
    if (activeTab) {
        $('#myTab a[href="' + activeTab + '"]').tab('show');
    } else {
        $('#myTab a[href="#description"]').tab('show');
    }

    // 탭 클릭 시 활성 탭 상태를 로컬 스토리지에 저장
    $('#myTab a').on('click', function (e) {
        e.preventDefault();
        var tabId = $(this).attr('href');
        localStorage.setItem('activeTab', tabId);
        $(this).tab('show');
    });

    // 공유하기 버튼 클릭 이벤트
    $('#shareButton').on('click', function() {
        var dummy = $('<input>').val(window.location.href).appendTo('body').select();
        document.execCommand('copy');
        dummy.remove();
        
        var $button = $(this);
        $button.addClass('copied');
        $('#shareMessage').show();

        setTimeout(function() {
            $button.removeClass('copied');
            $('#shareMessage').hide();
        }, 1000);
    });
});
