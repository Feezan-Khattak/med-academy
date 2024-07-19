package com.med.acadmys.services;

import com.med.acadmys.clients.MailGateway;
import com.med.acadmys.dtos.MailRequest;
import com.med.acadmys.dtos.Response;
import com.med.acadmys.models.Template;
import com.med.acadmys.repositories.TemplateRepository;
import com.med.acadmys.utils.ResponseUtil;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {
    private final MailGateway mailGateway;
    private final TemplateRepository templateRepository;
    private final ResponseUtil responseUtil;

    public Response sendEmail(MailRequest req) throws MessagingException {
        Template template = templateRepository.findByTemplateKey(req.getTemplate())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Template selected"));

        String templateString = template.getTemplate();
        log.info("Embedding template values...");

        for (String key : req.getData().keySet()) {
            templateString = templateString.replace("{{" + key + "}}", req.getData().get(key));
        }

        mailGateway.sendEmailAsync(req.getEmail(), req.getSubject(), templateString);
        return responseUtil.generateSuccessResponse("Email Sent Successfully");
    }
}