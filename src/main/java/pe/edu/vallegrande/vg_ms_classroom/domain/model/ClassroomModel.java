package pe.edu.vallegrande.vg_ms_classroom.domain.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "new_classrooms")
public class ClassroomModel {
    @Id
    private String classroomId;
    private String name;
    private String academicPeriodId;
    private String studyProgramId;
    private String didacticUnitId;
    private List<String> enrollmentDetailId;
    private int capacity;
    private String status;
}
