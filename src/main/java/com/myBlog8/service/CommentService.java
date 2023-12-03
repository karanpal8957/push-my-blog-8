package com.myBlog8.service;


import com.myBlog8.payload.CommentDto;

public interface CommentService {

 public   CommentDto saveComment(CommentDto commentDto, long postId);
 public void deleteComment(long id);
 public CommentDto updateComment(CommentDto commentDto, long id);


}
