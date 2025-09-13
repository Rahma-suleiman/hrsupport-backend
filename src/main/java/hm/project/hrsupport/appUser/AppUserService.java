package hm.project.hrsupport.appUser;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import hm.project.hrsupport.registration.token.ConfirmationToken;
import hm.project.hrsupport.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;



    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email)
            .orElseThrow(()-> new UsernameNotFoundException("user with email"+email+"not found"));
    }
    public String SignUpUser(AppUser appUser){
        Boolean userExist = appUserRepository.findByEmail(appUser.getEmail()).isPresent();
        //if user exist in DB throw Exception
        if (userExist) {
            throw new IllegalStateException("email "+appUser.getEmail()+" already exist/taken");
        }
        //if user doesnt exist in DB
        String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());
        appUser.setPassword(encodedPassword);
        appUserRepository.save(appUser);

        // generate unique confirmation token / create a new confirmation token(using the confirmationToken constructor)for new user
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
            token,
            LocalDateTime.now(),
            LocalDateTime.now().plusMinutes(15),
            appUser
        );
        //save the generated token to ConfirmationToken entity via confirmationTokenService
        confirmationTokenService.saveConfirmationToken(confirmationToken);
        return token;
    }

    // enable appUser account frm F to TRUE if token is validated, not expired, n not yet confirmed
    public int enableAppUser(String email){
        return appUserRepository.enableAppUser(email);
    }
    
}
