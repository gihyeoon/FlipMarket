
$(document).ready(function() {
	const $dropdown = $('#mypageDropdown');
	const $btn = $('#mypageBtn');

	// 마이페이지 버튼 클릭 시 드롭다운 토글
	$btn.on('click', function(e) {
		e.stopPropagation();

		console.log("마이페이지 버튼 클릭 이벤트 발생");

		if ($dropdown.hasClass('hidden')) {
			$dropdown.removeClass('hidden');
			setTimeout(function() {
				$dropdown.removeClass('opacity-0 scale-95')
					.addClass('opacity-100 scale-100');
			}, 10);
		} else {
			$dropdown.removeClass('opacity-100 scale-100')
				.addClass('opacity-0 scale-95');
			setTimeout(function() {
				$dropdown.addClass('hidden');
			}, 200);
		}
	});

	// 외부 클릭 시 드롭다운 닫기
	$(document).on('click', function(e) {
		// 드롭다운 안을 클릭한 경우는 제외
		if (!$(e.target).closest('#mypageDropdown').length &&
			!$(e.target).is('#mypageBtn')) {

			console.log("아무곳 클릭 중");

			if (!$dropdown.hasClass('hidden')) {
				$dropdown.removeClass('opacity-100 scale-100')
					.addClass('opacity-0 scale-95');
				setTimeout(function() {
					$dropdown.addClass('hidden');
				}, 200);
			}
		}
	});
});
