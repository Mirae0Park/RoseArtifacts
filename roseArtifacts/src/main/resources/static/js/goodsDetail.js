let currentSlide = 0;
const slides = document.querySelectorAll('.product-img');

function showSlide(index) {
    // 슬라이드 인덱스 조정 (무한 루프)
    if (index >= slides.length) {
        currentSlide = 0;
    } else if (index < 0) {
        currentSlide = slides.length - 1;
    } else {
        currentSlide = index;
    }
    const slideWidth = slides[currentSlide].clientWidth;
    document.querySelector('.slides').style.transform = `translateX(-${currentSlide * slideWidth}px)`;
}

function nextSlide() {
    showSlide(currentSlide + 1);
}

function prevSlide() {
    showSlide(currentSlide - 1);
}

// 첫 슬라이드 표시
showSlide(0);

// 햄버거 메뉴 클릭 시, 메뉴가 보이거나 숨겨지는 기능
document.querySelector('.hamburger-menu').addEventListener('click', function () {
    const menu = document.querySelector('.navbar-menu');
    const hamburger = document.querySelector(".hamburger-menu");

    // 햄버거 메뉴 아이콘 애니메이션
    hamburger.classList.toggle("active");

    // 메뉴가 펼쳐지는 애니메이션
    menu.classList.toggle("show");
});

window.onload = function() {
    const images = document.querySelectorAll('.product-img');
    const prevButton = document.querySelector('.prev-btn');
    const nextButton = document.querySelector('.next-btn');

    // 이미지 개수가 1개 이하이면 버튼 숨기기
    if (images.length <= 1) {
        prevButton.style.display = 'none';
        nextButton.style.display = 'none';
    } else {
        prevButton.style.display = 'block';
        nextButton.style.display = 'block';
    }
};