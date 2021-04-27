package kr.dataportal.datahubservice.dto.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
public class ApiListSearchDTO {
    private final Integer page;
    private final Integer itemPerPage;
    private final List<String> ownDatahub;
    private final List<String> category;
    private final List<String> organization;
    private final String name;

    public ApiListSearchDTO(Integer page, Integer itemPerPage, List<String> ownDatahub,
                            List<String> category, List<String> organization, String name) {
        this.page = Objects.requireNonNullElse(page, 1);
        this.itemPerPage = Objects.requireNonNullElse(itemPerPage, 10);
        this.ownDatahub = Objects.requireNonNullElse(ownDatahub, new ArrayList<String>());
        this.category = Objects.requireNonNullElse(category, new ArrayList<String>());
        this.organization = Objects.requireNonNullElse(organization, new ArrayList<String>());
        this.name = Objects.requireNonNullElse(name, "");
    }

    public static ApiListSearchDTO createDefault() {
        return new ApiListSearchDTO(null, null, null, null,
                null, null);
    }
}
