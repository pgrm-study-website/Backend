package plming.auth.Oauth.service.social;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import plming.auth.Oauth.dto.AccessTokenRequestDto;

@Service
public class GithubOauth implements SocialOauth{

    @Value("${sns.github.client.id}")
    private String GITHUB_CLIENT_ID;
    @Value("${sns.github.client.secret}")
    private String GITHUB_CLIENT_SECRET;
    @Value("${sns.github.redirect.uri}")
    private String GITHUB_REDIRECT_URI;
    @Value("${sns.github.token.url}")
    private String GITHUB_TOKEN_URL;
    @Value("${sns.github.resource.url}")
    private String GITHUB_RESOURCE_URL;

    @Override
    public String requestAccessToken(String code) {
        AccessTokenRequestDto accessTokenRequestDto = AccessTokenRequestDto
                .builder()
                .clientId(GITHUB_CLIENT_ID)
                .clientSecret(GITHUB_CLIENT_SECRET)
                .redirectUri(GITHUB_REDIRECT_URI)
                .code(code)
                .build();

        HttpEntity entity = new HttpEntity(accessTokenRequestDto.toMultiValueMap(),null);

        return requestToOauthTokenServer(GITHUB_TOKEN_URL,entity);
    }

    @Override
    public String requestOauthUserId(String accessToken){
        return requestToOauthResourceServer(GITHUB_RESOURCE_URL,accessToken,"token");
    }
}
