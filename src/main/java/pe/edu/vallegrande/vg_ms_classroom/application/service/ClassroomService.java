package pe.edu.vallegrande.vg_ms_classroom.application.service;

import org.springframework.stereotype.Service;
import pe.edu.vallegrande.vg_ms_classroom.domain.dto.*;
import pe.edu.vallegrande.vg_ms_classroom.domain.model.ClassroomModel;
import pe.edu.vallegrande.vg_ms_classroom.domain.repository.ClassroomRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ClassroomService {
    private final ExternalService service;
    private final ClassroomRepository repository;

    public ClassroomService(ExternalService service, ClassroomRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    public Flux<ClassroomDto> findByStatus(String status) {
        return repository.findByStatus(status)
                .flatMap(this::convertToDto)
                .collectList()
                .flatMapMany(Flux::fromIterable);
    }

    public Mono<ClassroomModel> findById(String id) {
        return repository.findById(id);
    }

    public Mono<ClassroomDto> newFindById(String id) {
        return repository.findById(id)
                .flatMap(this::convertToDto);
    }

    public Mono<ClassroomModel> create(ClassroomModel classroom) {
        classroom.setStatus("A");
        return repository.save(classroom);
    }

    public Mono<ClassroomModel> update(String classroomId, ClassroomModel classroom) {
        return repository.findById(classroomId)
                .flatMap(cr -> {
                    cr.setName(classroom.getName());
                    cr.setCapacity(classroom.getCapacity());
                    cr.setAcademicPeriodId(classroom.getAcademicPeriodId());
                    cr.setStudyProgramId(classroom.getStudyProgramId());
                    cr.setDidacticUnitId(classroom.getDidacticUnitId());
                    cr.setEnrollmentDetailId(classroom.getEnrollmentDetailId());
                    cr.setStatus("A");
                    return repository.save(cr);
                });
    }

    public Mono<ClassroomModel> changeStatus(String id, String status) {
        return repository.findById(id)
                .flatMap(cr -> {
                    cr.setStatus(status);
                    return repository.save(cr);
                });
    }

    private Mono<ClassroomDto> convertToDto(ClassroomModel classroom) {
        ClassroomDto dto = new ClassroomDto();
        dto.setClassroomId(classroom.getClassroomId());
        dto.setName(classroom.getName());
        dto.setCapacity(classroom.getCapacity());
        dto.setStatus(classroom.getStatus());

        Mono<AcademicPeriodDto> periodMono = service.getAcademicPeriod(classroom.getAcademicPeriodId())
                .defaultIfEmpty(new AcademicPeriodDto());

        Mono<StudyProgramDto> studyMono = service.getStudyProgram(classroom.getStudyProgramId())
                .defaultIfEmpty(new StudyProgramDto());

        Mono<DidacticUnitDto> didacticUnitDtoMono = service
                .getDidacticUnit(classroom.getDidacticUnitId())
                .defaultIfEmpty(new DidacticUnitDto());

        Flux<EnrollmentDetailDto> enrollmentDetailDtoMono = Flux.fromIterable(
                        classroom.getEnrollmentDetailId())
                .flatMap(service::getEnrollmentDetail)
                .defaultIfEmpty(new EnrollmentDetailDto());

        return Mono.zip(
                        periodMono,
                        studyMono,
                        didacticUnitDtoMono,
                        enrollmentDetailDtoMono.collectList()
                )
                .map(tuple -> {
                    AcademicPeriodDto academicPeriod = tuple.getT1();
                    StudyProgramDto studyProgram = tuple.getT2();
                    DidacticUnitDto didacticUnitDto = tuple.getT3();
                    Header header = buildHeader(academicPeriod, studyProgram, didacticUnitDto);

                    dto.setHeader(header);
                    dto.setEnrollmentDetailId(tuple.getT4());
                    return dto;
                });
    }

    private Header buildHeader(AcademicPeriodDto academicPeriod,
                               StudyProgramDto studyProgram,
                               DidacticUnitDto didacticUnitDto) {
        Header header = new Header();
        header.setAcademicPeriodId(academicPeriod.getIdAcademicPeriod());
        header.setAcademicPeriodName(academicPeriod.getAcademicPeriod());
        header.setAcademicPeriodStatus(academicPeriod.getStatus());

        header.setProgramId(studyProgram.getProgramId());
        header.setProgramName(studyProgram.getName());
        header.setProgramModule(studyProgram.getModule());
        header.setProgramStatus(studyProgram.getStatus());

        header.setDidacticId(didacticUnitDto.getDidacticId());
        header.setDidacticName(didacticUnitDto.getName());
        header.setDidacticProgramId(didacticUnitDto.getStudyProgramId());
        header.setDidacticStatus(didacticUnitDto.getStatus());
        return header;
    }

     public Flux<ClassroomDto> findByDidacticUnitId(String didacticUnitId) {
        return repository.findByDidacticUnitId(didacticUnitId)
            .flatMap(this::convertToDto);
    }

    public Flux<DidacticUnitDto> findDidacticUnitsByClassroomId(String classroomId) {
        return repository.findById(classroomId)
            .flatMapMany(classroom -> Flux.fromIterable(classroom.getEnrollmentDetailId()))
            .flatMap(service::getEnrollmentDetail)
            .flatMap(enrollmentDetail -> Flux.fromIterable(enrollmentDetail.getDidacticUnit()))
            .flatMap(didacticUnit -> service.getDidacticUnit(didacticUnit.getDidacticId()))
            .distinct();
    }

    public Flux<ClassroomDto> findClassroomsByStudyProgramId(String studyProgramId) {
        return repository.findByStudyProgramId(studyProgramId)
            .flatMap(this::convertToDto);
    }

}


