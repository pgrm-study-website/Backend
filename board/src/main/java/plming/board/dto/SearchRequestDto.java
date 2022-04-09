package plming.board.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class SearchRequestDto {

    private String searchType;
    private String keyword;
    private List<String> category;
    private List<String> status;
    private List<Integer> tagIds;
    private List<Integer> period;
    private List<Integer> participantMax;
}
