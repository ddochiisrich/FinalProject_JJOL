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
	
	// 포인트 사용
	var havePoint = parseInt($("#havePoint").text());
	var priceAfterPoint = parseInt($("#priceAfterPoint").text());
	$("#earningPoint").text(Math.ceil(priceAfterPoint * 5 / 100));
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
		
		$("#earningPoint").text(Math.ceil(parseInt($("#priceAfterPoint").text()) * 5 / 100));
	})
	
	// 포인트 전액 사용 버튼
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
		
		$("#earningPoint").text(Math.ceil(parseInt($("#priceAfterPoint").text()) * 5 / 100));
	});
	
	// 결제
	const userId = $("#userId").val();
	const lectureTitle = $("#lectureTitle").text();
	const lectureId = $("#lectureId").val();
	
	$("#payBt").on("click", async function() {
		var havePoint = parseInt($("#havePoint").text());
		var usingPoint = parseInt($("#pointInput").val());
		const lecturePriceAfterPoint = parseInt($('#priceAfterPoint').text());
		const uuid = crypto.randomUUID();
		const merchantUid = "merchant_" + uuid;
		
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
		}
		
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
								$('#lectureAppleyForm').submit();
					        },
					        error: async function(xhr, status, error) {
					            console.error("결제 실패", xhr.responseText, status, error);
					        }
						})
					}
				})
	        },
	        error: async function(xhr, status, error) {
				alert('비정상적인 접근입니다.');
	            console.error("검증 결과: 불일치", xhr.responseText, status, error);
	        }
	    });
	});
})