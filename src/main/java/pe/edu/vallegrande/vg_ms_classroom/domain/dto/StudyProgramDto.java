package pe.edu.vallegrande.vg_ms_classroom.domain.dto;

import lombok.Data;

@Data
public class StudyProgramDto {
    private String programId;
    private String name;
    private String module;
    private String trainingLevel;
    private String studyPlanType;
    private String credits;
    private String hours;
    private String status;
    private String cetproId;

}
