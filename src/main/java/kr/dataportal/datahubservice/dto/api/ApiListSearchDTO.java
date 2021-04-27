package kr.dataportal.datahubservice.dto.api;

import kr.dataportal.datahubservice.dto.common.Category1st;
import kr.dataportal.datahubservice.dto.datahub.DatahubList;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class ApiListSearchDTO {
    public final List<DatahubList> ownDatahub;
    public final List<Category1st> category;
    public final List<String> organization;
    public final Long itemCount;
    public final List<ApiList> items;

    public ApiListSearchDTO() {
        this.ownDatahub = new ArrayList<DatahubList>();
        this.category = new ArrayList<Category1st>();
        this.organization = new ArrayList<String>();
        this.itemCount = 0L;
        this.items = new ArrayList<ApiList>();
    }
}
