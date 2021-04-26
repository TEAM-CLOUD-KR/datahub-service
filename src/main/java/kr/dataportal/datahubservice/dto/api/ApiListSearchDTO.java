package kr.dataportal.datahubservice.dto.api;

import kr.dataportal.datahubservice.dto.PermissionGroup;
import kr.dataportal.datahubservice.dto.common.Category1st;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@Getter
public class ApiListSearchDTO {
    private final Integer page;
    private final Integer itemPerPage;
    private final PermissionGroup permissionGroup;
    private final String category;
    private final String organization;

    public ApiListSearchDTO(Integer page, Integer itemPerPage, PermissionGroup permissionGroup, String category, String organization) {
        this.page = Objects.requireNonNullElse(page, 1);
        this.itemPerPage = Objects.requireNonNullElse(itemPerPage, 10);
        this.permissionGroup = Objects.requireNonNullElse(permissionGroup, PermissionGroup.PERMISSION_PUBLIC);
        this.category = Objects.requireNonNullElse(category, "");
        this.organization = Objects.requireNonNullElse(organization, "");
    }
}
