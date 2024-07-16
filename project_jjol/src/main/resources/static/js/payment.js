function generateUUID() {
    // crypto.getRandomValues를 사용하여 UUID를 생성하는 함수
    const cryptoObj = window.crypto || window.msCrypto; // 인터넷 익스플로러 지원
    const buf = new Uint8Array(16);
    cryptoObj.getRandomValues(buf);
    const S4 = () => {
        return (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1);
    };
    return (S4() + S4() + "-" + S4() + "-" + S4() + "-" + S4() + "-" + S4() + S4() + S4());
}

$(function() {
    // 할인 적용
    var lectureDiscountText = $("#lectureDetailDiscount").text();
    var discountMatch = lectureDiscountText.match(/\d+/); // 숫자 추출
    var discountRate = parseInt(discountMatch[0]);
        
    function discount(price) {
        const discountPrice = price - (price * discountRate / 100);
        return discountPrice;
    }
    
    var lecturePriceText = $(".lectureDetailPrice").text();
    var priceMatch = lecturePriceText.match(/\d+/); // 숫자 추출
    var originalLecturePrice = parseInt(priceMatch[0]);
    var lecturePrice = discount(originalLecturePrice);
    $(".lectureDetailPrice").text('가격: ' + lecturePrice + '원');
    
    $("#lectureDetailPrice").text(lecturePrice);
    $("#lectureDetailPriceAfterPoint").text(lecturePrice);
    
    // 포인트 사용
    var havePoint = parseInt($("#lectureDetailHavePoint").text());
    var priceAfterPoint = parseInt($("#lectureDetailPriceAfterPoint").text());
    $("#lectureDetailEarningPoint").text(Math.ceil(priceAfterPoint * 5 / 100));
    $("#lectureDetailPointInput").on("keyup change", function() {
        if (lecturePrice > havePoint) {
            $("#lectureDetailPointInput").attr("max", havePoint);
        } else {
            $("#lectureDetailPointInput").attr("max", lecturePrice);
        }
        
        var usingPoint = parseInt($("#lectureDetailPointInput").val());
        
        if (usingPoint > havePoint) {
            $("#lectureDetailPointInput").val(havePoint);
        }
        if (usingPoint > lecturePrice) {
            $("#lectureDetailPointInput").val(lecturePrice);
        }
        
        $("#lectureDetailHavePoint").text(havePoint - usingPoint);
        
        $("#lectureDetailPriceAfterPoint").text(priceAfterPoint - usingPoint);
        
        $("#lectureDetailEarningPoint").text(Math.ceil(parseInt($("#lectureDetailPriceAfterPoint").text()) * 5 / 100));
    });
    
    // 포인트 전액 사용 버튼
    $("#lectureDetailUseAllPoint").on("click", function() {
        if (lecturePrice > havePoint) {
            $("#lectureDetailPointInput").attr("max", havePoint);
            $("#lectureDetailPointInput").val(havePoint);
        } else {
            $("#lectureDetailPointInput").attr("max", lecturePrice);
            $("#lectureDetailPointInput").val(lecturePrice);
        }
        var usingPoint = parseInt($("#lectureDetailPointInput").val());
        $("#lectureDetailHavePoint").text(havePoint - usingPoint);
        
        $("#lectureDetailPriceAfterPoint").text(priceAfterPoint - usingPoint);
        
        $("#lectureDetailEarningPoint").text(Math.ceil(parseInt($("#lectureDetailPriceAfterPoint").text()) * 5 / 100));
    });
    
    // 결제
    const userId = $("#lectureDetailUserId").val();
    const lectureTitle = $("#lectureDetailTitle").text();
    const lectureId = $("#lectureDetailLectureId").val();
    
    $("#lectureDetailPayBt").on("click", async function() {
        var havePoint = parseInt($("#lectureDetailHavePoint").text());
        var usingPoint = parseInt($("#lectureDetailPointInput").val());
        const lecturePriceAfterPoint = parseInt($('#lectureDetailPriceAfterPoint').text());
        const merchantUid = `merchant_${generateUUID()}`;
        
        const dataForValidate = {
            userId: userId,
            lectureId: lectureId,
            lecturePrice: lecturePriceAfterPoint,
            usingPoint: usingPoint,
        };
        
        const dataForAddPayment = {
            userId: userId,
            lectureId: lectureId,
            lectureTitle: lectureTitle,
            lecturePrice: lecturePriceAfterPoint,
            point: havePoint,
            merchantUid: merchantUid
        };
        
        // 검증
        await $.ajax({
            type: "POST",
            url: "/validatePayment",
            contentType: "application/json",
            data: JSON.stringify(dataForValidate),
            dataType: "json",
            success: function() {
                
                // 결제
                IMP.init("imp62227326");
                IMP.request_pay({
                    pg: "kakaopay",
                    pay_method: "kakaopay",
                    merchant_uid: merchantUid,
                    name: lectureTitle,
                    amount: lecturePriceAfterPoint,
                    buyer_name: userId,
                    lecture_id: lectureId
                }, function (rsp) {
                    if(rsp.success) {
                        // DB 반영        
                        $.ajax({
                            type: "POST",
                            url: "/addPayment",
                            contentType: "application/json",
                            data: JSON.stringify(dataForAddPayment),
                            dataType: "json",
                            success: function() {
                                console.log('결제 성공');
                                // 수강신청                        
                                $('#lectureDetailAppleyForm').submit();
                            },
                            error: async function(xhr, status, error) {
                                console.error("결제 실패", xhr.responseText, status, error);
                            }
                        });
                    }
                });
            },
            error: async function(xhr, status, error) {
                alert('비정상적인 접근입니다.');
                console.error("검증 결과: 불일치", xhr.responseText, status, error);
            }
        });
    });
});
