package nextstep.subway.test.acceptance;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

/**
 * @author a1101466 on 2022/07/10
 * @project subway
 * @description
 */
public class LineTestMethod {
    public static ExtractableResponse<Response> 지하철노선_생성(String lineName, String upStationId, String downStationId,
                                           String color, String distance){
        Map<String, String> params = new HashMap<>();
        params.put("name", lineName);
        params.put("color", color);
        params.put("upStationId", upStationId);
        params.put("downStationId", downStationId);
        params.put("distance", distance);

        return RestAssured
                .given().log().all()
                .body(params)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/lines")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 지하철노선_목록조회(){
        return RestAssured
                .given().log().all()
                .when()
                .get("/lines")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 지하철노선_단일조회(Long lineId){
        return RestAssured
                .given().log().all()
                .pathParam("lineId", lineId)
                .when()
                .get("/lines/{lineId}")
                .then().log().all()
                .extract();
    }

    public static void 지하철노선_수정(Map<String , String> params, Long lineId){
        RestAssured
                .given().log().all()
                .body(params)
                .pathParam("lineId", lineId)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .put("/lines/{lineId}")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 지하철노선_삭제(Long lineId){
        return RestAssured
                .given().log().all()
                .pathParam("lineId", lineId)
                .when()
                .delete("/lines/{lineId}")
                .then().log().all()
                .extract();
    }
}
