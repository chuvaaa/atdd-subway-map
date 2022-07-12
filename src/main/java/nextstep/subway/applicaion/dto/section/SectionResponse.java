package nextstep.subway.applicaion.dto.section;

import lombok.Builder;
import lombok.Getter;
import nextstep.subway.domain.Section;

@Getter
public class SectionResponse {
    Long sectionId;
    Long lineId;
    Long upStationId;
    Long downStationId;
    Integer distance;

    public SectionResponse(Long sectionId, Long lineId, Long upStationId, Long downStationId, Integer distance) {
        this.sectionId = sectionId;
        this.lineId = lineId;
        this.upStationId = upStationId;
        this.downStationId = downStationId;
        this.distance = distance;
    }

    public static SectionResponse of(Section section) {
        return new SectionResponse(section.getId(), section.getLineId(), section.getUpStationId(), section.getDownStationId()
                , section.getDistance());
    }

}
