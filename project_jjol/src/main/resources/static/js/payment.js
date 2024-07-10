$(function() {
	
	// 포인트 사용
	var lecturePrice = parseInt($("#lecturePrice").text());
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
	const lectureId = parseInt($("#lectureId").val());
	
	$("#payBt").on("click", function() {
		const lecturePirceAfterPoint = parseInt($('#priceAfterPoint').text());
		const merchantUid = `merchant_${crypto.randomUUID()}`; 
		
		IMP.init("imp62227326");
		IMP.request_pay({
			pg: "kakaopay",
			pay_method: "kakaopay",
			merchant_uid: merchantUid,
			name: lectureTitle,
			amount: lecturePirceAfterPoint,
			buyer_name: userId,
			lecture_id: lectureId
		}, function (rsp) {
			if(rsp.success) {
				alert('결제성공!');
				const dataToSend = {
			        userId: userId,
			        lectureTitle: lectureTitle,
			        lectureId: lectureId,
			        lecturePrice: lecturePirceAfterPoint
			    };
				
				$.ajax({
			        type: "POST",
			        url: "/addPayment",
					contentType: "application/json",
			        data: JSON.stringify(dataToSend),
			        dataType: "json",
			        success: function(response) {
			            alert('Controller에 데이터 전송 성공');
			            console.log(response);
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