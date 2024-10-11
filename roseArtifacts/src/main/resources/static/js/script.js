let x = 0;
let targetX = 0;
let scrollNum = 0;

const imageAll = document.querySelectorAll(".imageWrap .parallaxImage");
const totalNum = imageAll.length;
const subPageImage = document.querySelector(".subPage .parallaxImage");
const speed = .1;

window.addEventListener("scroll", () => {


    scrollNum = window.scrollY;

    imageAll.forEach((item, index) => {
        if (index < 4) {
            item.style.transform = `translateY(${-scrollNum / (2 * (totalNum - index))}px)`;
        }
    });
});

window.addEventListener("mousemove", (e) => {
    x = e.pageX - window.innerWidth / 2;
});

const loop = () => {
    targetX += (x - targetX) * speed;

    imageAll[0].style.transform =
        `scale(1.05) translate(${targetX / 150}px, ${-scrollNum / (2 * (totalNum - 1))}px)`;
    imageAll[1].style.transform =
        `scale(1.05) translate(${-targetX / 100}px, ${-scrollNum / (2 * (totalNum - 2))}px)`;
    imageAll[2].style.transform =
        `scale(1.05) translate(${targetX / 50}px, ${-scrollNum / (2 * (totalNum - 3))}px)`;
    imageAll[3].style.transform =
        `scale(1.05) translate(${-targetX / 25}px, ${-scrollNum / (2 * (totalNum - 4))}px)`;
    imageAll[4].style.transform =
        `scale(1.05) translate(${-targetX / 10}px, ${-scrollNum / (2 * (totalNum - 4))}px)`;

    window.requestAnimationFrame(loop);
}
loop();

// 햄버거 메뉴 클릭 시, 메뉴가 보이거나 숨겨지는 기능
document.querySelector('.hamburger-menu').addEventListener('click', function () {
    const menu = document.querySelector('.navbar-menu');
    const hamburger = document.querySelector(".hamburger-menu");

    // 햄버거 메뉴 아이콘 애니메이션
    hamburger.classList.toggle("active");

    // 메뉴가 펼쳐지는 애니메이션
    menu.classList.toggle("show");
});