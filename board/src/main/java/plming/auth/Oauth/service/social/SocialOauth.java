package plming.auth.Oauth.service.social;

import org.springframework.http.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import plming.auth.Oauth.dto.OauthAccessTokenDto;
import plming.auth.Oauth.dto.OauthUserIdDto;
import plming.auth.Oauth.entity.SocialLoginType;
import plming.exception.CustomException;
import plming.exception.ErrorCode;

public interface SocialOauth {

    String requestAccessToken(String code);

    String requestOauthUserId(String accessToken);

    default SocialLoginType type(){
        if(this instanceof GoogleOauth){
            return SocialLoginType.GOOGLE;
        }else if(this instanceof KakaoOauth){
            return SocialLoginType.KAKAO;
        }else if(this instanceof GithubOauth){
            return SocialLoginType.GITHUB;
        } else{
            return null;
        }
    }

    default String requestToOauthTokenServer(String requestUrl, HttpEntity entity){
        try{
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<OauthAccessTokenDto> responseEntity =
                    restTemplate.exchange(requestUrl, HttpMethod.POST,entity,OauthAccessTokenDto.class);
            OauthAccessTokenDto responseDto = responseEntity.getBody();
            if(responseDto.getAccessToken() == null) throw new CustomException(ErrorCode.BAD_REQUEST);
            return responseDto.getAccessToken();
        }catch (RestClientException e){
            throw new CustomException(ErrorCode.BAD_REQUEST);
        }
    }

    default String requestToOauthResourceServer(String requestUrl, String accessToken, String tokenType){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization",tokenType+" " + accessToken);
        HttpEntity entity = new HttpEntity("",headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<OauthUserIdDto> response = restTemplate.exchange(requestUrl, HttpMethod.GET,entity,OauthUserIdDto.class);
        return response.getBody().getId();
    }
}
