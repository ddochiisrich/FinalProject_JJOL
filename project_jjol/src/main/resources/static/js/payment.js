$(function() {
	// 결제 정보 추출(프로토타입용)
	const lectureName = $("#lecture0").text();
	const price = parseInt($("#price0").text(), 10);
	const lectureId = parseInt($("#lectureId0").val(), 10);
	
	// 결제
	$("#payBt").on("click", async function() {
		
		// 검증 요청
		const response = await fetch('/api/payment/validate', {
			method: 'POST',
			headers: {
				'Content-Type': 'application/x-www-form-urlencoded'
			},
			body: new URLSearchParams({
				lectureIds: lectureId,
				totalAmout: price
			})
		})
		
		const isValid = await response.json();
		
		if (isValid) {
			// 결제 요청
			const paymentResponse = await PortOne.requestPayment({
				storeId: "store-a122dc6d-cdc8-4bd1-aa02-53489e0d886d",
				channelKey: "channel-key-e373e32c-673e-47a0-acb3-e2d0b096bc6e",
				paymentId: `payment-${crypto.randomUUID()}`,
				orderName: lectureName,
				totalAmount: price,
				lectureIds: lectureId,
				currency: "CURRENCY_KRW",
				payMethod: "EASY_PAY"
			})
			
			// 결제 완료 처리
		} else {
			alert('결제 정보가 유효하지 않습니다.');
		}
	})
})