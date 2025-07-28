package com.example.boardexample.entity;

import com.example.boardexample.exception.TokenExpiredException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter @Getter
@Entity
public class EmailVerificationToken {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column()
    private String token;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime created_at;

    private LocalDateTime expires_at;

    private boolean used = false;

    private LocalDateTime used_at;

    private boolean deleted = false;

    private LocalDateTime deleted_at;


    @Column(name = "retry_token")
    private String retryToken;

    @Column(name = "retry_token_expires_at")
    private LocalDateTime retryTokenExpiresAt;

    public static EmailVerificationToken createToken(User user, String toekn, Long expiredMinutes) {
        EmailVerificationToken emailVerificationToken = new EmailVerificationToken();
        emailVerificationToken.setUser(user);
        emailVerificationToken.setToken(toekn);
        emailVerificationToken.setTime(expiredMinutes);

        return emailVerificationToken;
    }


    public void isExpires() {
        LocalDateTime now = LocalDateTime.now();

        if(now.isAfter(this.expires_at)) {
            throw new TokenExpiredException("token is expired");
        }
    }

    public void isDeleted() {
        if(this.deleted) {
            throw new IllegalStateException("token is Deleted");
        }
    }


    public void setTime(Long EXPIRED_MINUTES) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiredTime= now.plusMinutes(EXPIRED_MINUTES);

        this.setCreated_at(now);
        this.setExpires_at(expiredTime);
    }
}
