document.addEventListener('DOMContentLoaded', function () {
    const mainMenuSelectors = document.querySelectorAll('.data-sharing-view-main-menu-selector-wrapper');
    const subMenus = document.querySelectorAll('.data-sharing-view-sub-menu');
    const subMenuLinks = document.querySelectorAll('.data-sharing-view-sub-menu-link');
    const programmingSearchForms = document.querySelectorAll('.data-sharing-view-main-search form');
    const dataSearchForm = document.getElementById('data-sharing-view-data_search_form');

    // 메인 메뉴 선택 시 처리
    mainMenuSelectors.forEach(selector => {
        selector.addEventListener('click', function () {
            const targetMenuId = this.getAttribute('data-target');
            const targetMenu = document.getElementById(targetMenuId);
            const isActive = targetMenu.classList.contains('active');

            // 모든 sub_menu를 숨김
            subMenus.forEach(menu => {
                menu.classList.remove('active');
            });

            // 모든 메인 메뉴 선택자에서 active 클래스 제거
            mainMenuSelectors.forEach(selector => {
                selector.classList.remove('active');
            });

            // 클릭된 메인 메뉴를 토글
            if (!isActive) {
                targetMenu.classList.add('active');
                this.classList.add('active'); // 메인 메뉴 선택자에 active 클래스 추가
            }
        });
    });

    // 서브 메뉴 링크 선택 시 처리
    subMenuLinks.forEach(link => {
        link.addEventListener('click', function (event) {
            event.preventDefault(); // 링크의 기본 동작 방지

            // 클릭된 링크에만 active 클래스 추가
            subMenuLinks.forEach(link => {
                link.classList.remove('active');
            });
            this.classList.add('active');

            // 클릭된 메뉴의 부모 요소인 서브 메뉴를 활성화 상태로 유지
            const parentSubMenu = this.closest('.data-sharing-view-sub-menu');
            parentSubMenu.classList.add('active');

            // 클릭된 메인 메뉴 선택자에 active 클래스 추가
            const mainMenuSelector = document.querySelector(`[data-target="${parentSubMenu.id}"]`);
            if (mainMenuSelector) {
                mainMenuSelector.classList.add('active');
            }

            // 선택한 카테고리에 맞게 select 박스 보이기/감추기
            showSelectBox(parentSubMenu.id);
        });
    });

    // 메인 메뉴 선택 시 처리
    mainMenuSelectors.forEach(selector => {
        selector.addEventListener('click', function () {
            const targetId = this.getAttribute('data-target'); // 클릭된 메뉴의 data-target 값 가져오기

            // 모든 programming_search_select 숨기기
            programmingSearchForms.forEach(form => {
                form.style.display = 'none';
            });

            // 모든 data_search_form 숨기기
            dataSearchForm.style.display = 'none';

            // 클릭된 메뉴의 programming_search_select 표시
            const programmingSearchForm = document.getElementById(`${targetId}_search`);
            if (programmingSearchForm) {
                programmingSearchForm.style.display = 'block';
            }

            // data_search_form 표시
            dataSearchForm.style.display = 'block';
        });
    });

    // AI 카테고리 선택 시 처리
    const aiSelector = document.querySelector('.data-sharing-view-main-menu-selector-wrapper[data-target="AI"]');
    const aiSubMenu = document.getElementById('AI');
    const aiSearchForm = document.getElementById('data-sharing-view-ai_search');

    aiSelector.addEventListener('click', function () {
        // 모든 sub_menu를 숨김
        subMenus.forEach(menu => {
            menu.classList.remove('active');
        });

        // AI 카테고리의 sub_menu를 활성화
        aiSubMenu.classList.add('active');

        // AI 카테고리에 해당하는 select 폼 표시
        aiSearchForm.style.display = 'block';

        // 다른 메뉴의 select 폼 숨기기
        programmingSearchForms.forEach(form => {
            if (form !== aiSearchForm) {
                form.style.display = 'none';
            }
        });

        // data_search_form 표시
        dataSearchForm.style.display = 'block';
    });
});
