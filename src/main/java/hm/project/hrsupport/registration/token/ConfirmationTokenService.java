package hm.project.hrsupport.registration.token;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    //save confirmtion token
    public void saveConfirmationToken(ConfirmationToken token){
        confirmationTokenRepository.save(token);
    }
    //get token to validate if its valid frm the DB
    public Optional<ConfirmationToken> getToken(String token){
        return confirmationTokenRepository.findByToken(token);
    }
    // set confirmedAt
    public int setConfirmedAt(String token){
        return confirmationTokenRepository.updateConfirmedAt(token,LocalDateTime.now());
    }

}
