package com.med.acadmys.controllers;

import com.med.acadmys.dtos.MailRequest;
import com.med.acadmys.dtos.Response;
import com.med.acadmys.services.MailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(value = "*")
@RequestMapping("/api/v1/mail")
@PreAuthorize("hasRole('ADMIN')")
public class MailController {
    private final MailService mailService;

    @PostMapping("/send")
    @PreAuthorize("hasAuthority('admin:write')")
    ResponseEntity<Response> sendMail(@RequestBody MailRequest mailRequest) throws MessagingException {
        return ResponseEntity.ok(mailService.sendEmail(mailRequest));
    }
}
