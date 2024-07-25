package com.example.fitnessonline.controllers;

import com.example.fitnessonline.model.dto.Comment;
import com.example.fitnessonline.model.dto.Image;
import com.example.fitnessonline.model.dto.Program;
import com.example.fitnessonline.model.dto.ProgramHasAttribute;
import com.example.fitnessonline.model.requests.ProgramRequest;
import com.example.fitnessonline.services.CommentService;
import com.example.fitnessonline.services.ImageService;
import com.example.fitnessonline.services.ProgramHasAttributeService;
import com.example.fitnessonline.services.ProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RestController
@RequestMapping("/programs")
public class ProgramController {
    private final ProgramService programService;
    private final CommentService commentService;
    private final ImageService imageService;
    private final ProgramHasAttributeService programHasAttributeService;

    public ProgramController(ProgramService programService, CommentService commentService, ImageService imageService, ProgramHasAttributeService programHasAttributeService) {
        this.programService = programService;
        this.commentService = commentService;
        this.imageService = imageService;
        this.programHasAttributeService = programHasAttributeService;
    }

    @GetMapping
    public List<Program> findAll(){
        return programService.findAll();
    }
    @GetMapping("/{id}")
    public Program findById(@PathVariable Integer id){
        return programService.findById(id);
    }

    @GetMapping("/{id}/comments")
    public List<Comment> getAllCommentsByProgramId(@PathVariable Integer id){
        return commentService.getAllByProgramId(id);
    }

    @GetMapping("/{id}/images")
    public List<Image> getAllImagesByProgramId(@PathVariable Integer id){
        return imageService.getAllByProgramId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Program insert(@RequestBody ProgramRequest programRequest, Authentication auth){
        return programService.insert(programRequest, auth);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id, Authentication auth){
        programService.delete(id, auth);
    }

    @GetMapping("/search/{title}")
    public Page<Program> searchProgramsByTitle(@PathVariable String title, @RequestParam int page, @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size);
        return programService.searchProgramsByTitle(title, pageable);
    }

    @GetMapping("/{id}/attributes")
    public List<ProgramHasAttribute> getAllAttributesByProgramId(@PathVariable Integer id){
        return programHasAttributeService.getAllByProgramId(id);
    }
}
