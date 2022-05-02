package plming.board.boardSearch.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
public class SearchRequestDto {

    private String searchType;
    private String keyword;
    private List<String> category;
    private List<String> status;
    private List<Integer> tagIds;
    private List<Integer> period;
    private List<Integer> participantMax;

    @Builder
    public SearchRequestDto(String searchType, String keyword, List<String> category, List<String> status, List<Integer> tagIds, List<Integer> period, List<Integer> participantMax) {
        this.searchType = searchType;
        this.keyword = keyword;
        this.category = category;
        this.status = status;
        this.tagIds = tagIds;
        this.period = period;
        this.participantMax = participantMax;
    }
}