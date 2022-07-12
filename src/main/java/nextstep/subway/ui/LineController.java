package nextstep.subway.ui;

import nextstep.subway.applicaion.LineService;
import nextstep.subway.applicaion.dto.line.LineRequest;
import nextstep.subway.applicaion.dto.line.LineResponse;
import nextstep.subway.applicaion.dto.section.SectionRequest;
import nextstep.subway.applicaion.dto.section.SectionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RequestMapping("/lines")
@RestController
public class LineController {
    private final LineService lineService;

    public LineController(LineService lineService) {
        this.lineService = lineService;
    }

    @PostMapping
    public ResponseEntity<LineResponse> createLine(@RequestBody LineRequest lineRequest) {
        LineResponse line = lineService.saveLine(lineRequest);
        return ResponseEntity.created(URI.create("/lines/" + line.getId())).body(line);
    }

    @GetMapping
    public ResponseEntity<List<LineResponse>> getLineAll() {
        List<LineResponse> line = lineService.findAllLine();
        return ResponseEntity.ok().body(line);
    }

    @GetMapping("/{lineId}")
    public ResponseEntity<LineResponse> getLine(@PathVariable Long lineId) {
        LineResponse line = lineService.findLineById(lineId);
        return ResponseEntity.ok().body(line);
    }

    @PutMapping("/{lineId}")
    public void modifyLineInfo(@PathVariable Long lineId
            , @RequestBody LineRequest lineRequest) {
        lineService.modifyLine(lineId, lineRequest);
    }

    @DeleteMapping("/{lineId}")
    public ResponseEntity<Void> deleteLine(@PathVariable Long lineId) {
        lineService.deleteLineById(lineId);
        return ResponseEntity.noContent().build();
    }

    // section 기능
    @PostMapping(value = "{lineId}/sections")
    public ResponseEntity<Void> createSection(
            @RequestBody SectionRequest sectionRequest
            , @PathVariable Long lineId) {
        lineService.saveSection(lineId, sectionRequest);
        return ResponseEntity.ok().build();
    }
    @GetMapping(value = "/section/{selectionId}")
    public ResponseEntity<SectionResponse> getSection(@PathVariable long sectionId) {
        return ResponseEntity.ok().body(lineService.findSectionById(sectionId));
    }
}
