$(function() {
	
	// 할인 적용
	var lectureDiscountText = $("#lectureDiscount").text();
	var discountMatch = lectureDiscountText.match(/\d+/); // 숫자 추출
	var discountRate = parseInt(discountMatch[0]);
		
	function discount(price) {
		const discountPrice = price - (price * discountRate / 100);
		return discountPrice;
	}
	
	var lecturePriceText = $(".lecturePrice").text();
	var priceMatch = lecturePriceText.match(/\d+/); // 숫자 추출
	var originalLecturePrice = parseInt(priceMatch[0]);
	var lecturePrice = discount(originalLecturePrice);
	$(".lecturePrice").text('가격: ' + lecturePrice + '원');
	
	$("#lecturePrice").text(lecturePrice);
	$("#priceAfterPoint").text(lecturePrice);
	
	//포인트 사용
	var havePoint = parseInt($("#havePoint").text());
	var priceAfterPoint = parseInt($("#priceAfterPoint").text());
	$("#pointInput").on("keyup change", function() {
		if (lecturePrice > havePoint) {
	    	$("#pointInput").attr("max", havePoint);
		} else {
			$("#pointInput").attr("max", lecturePrice);
		}
		
		var usingPoint = parseInt($("#pointInput").val());
		
		if (usingPoint > havePoint) {
			$("#pointInput").val(havePoint);
		}
		if (usingPoint > lecturePrice) {
			$("#pointInput").val(lecturePrice);
		}
		
		$("#havePoint").text(havePoint - usingPoint);
		
		$("#priceAfterPoint").text(priceAfterPoint - usingPoint);
	})
	
	// 전액 사용 버튼
	$("#useAllPoint").on("click", function() {
		if (lecturePrice > havePoint) {
	    	$("#pointInput").attr("max", havePoint);
			$("#pointInput").val(havePoint);
		} else {
			$("#pointInput").attr("max", lecturePrice);
			$("#pointInput").val(lecturePrice);
		}
		var usingPoint = parseInt($("#pointInput").val());
		$("#havePoint").text(havePoint - usingPoint);
		
		$("#priceAfterPoint").text(priceAfterPoint - usingPoint);
	});
	
	// 결제
	const userId = $("#userId").val();
	const lectureTitle = $("#lectureTitle").text();
	const lectureId = $("#lectureId").val();
	
	$("#payBt").on("click", function() {
		var havePoint = parseInt($("#havePoint").text());
		const lecturePriceAfterPoint = parseInt($('#priceAfterPoint').text());
		const merchantUid = `merchant_${crypto.randomUUID()}`;
		
		var usingPoint = parseInt($("#pointInput").val());
		alert('usingPoint: ' + usingPoint);
		alert('lecturePrice : ' + lecturePriceAfterPoint);
		
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
				const dataToSend = {
			        userId: userId,
			        lectureTitle: lectureTitle,
			        lectureId: lectureId,
			        lecturePrice: lecturePriceAfterPoint,
					point: havePoint,
					usingPoint: usingPoint
			    };
				
				// 서버(controller)에 정보 전달
				$.ajax({
			        type: "POST",
			        url: "/addPayment",
					contentType: "application/json",
			        data: JSON.stringify(dataToSend),
			        dataType: "json",
			        success: function() {
						alert('controller에 전송 성공');
						// 결제 완료 후 수강신청 페이지로 이동						
			            window.location.href = '/lectures/apply/' + lectureId;
			        },
			        error: function(xhr, status, error) {
						alert('controller에 전송 실패');
			            console.error("Controller에 데이터 전송 실패", xhr.responseText, status, error);
			        }
			    });
			}
		})
	});
})