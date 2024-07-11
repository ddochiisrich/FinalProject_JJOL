$(document).ready(function() {
    var chapters = document.getElementsByClassName('lecture-update-chapter');
    var numberOfChapters = chapters.length;
    let chapterIndex = numberOfChapters;
    $('#addChapter').click(function() {
        chapterIndex++;
        const chapterTemplate = `
            <div class="lecture-update-chapter">
                <h3>챕터 ${chapterIndex}</h3>
                <div class="form-group">
                    <label for="chapterTitle${chapterIndex}">챕터 제목 :</label>
                    <input type="text" id="chapterTitle${chapterIndex}" name="chapterTitles" class="form-control">
                </div>
                <div class="form-group">
                    <label for="chapterDescription${chapterIndex}">챕터 설명 :</label>
                    <input type="text" id="chapterDescription${chapterIndex}" name="chapterDescriptions" class="form-control">
                </div>
                <div class="form-group">
                    <label for="chapterFile${chapterIndex}">챕터 비디오 :</label>
                    <input type="file" id="chapterFile${chapterIndex}" name="chapterFiles" class="form-control-file">
                </div>
                <div class="form-group">
                    <label for="chapterOrder${chapterIndex}">챕터 순서 :</label>
                    <input type="number" id="chapterOrder${chapterIndex}" name="chapterOrders" class="form-control">
                </div>
                <hr>
            </div>
        `;
        $('#lecture-update-chapter-container').append(chapterTemplate);
    });
});
