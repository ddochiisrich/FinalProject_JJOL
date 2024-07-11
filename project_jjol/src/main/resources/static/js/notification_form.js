function confirmDeletion() {
    return confirm("정말 이 알림을 삭제하시겠습니까?");
}

function openUpdateModal(button) {
    const id = button.getAttribute('data-id');
    const subject = button.getAttribute('data-subject');
    const examDate = button.getAttribute('data-examdate');

    document.getElementById('updateNotificationId').value = id;
    document.getElementById('updateSubject').value = subject;
    document.getElementById('updateExamDate').value = examDate;
    
    document.getElementById('notificationId').value = id;

    $('#updateModal').modal('show');
}

function changeSorting() {
    var sortOption = document.getElementById('sortSelect').value;
    var url = '/notifications/form';
    if (sortOption !== 'default') {
        url += '?sort=' + sortOption;
    }
    window.location.href = url;
}

function goToMainPage() {
    window.location.href = '/lectures';
}
