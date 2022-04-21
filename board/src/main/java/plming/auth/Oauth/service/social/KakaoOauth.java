package plming.auth.Oauth.service.social;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import plming.auth.Oauth.dto.AccessTokenRequestDto;

@Service
public class KakaoOauth implements SocialOauth{

    @Value("${sns.kakao.client.id}")
    private String KAKAO_CLIENT_ID;
    @Value("${sns.kakao.client.secret}")
    private String KAKAO_CLIENT_SECRET;
    @Value("${sns.kakao.redirect.uri}")
    private String KAKAO_REDIRECT_URI;
    @Value("${sns.kakao.token.url}")
    private String KAKAO_TOKEN_URL;
    @Value("${sns.kakao.resource.url}")
    private String KAKAO_RESOURCE_URL;

    @Override
    public String requestAccessToken(String code) {
        AccessTokenRequestDto accessTokenRequestDto = AccessTokenRequestDto
                .builder()
                .clientId(KAKAO_CLIENT_ID)
                .clientSecret(KAKAO_CLIENT_SECRET)
                .redirectUri(KAKAO_REDIRECT_URI)
                .grantType("authorization_code")
                .code(code)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity entity = new HttpEntity(accessTokenRequestDto.toMultiValueMap(),headers);

        return requestToOauthTokenServer(KAKAO_TOKEN_URL,entity);
    }

    @Override
    public String requestOauthUserId(String accessToken){
        return requestToOauthResourceServer(KAKAO_RESOURCE_URL,accessToken,"Bearer");
    }

}
