$(function() {
	// 결제 정보 추출(프로토타입용)
	const lectureName = $("#lecture0").text();
	const price = parseInt($("#price0").text(), 10);
	const lectureId = parseInt($("#lectureId0").val(), 10);
	const userId = $("#userId").val();
	
	// 결제
	$("#payBt").on("click", async function() {
		
		const merchantUid = `merchant_${crypto.randomUUID()}`; 
		
		IMP.init("imp62227326");
		IMP.request_pay({
			pg: "kakaopay",
			pay_method: "kakaopay",
			merchant_uid: merchantUid,
			name: lectureName,
			amount: price,
			buyer_name: userId,
			lecture_id: lectureId
		}, function (rsp) {
			if (rsp.success) {
				console.log(rsp);
				console.log(userId);
				var data = "price=" + price + "&userId=" + userId + "&lectureId="+lectureId;
				// db 저장 위해 서버(controller)로 데이터 전송
				$.ajax({
					url: '/addPayment',
					type: 'POST',
					dataType: 'json',
					contentType: 'application/json',
					data: data,
					success: function() {
						alert('ajax 작동');
					},
					error: function() {
						alert('ajax 에러');
					}
				}).done(function(data) {
					if(rsp.paid_amount === data.response.amount) { // paid_amount를 수정해야 함.
						alert("결제 성공");
					} else {
						alert("결제 실패");
					}
				})
				
			} else if (rsp.success == false) {
				alert("결제 실패: " + rsp.error_msg);
			}
		})
	});
})