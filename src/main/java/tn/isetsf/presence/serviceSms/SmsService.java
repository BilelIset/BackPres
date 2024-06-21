package tn.isetsf.presence.serviceSms;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.stereotype.Service;

@Service
public class SmsService {
    // Find your Account Sid and Token at twilio.com/console
    public  String ACCOUNT_SID = "ACf54107496b1376f787a6f153397a7781";
    public  String AUTH_TOKEN = "7c29c592410d6a7540a17167136df91a";

  public void sendSms(String number,String msg) {

      Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
      Message message = Message.creator(
              new com.twilio.type.PhoneNumber("+216"+number),
              new com.twilio.type.PhoneNumber("+14177364771"),
              msg

      ).create();

  }

}