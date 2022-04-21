package plming.auth.Oauth.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SocialLoginType {
    GOOGLE(1),
    KAKAO(2),
    GITHUB(3);

    private int socialType;

    public static SocialLoginType findBy(int socialType){
        for(SocialLoginType s : values()){
            if(s.getSocialType() == socialType) return s;
        }
        throw new IllegalArgumentException("해당하는 숫자의 소셜 로그인 타입이 없습니다.");
    }
}

