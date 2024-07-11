$(document).ready(function() {
    let chapterIndex = 1;
    $('#addChapter').click(function() {
        chapterIndex++;
        const chapterTemplate = `
            <div class="chapter">
                <h3>챕터 ${chapterIndex}</h3>
                <div class="form-group">
                    <label for="chapterTitle${chapterIndex}">챕터 제목 :</label>
                    <input type="text" id="chapterTitle${chapterIndex}" name="chapterTitles" class="form-control">
                    <div class="error-message" id="errorChapterTitle${chapterIndex}"></div>
                </div>
                <div class="form-group">
                    <label for="chapterDescription${chapterIndex}">챕터 설명 :</label>
                    <input type="text" id="chapterDescription${chapterIndex}" name="chapterDescriptions" class="form-control">
                    <div class="error-message" id="errorChapterDescription${chapterIndex}"></div>
                </div>
                <div class="form-group">
                    <label for="chapterFile${chapterIndex}">챕터 비디오 :</label>
                    <input type="file" id="chapterFile${chapterIndex}" name="chapterFiles" class="form-control-file">
                    <div class="error-message" id="errorChapterFile${chapterIndex}"></div>
                </div>
                <div class="form-group">
                    <label for="chapterOrder${chapterIndex}">챕터 순서 :</label>
                    <input type="number" id="chapterOrder${chapterIndex}" name="chapterOrders" class="form-control">
                    <div class="error-message" id="errorChapterOrder${chapterIndex}"></div>
                </div>
                <hr>
            </div>
        `;
        $('#chapter-container').append(chapterTemplate);
    });

    $('#uploadForm').submit(function(e) {
        let isValid = true;
        let firstInvalidElement = null;

        function showError(element, message) {
            const errorElement = $(element).next('.error-message');
            errorElement.text(message);
            errorElement.addClass('show');
            if (isValid) firstInvalidElement = $(element);
            isValid = false;
        }

        function hideError(element) {
            const errorElement = $(element).next('.error-message');
            errorElement.removeClass('show');
        }

        if ($('#title').val() === '') {
            showError('#title', '강의 제목을 입력해주세요.');
        } else {
            hideError('#title');
        }

        if ($('#shortDescription').val() === '') {
            showError('#shortDescription', '간략 설명을 입력해주세요.');
        } else {
            hideError('#shortDescription');
        }

        if ($('#longDescription').val() === '') {
            showError('#longDescription', '상세 설명을 입력해주세요.');
        } else {
            hideError('#longDescription');
        }

        if ($('#thumbnailVideo').val() === '') {
            showError('#thumbnailVideo', '썸네일 비디오를 선택해주세요.');
        } else {
            hideError('#thumbnailVideo');
        }

        if ($('#thumbnailImage').val() === '') {
            showError('#thumbnailImage', '썸네일 이미지를 선택해주세요.');
        } else {
            hideError('#thumbnailImage');
        }

        if ($('#level').val() === '') {
            showError('#level', '레벨을 선택해주세요.');
        } else {
            hideError('#level');
        }

        if ($('#price').val() === '') {
            showError('#price', '가격을 입력해주세요.');
        } else {
            hideError('#price');
        }

        if ($('#discount').val() === '') {
            showError('#discount', '할인율을 입력해주세요.');
        } else {
            hideError('#discount');
        }

        for (let i = 1; i <= chapterIndex; i++) {
            if ($(`#chapterTitle${i}`).val() === '') {
                showError(`#chapterTitle${i}`, '챕터 제목을 입력해주세요.');
            } else {
                hideError(`#chapterTitle${i}`);
            }

            if ($(`#chapterDescription${i}`).val() === '') {
                showError(`#chapterDescription${i}`, '챕터 설명을 입력해주세요.');
            } else {
                hideError(`#chapterDescription${i}`);
            }

            if ($(`#chapterFile${i}`).val() === '') {
                showError(`#chapterFile${i}`, '챕터 비디오를 선택해주세요.');
            } else {
                hideError(`#chapterFile${i}`);
            }

            if ($(`#chapterOrder${i}`).val() === '') {
                showError(`#chapterOrder${i}`, '챕터 순서를 입력해주세요.');
            } else {
                hideError(`#chapterOrder${i}`);
            }
        }

        if (!isValid) {
            e.preventDefault();
            firstInvalidElement.focus();
        }
    });
});
