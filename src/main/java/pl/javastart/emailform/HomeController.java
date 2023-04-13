package pl.javastart.emailform;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    MailService mailService;

    public HomeController(MailService mailService) {
        this.mailService = mailService;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/contact")
    public String contact(){
        return "send";
    }

    @PostMapping ("/contact")
    public String contactForm(@RequestParam String senderEmail,
                              @RequestParam String senderName,
                              @RequestParam String text) {
        mailService.sendEmailToOwner(senderEmail ,senderName, text);
        mailService.sendConfirmationToSender(senderEmail);
        return "index";
    }
}
