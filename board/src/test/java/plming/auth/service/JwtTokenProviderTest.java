package plming.auth.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class JwtTokenProviderTest {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    @Test
    void getUserIdTest(){
        //given
        Long userId = 22L;
        String token = jwtTokenProvider.createToken(userId);

        //when
        Long newUserId = jwtTokenProvider.getUserId(token);

        //then
        assertThat(newUserId).isEqualTo(userId);
    }

}
