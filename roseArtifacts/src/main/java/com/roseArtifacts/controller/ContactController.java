package com.roseArtifacts.controller;

import com.roseArtifacts.dto.MailDto;
import com.roseArtifacts.service.ContactService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
@Log4j2
public class ContactController {

    private final ContactService contactService;

    @GetMapping("/contact")
    public String sendMail() {
        return "contact";
    }

    @PostMapping("/contact")
    public ResponseEntity<?> sendContactForm(@RequestBody MailDto mailDto) {
        try {
            contactService.sendEmail(mailDto);
            System.out.println("메일 전송 완료");
            // JSON으로 응답을 전송
            return ResponseEntity.ok().body("{\"message\":\"메일이 전송되었습니다!\"}");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("{\"message\":\"메일 전송 중 오류가 발생했습니다.\"}");
        }
    }

}
