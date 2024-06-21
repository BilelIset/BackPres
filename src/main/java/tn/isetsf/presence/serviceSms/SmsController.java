package tn.isetsf.presence.serviceSms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SmsController {

    @Autowired
    private SmsService smsService;

    @PostMapping(value = "/sms")
    public String sendMsg(@RequestParam String phone, @RequestParam String message) {
        smsService.sendSms(phone, message);
        return "OK";
    }
}
