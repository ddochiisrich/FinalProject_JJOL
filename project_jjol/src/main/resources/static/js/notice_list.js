document.addEventListener("DOMContentLoaded", function() {
    const noticeTab = document.getElementById('notice-tab');
    const faqTab = document.getElementById('faq-tab');
    const pageTitle = document.getElementById('page-title');
    const noticeSection = document.getElementById('notice-section');
    const faqSection = document.getElementById('faq-section');

    noticeTab.addEventListener('click', function() {
        pageTitle.textContent = '공지사항';
        noticeTab.classList.add('active');
        faqTab.classList.remove('active');
        noticeSection.style.display = 'block';
        faqSection.style.display = 'none';
    });

    faqTab.addEventListener('click', function() {
        pageTitle.textContent = '자주 묻는 질문';
        faqTab.classList.add('active');
        noticeTab.classList.remove('active');
        noticeSection.style.display = 'none';
        faqSection.style.display = 'block';
    });
});

function toggleAnswer(element) {
    const answer = element.nextElementSibling;
    answer.style.display = (answer.style.display === 'none' || answer.style.display === '') ? 'block' : 'none';
}
