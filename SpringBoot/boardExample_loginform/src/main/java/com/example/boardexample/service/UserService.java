package com.example.boardexample.service;

import com.example.boardexample.Util.AuthUtil;
import com.example.boardexample.dto.EmailVerificationPayload;
import com.example.boardexample.dto.RetryTokenDto;
import com.example.boardexample.dto.UserDto;
import com.example.boardexample.dto.SignupDto;
import com.example.boardexample.entity.EmailVerificationToken;
import com.example.boardexample.entity.User;
import com.example.boardexample.repository.EmailVerificationTokenRepository;
import com.example.boardexample.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final AuthUtil authUtil;
    private final EmailVerificationTokenRepository emailVerificationTokenRepository;
    private final KafkaTemplate<String,Object> kafkaTemplate;

    private final Long EXPIRED_MINUTES = 15L;

    private final String TOPIC_NAME = "email-auth-topic";

    @Transactional
    public SignupDto signup(UserDto userDto) {

        //create user
        User user = User.createUser(userDto);
        userRepository.save(user);

        //create email token
        EmailVerificationToken emailVerificationToken = EmailVerificationToken.createToken(
                user,
                authUtil.createToekn(),
                EXPIRED_MINUTES);
        emailVerificationTokenRepository.save(emailVerificationToken);

        EmailVerificationPayload payload = new EmailVerificationPayload(
                user.getEmail(),
                emailVerificationToken.getToken(),
                emailVerificationToken.getExpires_at()
        );

        //kafka
        kafkaTemplate.executeInTransaction(kafka->{
            kafkaTemplate.send(TOPIC_NAME,payload);
            return true;
        });

        return new SignupDto(user.getId(), emailVerificationToken.getId());
    }


    public void retry(RetryTokenDto retryTokenDto) {
        userRepository.findRetryToken(retryTokenDto.getId());
    }
}
