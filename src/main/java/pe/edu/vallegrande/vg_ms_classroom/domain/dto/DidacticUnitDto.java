package pe.edu.vallegrande.vg_ms_classroom.domain.dto;

import lombok.Data;

@Data
public class DidacticUnitDto {
    private String didacticId;
    private String name;
    private String studyProgramId;
    private String credit;
    private String hours;
    private String condition;
    private String status;
}
