package com.fzoo.zoomanagementsystem.service;

import com.fzoo.zoomanagementsystem.dto.RefreshTokenRequest;
import com.fzoo.zoomanagementsystem.model.RefreshToken;
import com.fzoo.zoomanagementsystem.repository.AccountRepository;
import com.fzoo.zoomanagementsystem.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final AccountRepository accountRepository;
    Duration duration = Duration.ofDays(7);

    public RefreshToken createRefreshToken(String email){

        RefreshToken refreshToken = RefreshToken
                .builder()
                .accountInfo(accountRepository.findByEmail(email).get())
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plus(duration))
                .build();
        return refreshTokenRepository.save(refreshToken);
    }

    public Optional<RefreshToken> findByToken(String token){
        return  refreshTokenRepository.findByToken(token);
    }

    public RefreshToken verifyExpiration(RefreshToken token){
        if(token.getExpiryDate().compareTo(Instant.now())<0){
            refreshTokenRepository.delete(token);
            throw new RuntimeException(token.getToken()+ " was expired");
        }
        return token;
    }

    public void logout(RefreshTokenRequest request){
        Optional<RefreshToken> byToken = refreshTokenRepository.findByToken(request.getToken());
        if(byToken.isPresent()){
            refreshTokenRepository.deleteById(byToken.get().getId());
        }
    }

}
