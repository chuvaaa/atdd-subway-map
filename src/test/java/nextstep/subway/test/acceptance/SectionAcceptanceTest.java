package nextstep.subway.test.acceptance;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static nextstep.subway.test.acceptance.LineTestMethod.지하철노선_단일조회;
import static nextstep.subway.test.acceptance.LineTestMethod.지하철노선_목록조회;
import static nextstep.subway.test.acceptance.LineTestMethod.지하철노선_삭제;
import static nextstep.subway.test.acceptance.LineTestMethod.지하철노선_생성;
import static nextstep.subway.test.acceptance.LineTestMethod.지하철노선_수정;
import static nextstep.subway.test.acceptance.StationTestMethod.지하철역_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

/**
 * @author a1101466 on 2022/07/10
 * @project subway
 * @description
 */
public class SectionAcceptanceTest extends AcceptanceTest{

    @BeforeEach
    public void SectionTestSetUp() {

    }
    /**
     * 지하철 노선에 구간을 등록하는 기능을 구현.
     * 새로운 구간의 상행역은 해당 노선에 등록되어있는 하행 종점역이어야 한다.
     * 새로운 구간의 하행역은 해당 노선에 등록되어있는 역일 수 없다.
     * 새로운 구간 등록시 위 조건에 부합하지 않는 경우 에러 처리한다.
     */
    @Test
    @DisplayName("지하철노선을 생성한다")
    void createLine(){
        Map<String, String> params = new HashMap<>();

        params.put("downStationId"  , "4");
        params.put("upStationId"    , "2");
        params.put("distance"       , "10");

        RestAssured
                .given().log().all()
                .body(params)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/lines/1/sections")
                .then().log().all()
                .extract();

    }

    /**
     * 지하철 노선에 구간을 제거하는 기능 구현
     * 지하철 노선에 등록된 역(하행 종점역)만 제거할 수 있다. 즉, 마지막 구간만 제거할 수 있다.
     * 지하철 노선에 상행 종점역과 하행 종점역만 있는 경우(구간이 1개인 경우) 역을 삭제할 수 없다.
     * 새로운 구간 제거시 위 조건에 부합하지 않는 경우 에러 처리한다.
     */
    @Test
    @DisplayName("구간을 제거한다")
    void deleteSection(){
        Long stationId = 2L;
        Long lineId = 1L;
        RestAssured
                .given().log().all()
                .pathParam("lineId", lineId)
                .when()
                .get("/lines/{lineId}/sections?stationId="+stationId)
                .then()
                .extract();
    }
}
