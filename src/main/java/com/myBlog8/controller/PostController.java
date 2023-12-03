package com.myBlog8.controller;

import com.myBlog8.payload.PostDto;
import com.myBlog8.payload.PostResponse;
import com.myBlog8.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.naming.Binding;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

     //http://localhost:8080/api/posts

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> createPost(@Valid @RequestBody PostDto postDto,
                                              BindingResult result
                                              ){
              if(result.hasErrors()){
                  return new ResponseEntity<>(result.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
              }
        PostDto dto = postService.createPost(postDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);

    }
    //http://localhost:8080/api/posts/{postId}
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{postId}")
    public ResponseEntity<String>deletePostById(@PathVariable int postId){
        postService.deletePostBy(postId);
      return new ResponseEntity<>("Record is delete:"+postId,HttpStatus.OK);

    }
    @GetMapping("{postId}")
    public PostDto getPostById(@PathVariable int postId){
     PostDto dto=   postService.getPostById(postId);
        return dto;


    }
    //http://localhost:8080/api/posts?pageNo=0&pageSize=3&sortBy=content&sortDir=desc
    @GetMapping
    public PostResponse getAll(
            @RequestParam(value ="pageNo",defaultValue = "0",required = false) int pageNo,
            @RequestParam(value="pageSize",defaultValue = "10",required = false) int pageSize,
            @RequestParam(value="sortBy",defaultValue = "id",required = false)  String sortBy,
            @RequestParam(value="sortDir",defaultValue="asc",required=false) String sortDir
    ){
        PostResponse postResponse=  postService.getAllPost(pageNo,pageSize,sortBy,sortDir );
        return postResponse;
    }

}
