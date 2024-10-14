package com.roseArtifacts.controller;

import com.roseArtifacts.dto.MailDto;
import com.roseArtifacts.service.ContactService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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
    public String sendContactForm(MailDto mailDto){
        contactService.sendEmail(mailDto);
        System.out.println("메일 전송 완료");
        return "redirect:/";
    }

}
