// 햄버거 메뉴 클릭 시, 메뉴가 보이거나 숨겨지는 기능
document.querySelector('.hamburger-menu').addEventListener('click', function () {
    const menu = document.querySelector('.navbar-menu');
    const hamburger = document.querySelector(".hamburger-menu");

    // 햄버거 메뉴 아이콘 애니메이션
    hamburger.classList.toggle("active");

    // 메뉴가 펼쳐지는 애니메이션
    menu.classList.toggle("show");
});

// 메일 전송 성공 & 실패 alert
document.getElementById('contactForm').addEventListener('submit', function (event) {
        event.preventDefault();  // 폼이 전송되지 않도록 막음

        const mailDto = {
            title: document.getElementById('subject').value,
            name: document.getElementById('name').value,
            address: document.getElementById('address').value,
            content: document.getElementById('content').value
        };

        fetch('/contact', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(mailDto)  // 자바스크립트 객체를 JSON으로 변환
        })
        .then(response => response.json())  // 응답을 JSON으로 변환
        .then(data => {
            if (data.message) {
                alert(data.message);  // 메일 전송 완료 메시지를 alert로 띄움
            }
            window.location.reload();
        })
        .catch(error => {
            console.error('Error:', error);
            alert("메일 전송에 실패했습니다.");
            window.location.reload();
        });
    });