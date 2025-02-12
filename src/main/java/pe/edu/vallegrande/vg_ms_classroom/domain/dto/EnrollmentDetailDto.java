package pe.edu.vallegrande.vg_ms_classroom.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class EnrollmentDetailDto {
    private String id;
    private StudentDto student;
    private List<CustomDidacticUnitDto> didacticUnit;
    private String status;
}

