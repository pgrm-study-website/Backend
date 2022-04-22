package plming.auth.Oauth.service.social;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import plming.auth.Oauth.dto.AccessTokenRequestDto;
@Service
public class GoogleOauth implements SocialOauth{

    @Value("${sns.google.client.id}")
    private String GOOGLE_CLIENT_ID;
    @Value("${sns.google.client.secret}")
    private String GOOGLE_CLIENT_SECRET;
    @Value("${sns.google.redirect.uri}")
    private String GOOGLE_REDIRECT_URI;
    @Value("${sns.google.token.url}")
    private String GOOGLE_TOKEN_URL;
    @Value("${sns.google.resource.url}")
    private String GOOGLE_RESOURCE_URL;

    @Override
    public String requestAccessToken(String code) {
        AccessTokenRequestDto accessTokenRequestDto = AccessTokenRequestDto
                .builder()
                .clientId(GOOGLE_CLIENT_ID)
                .clientSecret(GOOGLE_CLIENT_SECRET)
                .redirectUri(GOOGLE_REDIRECT_URI)
                .grantType("authorization_code")
                .code(code)
                .build();

        HttpEntity entity = new HttpEntity(accessTokenRequestDto.toMultiValueMap(),null);

        return requestToOauthTokenServer(GOOGLE_TOKEN_URL,entity);
    }

    @Override
    public String requestOauthUserId(String accessToken){
        return requestToOauthResourceServer(GOOGLE_RESOURCE_URL,accessToken,"Bearer");
    }

}
