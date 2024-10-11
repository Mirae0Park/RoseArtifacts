// 햄버거 메뉴 클릭 시, 메뉴가 보이거나 숨겨지는 기능
document.querySelector('.hamburger-menu').addEventListener('click', function () {
    const menu = document.querySelector('.navbar-menu');
    const hamburger = document.querySelector(".hamburger-menu");

    // 햄버거 메뉴 아이콘 애니메이션
    hamburger.classList.toggle("active");

    // 메뉴가 펼쳐지는 애니메이션
    menu.classList.toggle("show");
});