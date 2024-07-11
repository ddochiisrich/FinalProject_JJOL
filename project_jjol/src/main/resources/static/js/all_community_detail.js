$(function () {
    $("#allcommunityDelete").on("click", function () {
        $("#checkForm").attr("action", "/deleteAllCommunity");
        $("#checkForm").attr("method", "post");
        $("#checkForm").submit();
    });

    $("#allcommunityUpdateForm").on("click", function () {
        var allcNo = $("input[name='allcNo']").val(); // 데이터 번호 가져오기
        // 수정하기 폼으로 이동
        window.location.href = '/updateAllCommunityForm?no=' + allcNo; // 수정 폼의 URL
    });
});
