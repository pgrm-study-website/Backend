package plming.auth.Oauth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import plming.auth.Oauth.entity.SocialLoginType;
import plming.auth.Oauth.service.social.SocialOauth;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OauthService {
    private final HttpServletResponse response;
    private final List<SocialOauth> socialOauthList;

    public String requestOauthUserId(int socialType, String code){
        SocialOauth socialOauth = this.findSocialOauthByType(SocialLoginType.findBy(socialType));
        String authorizeCode = socialOauth.requestAccessToken(code);
        return socialOauth.requestOauthUserId(authorizeCode);
    }

    private SocialOauth findSocialOauthByType(SocialLoginType socialLoginType){
        return socialOauthList.stream()
                .filter(x -> x.type() == socialLoginType)
                .findFirst()
                .orElseThrow(()->new IllegalArgumentException("알 수 없는 소셜 로그인 타입입니다."));
    }
}
