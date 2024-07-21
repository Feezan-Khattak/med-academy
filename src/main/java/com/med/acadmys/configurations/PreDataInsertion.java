package com.med.acadmys.configurations;

import com.med.acadmys.models.SubscriptionPlan;
import com.med.acadmys.models.Template;
import com.med.acadmys.models.User;
import com.med.acadmys.repositories.SubscriptionPlanRepository;
import com.med.acadmys.repositories.TemplateRepository;
import com.med.acadmys.repositories.UserRepository;
import com.med.acadmys.utils.Role;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static com.med.acadmys.utils.Mail.FORGOT_PASSWORD;

@Slf4j
@Service
@AllArgsConstructor
public class PreDataInsertion {
    private final UserRepository userRepository;
    private final TemplateRepository templateRepository;
    private final SubscriptionPlanRepository subscriptionPlanRepository;
    private final BCryptPasswordEncoder passwordEncoder;


    @PostConstruct
    public void initialDataInsertion() {
        addSuperAdmin();
        addResetPasswordTemplate();
        addSubscriptionPlan();
    }

    private void addSuperAdmin() {
        Optional<User> user = userRepository.findById(1L);
        if (user.isPresent()) {
            log.info("Admin already exists");
        } else {
            User admin = new User();
            admin.setRole(Role.ADMIN);
            admin.setEmail("feezanktk2208@gmail.com");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setFirstName("Feezan");
            admin.setLastName("Khattak");
            admin.setUserId(UUID.randomUUID().toString());
            admin.setIsEnabled(true);
            userRepository.save(admin);
        }
    }

    private void addResetPasswordTemplate() {
        Optional<Template> template = templateRepository.findByTemplateKey(FORGOT_PASSWORD.name());
        if (template.isPresent()) {
            log.info("Template already exists");
        } else {
            Template forgotTemplate = new Template();
            forgotTemplate.setTemplateKey(FORGOT_PASSWORD.name());
            forgotTemplate.setSubject("Password Reset Request");
            forgotTemplate.setTemplate("<!DOCTYPE html><html lang=\"en-US\"><head><meta content=\"text/html; charset=utf-8\" http-equiv=\"Content-Type\"><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"><title>Reset Password</title><meta name=\"description\" content=\"Reset Password\"><style type=\"text/css\">a:hover { text-decoration: underline !important; } @media screen and (max-width: 600px) { table { width: 100% !important; } td { display: block; width: 100%; box-sizing: border-box; padding: 10px; } }</style></head><body marginheight=\"0\" topmargin=\"0\" marginwidth=\"0\" style=\"margin: 0px; background-color: #f2f3f8;\" leftmargin=\"0\"><table cellspacing=\"0\" border=\"0\" cellpadding=\"0\" width=\"100%\" bgcolor=\"#f2f3f8\" style=\"font-family: ''Open Sans'', sans-serif;\"><tr><td><table style=\"background-color: #f2f3f8; max-width:670px;  margin:0 auto;\" width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\"><tr><td style=\"height:20px;\">&nbsp;</td></tr><tr><td><table width=\"95%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" style=\"max-width:670px;background:#fff; border-radius:3px; text-align:center;-webkit-box-shadow:0 6px 18px 0 rgba(0,0,0,.06);-moz-box-shadow:0 6px 18px 0 rgba(0,0,0,.06);box-shadow:0 6px 18px 0 rgba(0,0,0,.06);\"><tr><td style=\"height:20px;\">&nbsp;</td></tr><tr><td style=\"padding:0 35px;\"><h1 style=\"color:#1e1e2d; font-weight:500; margin:0;font-size:28px;font-family:''Rubik'',sans-serif;\">You have requested to reset your password</h1><span style=\"display:inline-block; vertical-align:middle; margin:20px 0 18px; border-bottom:1px solid #cecece; width:100px;\"></span><p style=\"color:#455056; font-size:14px;line-height:22px; margin:0;\">A unique link to reset your password has been generated for you. To reset your password, click the button below and follow the instructions.</p><a href=\"{{password_reset_link}}\" style=\"background:#20e277;text-decoration:none !important; font-weight:500; margin-top:30px; color:#fff;text-transform:uppercase; font-size:14px;padding:10px 20px;display:inline-block;border-radius:50px;\">Reset Password</a></td></tr><tr><td style=\"height:20px;\">&nbsp;</td></tr></table></td></tr><tr><td style=\"height:20px;\">&nbsp;</td></tr></table></td></tr></table></body></html>\n");
            templateRepository.save(forgotTemplate);
        }
    }

    private void addSubscriptionPlan() {
        Optional<SubscriptionPlan> basicSubscription = subscriptionPlanRepository.findByName("BASIC");
        if (basicSubscription.isPresent()) {
            log.info("Basic subscription plan already exists");
        } else {
            SubscriptionPlan subscriptionPlan = new SubscriptionPlan();
            subscriptionPlan.setName("BASIC");
            subscriptionPlan.setDescription("This is basic subscription");
            subscriptionPlan.setDurationMonths(1);
            subscriptionPlan.setPrice(3000);
            subscriptionPlanRepository.save(subscriptionPlan);
        }

        Optional<SubscriptionPlan> standardSubscription = subscriptionPlanRepository.findByName("STANDARD");
        if (standardSubscription.isPresent()) {
            log.info("Standard subscription plan already exists");
        } else {
            SubscriptionPlan subscriptionPlan = new SubscriptionPlan();
            subscriptionPlan.setName("STANDARD");
            subscriptionPlan.setDescription("This is standard subscription");
            subscriptionPlan.setDurationMonths(6);
            subscriptionPlan.setPrice(6000);
            subscriptionPlanRepository.save(subscriptionPlan);
        }

        Optional<SubscriptionPlan> premiumSubscription = subscriptionPlanRepository.findByName("PREMIUM");
        if (standardSubscription.isPresent()) {
            log.info("Premium subscription plan already exists");
        } else {
            SubscriptionPlan subscriptionPlan = new SubscriptionPlan();
            subscriptionPlan.setName("PREMIUM");
            subscriptionPlan.setDescription("This is premium subscription");
            subscriptionPlan.setDurationMonths(12);
            subscriptionPlan.setPrice(12000);
            subscriptionPlanRepository.save(subscriptionPlan);
        }
    }
}
