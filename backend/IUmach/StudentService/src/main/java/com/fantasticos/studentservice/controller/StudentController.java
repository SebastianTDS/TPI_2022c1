package com.fantasticos.studentservice.controller;

import com.fantasticos.studentservice.dto.DtoRequestStudent;
import com.fantasticos.studentservice.dto.DtoStudent;
import com.fantasticos.studentservice.dto.DtoUser;
import com.fantasticos.studentservice.model.Student;
import com.fantasticos.studentservice.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;
    @Autowired
    private DtoUser user;

    @GetMapping("/version")
    public ResponseEntity<String> version() {
        return ResponseEntity.ok("Hola StudentController Version");
    }

    @GetMapping("/my-id")
    public ResponseEntity<DtoUser> myId() {
        studentService.findById(user.getUserName());
        return ResponseEntity.ok(user);
    }
    
    @GetMapping("/id")
    public ResponseEntity<DtoUser> Id() {
        return ResponseEntity.ok(user);
    }

    @GetMapping("/get-user")
    public ResponseEntity<DtoRequestStudent> getUser() {
        return ResponseEntity.ok(studentService.findById(user.getUserName()));
    }

    @PostMapping("/get-user/{id}")
    public ResponseEntity<DtoRequestStudent> getUser(@PathVariable("id") String userId) {
        return ResponseEntity.ok(studentService.findById(userId));
    }
    
    @GetMapping("/check-status/{email}")
    public ResponseEntity<DtoRequestStudent> checkifBanned(@PathVariable("email") String email) {
        return ResponseEntity.ok(studentService.findByEmail(email));
    }

    @PostMapping("/create-user")
    public ResponseEntity<Student> createUser(@RequestBody DtoStudent dtoStudent , @RequestHeader("Authorization") String bearerToken) {
        dtoStudent.setId(user.getUserName());
        return new ResponseEntity<>(studentService.save(dtoStudent, bearerToken), HttpStatus.OK);
    }
}
