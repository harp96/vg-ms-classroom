package pe.edu.vallegrande.vg_ms_classroom.presentation.controller;

import org.springframework.web.bind.annotation.*;

import pe.edu.vallegrande.vg_ms_classroom.application.service.ClassroomService;
import pe.edu.vallegrande.vg_ms_classroom.domain.dto.ClassroomDto;
import pe.edu.vallegrande.vg_ms_classroom.domain.dto.DidacticUnitDto;
import pe.edu.vallegrande.vg_ms_classroom.domain.model.ClassroomModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/management/${api.version}/classroom")
public class ClassroomController {

    private final ClassroomService service;

    public ClassroomController(ClassroomService service) {
        this.service = service;
    }

    @GetMapping("/findById/{id}")
    public Mono<ClassroomDto> NewFindById(@PathVariable String id) {
        return service.newFindById(id);
    }

    @GetMapping("/{id}")
    public Mono<ClassroomModel> findById(@PathVariable String id) {
        return service.findById(id);
    }

    @GetMapping("/list/active")
    public Flux<ClassroomDto> listActive() {
        return service.findByStatus("A");
    }

    @GetMapping("/list/inactive")
    public Flux<ClassroomDto> listInactive() {
        return service.findByStatus("I");
    }

    @PutMapping("/update/{id}")
    public Mono<ClassroomModel> update(@PathVariable String id, @RequestBody ClassroomModel classroom) {
        return service.update(id, classroom);
    }

    @PostMapping("/create")
    public Mono<ClassroomModel> create(@RequestBody ClassroomModel classroom) {
        return service.create(classroom);
    }

    @PutMapping("/active/{id}")
    public Mono<ClassroomModel> active(@PathVariable String id) {
        return service.changeStatus(id, "A");
    }

    @PutMapping("/inactive/{id}")
    public Mono<ClassroomModel> inactive(@PathVariable String id) {
        return service.changeStatus(id, "I");
    }

    @GetMapping("/didactic-unit/{didacticUnitId}")
    public Flux<ClassroomDto> getClassroomsByDidacticUnit(@PathVariable String didacticUnitId) {
        return service.findByDidacticUnitId(didacticUnitId);
    }

    @GetMapping("/classroom/{classroomId}/didactic-units")
    public Flux<DidacticUnitDto> getDidacticUnitsByClassroomId(@PathVariable String classroomId) {
        return service.findDidacticUnitsByClassroomId(classroomId);
    }

    @GetMapping("/study-program/{studyProgramId}/classrooms")
    public Flux<ClassroomDto> getClassroomsByStudyProgram(@PathVariable String studyProgramId) {
        return service.findClassroomsByStudyProgramId(studyProgramId);
    }
}
