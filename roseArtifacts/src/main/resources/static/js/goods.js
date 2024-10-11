const carousel = document.querySelector('.carousel');
const track = document.querySelector('.carousel-track');
let mouseX = 0;
let currentX = 0;
let maxScroll = track.scrollWidth - carousel.offsetWidth;

// 햄버거 메뉴 클릭 시, 메뉴가 보이거나 숨겨지는 기능
document.querySelector('.hamburger-menu').addEventListener('click', function () {
    const menu = document.querySelector('.navbar-menu');
    const hamburger = document.querySelector(".hamburger-menu");

    // 햄버거 메뉴 아이콘 애니메이션
    hamburger.classList.toggle("active");

    // 메뉴가 펼쳐지는 애니메이션
    menu.classList.toggle("show");
});

function throttle(func, limit) {
    let inThrottle;
    return function() {
        const args = arguments;
        const context = this;
        if (!inThrottle) {
            func.apply(context, args);
            inThrottle = true;
            setTimeout(() => inThrottle = false, limit);
        }
    };
}

let isRequestingFrame = false;

carousel.addEventListener('mousemove', throttle((e) => {
    if (!isRequestingFrame) {
        isRequestingFrame = true;

        // 브라우저가 화면을 업데이트할 때 호출
        requestAnimationFrame(() => {
            const mouseX = e.clientX / carousel.offsetWidth;

            const leftLimit = 0.1;
            const rightLimit = 0.9;
            const adjustedMouseX = Math.max(Math.min(mouseX, rightLimit), leftLimit);

            const maxScroll = track.scrollWidth - carousel.offsetWidth;

            // 추가 공간을 화면 너비에 따라 조정
            const extraSpace = Math.max(200, carousel.offsetWidth / 5); // 화면 크기에 따라 여유 공간 조정
            const adjustedMaxScroll = maxScroll + extraSpace * 2;

            const currentX = -((adjustedMouseX * adjustedMaxScroll) - extraSpace);

            // 화면이 작은 경우에는 더 이상 트랙이 이동하지 않도록 설정
            if (carousel.offsetWidth < 768) {
                const maxPosition = Math.min(0, -maxScroll);
                track.style.transform = `translateX(${Math.max(currentX, maxPosition)}px)`;
            } else {
                track.style.transform = `translateX(${currentX}px)`;
            }

            // 프레임 요청 완료
            isRequestingFrame = false;
        });
    }
}, 100)); // 100ms 간격으로 이벤트 호출


window.onload = () => {
    const h1 = document.querySelector('h1');
    const box = document.querySelector(".box");

    let x = 0;
    let y = 0;
    let targetX = 0;
    let targetY = 0;
    let speed = 0.8;

    window.addEventListener("mousemove", (event) => {
        x = event.pageX;
        y = event.pageY;
    });

    const loop = () => {

        targetX += (x - targetX) * speed;
        targetY += (y - targetY) * speed;

        box.style.top = targetY + 30 + "px";
        box.style.left = targetX + 28 + "px";

        window.requestAnimationFrame(loop);
    };

    loop();

};

async function searchByCategory(cate) {

    const carouselTrack = document.getElementById('carousel-track');

    carouselTrack.style.transition = 'transform 0.5s ease-in-out';
    carouselTrack.style.transform = 'translateX(-100%)';

    try {
        const response = await fetch(`/goods/list/async?cate=${cate}`);
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        const goodsData = await response.json();
        setTimeout(() => {
            updateCarousel(goodsData);
        }, 500);   // 데이터를 화면에 업데이트
    } catch (error) {
        console.error('Error fetching category data:', error);
    }
}

function updateCarousel(goods) {
    const carouselTrack = document.getElementById('carousel-track');

    carouselTrack.innerHTML = '';
    carouselTrack.style.transform = 'translateX(100%)';

    goods.forEach(goods => {
        const carouselItem = document.createElement('div');
        carouselItem.classList.add('carousel-item');
        carouselItem.innerHTML = `
            <a href="/goods/detail/${goods.id}">
                <img src="/image/frame_01.png" alt="Frame" class="frame-image">
                <img src="${goods.imgUrl}" alt="${goods.name}" class="product-image">
                <h3>${goods.name}</h3>
                <p>${goods.price.toLocaleString()} 원</p>
            </a>
        `;
        carouselTrack.appendChild(carouselItem);
    });

    setTimeout(() => {
        carouselTrack.style.transition = 'transform 0.2s ease-in-out';
        carouselTrack.style.transform = 'translateX(0)';  // 중앙으로 이동
    }, 50);
}