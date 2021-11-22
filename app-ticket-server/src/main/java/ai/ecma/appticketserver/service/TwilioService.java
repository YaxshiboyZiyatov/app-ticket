package ai.ecma.appticketserver.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author Muhammad Mo'minov
 * 11.10.2021
 */
@Service
public class TwilioService {
    @Value("${twilio.accountSID}")
    private String twilioAccountSID;


    @Value("${twilio.authToken}")
    private String twilioAuthToken;

    @Value("${twilio.trialNumber}")
    private String twilioTrialNumber;

    public boolean sendVerificationCode(String phoneNumber, String verificationCode) {
        try {
            Twilio.init(twilioAccountSID, twilioAuthToken);
            Message
                    .creator(
                            new com.twilio.type.PhoneNumber(phoneNumber),
                            new com.twilio.type.PhoneNumber(twilioTrialNumber),
                            "Your verification code: " + verificationCode + ". Buni hechkimga bermang aka!")
                    .create();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
