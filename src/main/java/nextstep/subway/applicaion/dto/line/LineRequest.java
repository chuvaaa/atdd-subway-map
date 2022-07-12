package nextstep.subway.applicaion.dto.line;

import lombok.Getter;

@Getter
public class LineRequest {
    private String name;
    private String color;
    private Long upStationId;
    private Long downStationId;
    private Integer distance;
}
