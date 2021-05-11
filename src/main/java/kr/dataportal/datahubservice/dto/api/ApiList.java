package kr.dataportal.datahubservice.dto.api;

import kr.dataportal.datahubservice.dto.PermissionGroup;
import kr.dataportal.datahubservice.dto.common.Category1st;
import kr.dataportal.datahubservice.dto.common.Category2nd;
import kr.dataportal.datahubservice.dto.dataset.DataSetList;
import kr.dataportal.datahubservice.dto.user.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class ApiList {
    private final Integer seq;
    private final String name;
    private final DataSetList targetDataset;
    private PermissionGroup permissionGroup;
    private String apiDesc;
    private Category1st category1st;
    private Category2nd category2nd;
    private String organization;
    private final User publisher;
    private final LocalDateTime publish_at;
    private final LocalDateTime last_edit;

    public ApiList() {
        this.seq = -1;
        this.name = null;
        this.targetDataset = null;
        this.permissionGroup = null;
        this.apiDesc = null;
        this.category1st = null;
        this.category2nd = null;
        this.organization = null;
        this.publisher = null;
        this.publish_at = null;
        this.last_edit = null;
    }
}
