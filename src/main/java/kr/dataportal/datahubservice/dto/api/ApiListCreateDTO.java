package kr.dataportal.datahubservice.dto.api;

import kr.dataportal.datahubservice.dto.PermissionGroup;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public class ApiListCreateDTO {
    private final String name;
    private final String targetDataset;
    private final String targetColumn;
    private final PermissionGroup permissionGroup;
    private final String apiDesc;
    private final String category1st;
    private final String category2nd;
    private final String organization;
    @Setter
    private int publisher;
}
