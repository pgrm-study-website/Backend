package plming.board.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchRequestDto {

    private String keyword;
    private List<String> categories;
    private List<String> status;
    private List<Integer> tags;
}
