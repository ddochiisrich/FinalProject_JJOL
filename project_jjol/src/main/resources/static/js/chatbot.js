$(function() {
    // chatbotButton 클릭 이벤트 처리
    $("#chatbotButton").click(function() {
        // chatbotFrameContainer 요소 가져오기
        var chatbotFrameContainer = $("#chatbotFrameContainer");

        // chatbotFrameContainer의 가시성 토글
        if (chatbotFrameContainer.hasClass("visible")) {
            chatbotFrameContainer.removeClass("visible");
        } else {
            chatbotFrameContainer.addClass("visible");
        }
    });
});