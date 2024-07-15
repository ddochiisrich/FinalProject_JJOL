document.addEventListener('DOMContentLoaded', function () {
    const forms = document.querySelectorAll('.needs-validation');
    Array.prototype.slice.call(forms)
        .forEach(function (form) {
            form.addEventListener('submit', function (event) {
                if (!form.checkValidity()) {
                    event.preventDefault();
                    event.stopPropagation();
                }
                form.classList.add('was-validated');
            }, false);
        });
});

function notification_form_confirmDeletion() {
    return confirm("정말 이 알림을 삭제하시겠습니까?");
}

function notification_form_openUpdateModal(button) {
    const id = button.getAttribute('data-id');
    const subject = button.getAttribute('data-subject');
    const examDate = button.getAttribute('data-examdate');

    document.getElementById('notification_form_updateNotificationId').value = id;
    document.getElementById('notification_form_updateSubject').value = subject;
    document.getElementById('notification_form_updateExamDate').value = examDate;

    $('#notification_form_updateModal').modal('show');
}

function notification_form_openCreateModal() {
    document.getElementById('notification_form_subject').value = '';
    document.getElementById('notification_form_examDate').value = '';
    $('#notification_form_createModal').modal('show');
}

function notification_form_changeSorting() {
    var sortOption = document.getElementById('notification_form_sortSelect').value;
    var url = '/notifications/form';
    if (sortOption !== 'default') {
        url += '?sort=' + sortOption;
    }
    window.location.href = url;
}

function notification_form_goToMainPage() {
    window.location.href = '/lectures';
}

function notification_form_closeCreateModal() {
    $('#notification_form_createModal').modal('hide');
}

function notification_form_closeUpdateModal() {
    $('#notification_form_updateModal').modal('hide');
}
