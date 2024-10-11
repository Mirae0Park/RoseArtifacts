package com.roseArtifacts.service;

import com.roseArtifacts.dto.MailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class ContactService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail (MailDto mailDto){
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo("milepark999@gmail.com");
        message.setFrom("milepark999@gmail.com");

        // 'Reply-To': 사용자가 입력한 이메일 주소 (회신 이메일)
        message.setReplyTo(mailDto.getAddress());

        message.setSubject("roseArtifacts 문의 메일");
        message.setText("이름: " + mailDto.getName() + "\n내용 : " + mailDto.getContent());

        try {
            mailSender.send(message); // 메일 전송
            System.out.println("메일 전송 완료");
        } catch (MailException e) {
            System.err.println("메일 전송 실패: " + e.getMessage()); // 예외 처리
        }
    }

}
