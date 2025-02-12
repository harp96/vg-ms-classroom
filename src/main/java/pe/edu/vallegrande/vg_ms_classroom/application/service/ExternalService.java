package pe.edu.vallegrande.vg_ms_classroom.application.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.slf4j.Slf4j;
import pe.edu.vallegrande.vg_ms_classroom.domain.dto.AcademicPeriodDto;
import pe.edu.vallegrande.vg_ms_classroom.domain.dto.DidacticUnitDto;
import pe.edu.vallegrande.vg_ms_classroom.domain.dto.EnrollmentDetailDto;
import pe.edu.vallegrande.vg_ms_classroom.domain.dto.StudyProgramDto;
import reactor.core.publisher.Mono;


@Slf4j
@Service
public class ExternalService {

    @Value("${services.study-program.url}")
    private String studyProgramUrl;

    @Value("${services.acdemic-period.url}")
    private String academicPeriodUrl;

    @Value("${services.enrollment-detail.url}")
    private String enrollmentDetailUrl;

    @Value("${services.didactic-unit.url}")
    private String didacticUnitUrl;

    private final WebClient.Builder webClientBuilder;

    public ExternalService(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public Mono<EnrollmentDetailDto> getEnrollmentDetail(String enrollmentId) {
        return fetchData(enrollmentDetailUrl + "/findById/",
                enrollmentId, EnrollmentDetailDto.class);
    }

    public Mono<DidacticUnitDto> getDidacticUnit(String didacticUnitId) {
        return fetchData(didacticUnitUrl + "/",
                didacticUnitId, DidacticUnitDto.class);
    }

    public Mono<StudyProgramDto> getStudyProgram(String programId) {
        return fetchData(studyProgramUrl + "/",
                programId, StudyProgramDto.class);
    }

    public Mono<AcademicPeriodDto> getAcademicPeriod(String idAcademicPeriod) {
        return fetchData(academicPeriodUrl + "/id/",
                idAcademicPeriod, AcademicPeriodDto.class);
    }

    private <T> Mono<T> fetchData(String baseUrl, String id, Class<T> responseType) {
        return webClientBuilder.build()
                .get()
                .uri(baseUrl + id)
                .retrieve()
                .bodyToMono(responseType)
                .onErrorResume(e -> {
                    log.error("Error fetching data: ", e);
                    return Mono.empty();
                });
    }

}