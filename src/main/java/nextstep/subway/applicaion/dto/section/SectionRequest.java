package nextstep.subway.applicaion.dto.section;

import lombok.Getter;

@Getter
public class SectionRequest {
    Long downStationId;
    Long upStationId;
    Integer distance;

    public SectionRequest(Long downStationId, Long upStationId, Integer distance) {
        this.downStationId = downStationId;
        this.upStationId = upStationId;
        this.distance = distance;
    }
}
