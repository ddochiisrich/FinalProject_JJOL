$(document).ready(function() {
    let chapterIndex = 1;
    $('#addChapterUpload').off('click').on('click', function() {
        chapterIndex++;
        const chapterTemplate = `
            <div class="upload-chapter-upload">
                <h3>챕터 ${chapterIndex}</h3>
                <div class="upload-form-group-upload form-group">
                    <label for="chapterTitleUpload${chapterIndex}">챕터 제목 :</label>
                    <input type="text" id="chapterTitleUpload${chapterIndex}" name="chapterTitles" class="form-control">
                    <div class="error-message-upload" id="errorChapterTitleUpload${chapterIndex}"></div>
                </div>
                <div class="upload-form-group-upload form-group">
                    <label for="chapterDescriptionUpload${chapterIndex}">챕터 설명 :</label>
                    <input type="text" id="chapterDescriptionUpload${chapterIndex}" name="chapterDescriptions" class="form-control">
                    <div class="error-message-upload" id="errorChapterDescriptionUpload${chapterIndex}"></div>
                </div>
                <div class="upload-form-group-upload form-group">
                    <label for="chapterFileUpload${chapterIndex}">챕터 비디오 :</label>
                    <input type="file" id="chapterFileUpload${chapterIndex}" name="chapterFiles" class="form-control-file">
                    <div class="error-message-upload" id="errorChapterFileUpload${chapterIndex}"></div>
                </div>
                <div class="upload-form-group-upload form-group">
                    <label for="chapterOrderUpload${chapterIndex}">챕터 순서 :</label>
                    <input type="number" id="chapterOrderUpload${chapterIndex}" name="chapterOrders" class="form-control">
                    <div class="error-message-upload" id="errorChapterOrderUpload${chapterIndex}"></div>
                </div>
                <hr>
            </div>
        `;
        $('#chapter-container-upload').append(chapterTemplate);
    });

    $('#uploadFormUpload').off('submit').on('submit', function(e) {
        let isValid = true;
        let firstInvalidElement = null;

        function showError(element, message) {
            const errorElement = $(element).next('.error-message-upload');
            errorElement.text(message);
            errorElement.addClass('show');
            if (isValid) firstInvalidElement = $(element);
            isValid = false;
        }

        function hideError(element) {
            const errorElement = $(element).next('.error-message-upload');
            errorElement.removeClass('show');
        }

        if ($('#titleUpload').val() === '') {
            showError('#titleUpload', '강의 제목을 입력해주세요.');
        } else {
            hideError('#titleUpload');
        }

        if ($('#shortDescriptionUpload').val() === '') {
            showError('#shortDescriptionUpload', '간략 설명을 입력해주세요.');
        } else {
            hideError('#shortDescriptionUpload');
        }

        if ($('#longDescriptionUpload').val() === '') {
            showError('#longDescriptionUpload', '상세 설명을 입력해주세요.');
        } else {
            hideError('#longDescriptionUpload');
        }

        if ($('#thumbnailVideoUpload').val() === '') {
            showError('#thumbnailVideoUpload', '썸네일 비디오를 선택해주세요.');
        } else {
            hideError('#thumbnailVideoUpload');
        }

        if ($('#thumbnailImageUpload').val() === '') {
            showError('#thumbnailImageUpload', '썸네일 이미지를 선택해주세요.');
        } else {
            hideError('#thumbnailImageUpload');
        }

        if ($('#levelUpload').val() === '') {
            showError('#levelUpload', '레벨을 선택해주세요.');
        } else {
            hideError('#levelUpload');
        }

        if ($('#priceUpload').val() === '') {
            showError('#priceUpload', '가격을 입력해주세요.');
        } else {
            hideError('#priceUpload');
        }

        if ($('#discountUpload').val() === '') {
            showError('#discountUpload', '할인율을 입력해주세요.');
        } else {
            hideError('#discountUpload');
        }

        for (let i = 1; i <= chapterIndex; i++) {
            if ($(`#chapterTitleUpload${i}`).val() === '') {
                showError(`#chapterTitleUpload${i}`, '챕터 제목을 입력해주세요.');
            } else {
                hideError(`#chapterTitleUpload${i}`);
            }

            if ($(`#chapterDescriptionUpload${i}`).val() === '') {
                showError(`#chapterDescriptionUpload${i}`, '챕터 설명을 입력해주세요.');
            } else {
                hideError(`#chapterDescriptionUpload${i}`);
            }

            if ($(`#chapterFileUpload${i}`).val() === '') {
                showError(`#chapterFileUpload${i}`, '챕터 비디오를 선택해주세요.');
            } else {
                hideError(`#chapterFileUpload${i}`);
            }

            if ($(`#chapterOrderUpload${i}`).val() === '') {
                showError(`#chapterOrderUpload${i}`, '챕터 순서를 입력해주세요.');
            } else {
                hideError(`#chapterOrderUpload${i}`);
            }
        }

        if (!isValid) {
            e.preventDefault();
            firstInvalidElement.focus();
        }
    });
});
