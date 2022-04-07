package plming.board.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchRequestDto {

    private String type;
    private Object keyword;

    public Map<String, Object> toEntity() {

        Map<String, Object> map = new HashMap<String, Object>(2);
        map.put("type", this.type);
        map.put("keyword", this.keyword);

        return map;
    }
}
