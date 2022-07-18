package plming.auth.Oauth.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;


@Getter
@AllArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AccessTokenRequestDto {
    private String clientId;
    private String clientSecret;
    private String redirectUri;
    private String grantType;
    private String code;

    public MultiValueMap toMultiValueMap(){
        MultiValueMap map = new LinkedMultiValueMap();
        map.add("client_id",clientId);
        map.add("client_secret",clientSecret);
        map.add("redirect_uri",redirectUri);
        map.add("grant_type",grantType);
        map.add("code",code);
        return map;
    }
}
