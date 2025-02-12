package pe.edu.vallegrande.vg_ms_classroom.domain.dto;

import lombok.Data;

@Data
public class StudentDto {
    private String id;
    private String documentType;
    private String documentNumber;
    private String lastNamePaternal;
    private String lastNameMaternal;
    private String names;
}
