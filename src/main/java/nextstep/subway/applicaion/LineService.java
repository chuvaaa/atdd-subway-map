package nextstep.subway.applicaion;

import nextstep.subway.applicaion.dto.line.LineRequest;
import nextstep.subway.applicaion.dto.line.LineResponse;
import nextstep.subway.applicaion.dto.section.SectionRequest;
import nextstep.subway.applicaion.dto.section.SectionResponse;
import nextstep.subway.applicaion.dto.station.StationResponse;
import nextstep.subway.domain.Line;
import nextstep.subway.domain.LineRepository;
import nextstep.subway.domain.Section;
import nextstep.subway.domain.SectionRepository;
import nextstep.subway.domain.Station;
import nextstep.subway.domain.StationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional
public class LineService {
    private final LineRepository lineRepository;
    private final SectionRepository sectionRepository;
    private final StationRepository stationRepository;

    public LineService(LineRepository lineRepository, SectionRepository sectionRepository, StationRepository stationRepository) {
        this.lineRepository = lineRepository;
        this.sectionRepository = sectionRepository;
        this.stationRepository = stationRepository;
    }

    public LineResponse saveLine(LineRequest lineRequest) {
        Line saveLine = lineRepository.save(
                new Line(lineRequest.getName(), lineRequest.getColor(),
                        new Section(saveLine, lineRequest.getUpStationId(), lineRequest.getDownStationId(), lineRequest.getDistance()))
        );
        List<Section> sections = sectionRepository.findByLineId(saveLine.getId());
        sections.forEach(System.out::println);
        /*saveSection(saveLine.getId(), new SectionRequest(saveLine.getDownStationId(), saveLine.getUpStationId()
                    , saveLine.getDistance()));*/

        return LineResponse.of(saveLine, findAllStationUsingLine(saveLine));
    }

    @Transactional(readOnly = true)
    public LineResponse findLineById(long id) {
        Line line = lineRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("노선을 찾을 수 없습니다."));

        return LineResponse.of(line, findAllStationUsingLine(line));
    }

    @Transactional(readOnly = true)
    public List<LineResponse> findAllLine(){
        return lineRepository.findAll()
                .stream()
                .map(line -> LineResponse.of(line, findAllStationUsingLine(line)))
                .collect(Collectors.toList());
    }

    public void modifyLine(Long lineId, LineRequest lineRequest){
        Line line = lineRepository.findById(lineId)
                .orElseThrow(() -> new NoSuchElementException("노선을 찾을 수 없습니다."));

        line.modify(lineRequest.getName(), lineRequest.getColor());
    }


    public void deleteLineById(Long lineId) {
        lineRepository.deleteById(lineId);
    }

    private List<StationResponse> findAllStationUsingLine(Line line){
        return stationRepository.findAllById(
                        List.of(line.getUpStationId(), line.getDownStationId()))
                .stream()
                .map(station -> StationResponse.of(station))
                .collect(Collectors.toList());
    }

    public void saveSection(Long lineId, SectionRequest sectionRequest){
        Line line = lineRepository.findById(lineId)
                .orElseThrow(() -> new NoSuchElementException("노선을 찾을 수 없습니다."));

        line.addSection(new Section(line, sectionRequest.getUpStationId(), sectionRequest.getDownStationId()
                                    , sectionRequest.getDistance()));

    }

    public SectionResponse findSectionById(long sectionId) {
        return SectionResponse.of(sectionRepository.findById(sectionId)
                .orElseThrow(() -> new NoSuchElementException("해당 구간을 찾을수 없습니다.")));
    }
}
