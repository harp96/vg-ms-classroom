package pe.edu.vallegrande.vg_ms_classroom.domain.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import pe.edu.vallegrande.vg_ms_classroom.domain.model.ClassroomModel;
import reactor.core.publisher.Flux;

@Repository
public interface ClassroomRepository extends ReactiveMongoRepository<ClassroomModel, String> {
    Flux<ClassroomModel> findByStatus(String status);
    Flux<ClassroomModel> findByDidacticUnitId(String didacticUnitId);
    Flux<ClassroomModel> findByStudyProgramId(String studyProgramId);
}
