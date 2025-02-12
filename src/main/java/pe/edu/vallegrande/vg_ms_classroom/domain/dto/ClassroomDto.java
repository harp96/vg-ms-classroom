package pe.edu.vallegrande.vg_ms_classroom.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class ClassroomDto {
    private String classroomId;
    private String name;
    private Header Header;
    private List<EnrollmentDetailDto> enrollmentDetailId;
    private int capacity;
    private String status;
}