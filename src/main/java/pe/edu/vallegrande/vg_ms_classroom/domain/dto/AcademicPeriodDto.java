package pe.edu.vallegrande.vg_ms_classroom.domain.dto;

import lombok.Data;

@Data
public class AcademicPeriodDto {
    private String idAcademicPeriod;
    private String academicPeriod;
    private String startDate;
    private String endDate;
    private String shift;
    private String cluster;
    private String status;

}
