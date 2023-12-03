package com.myBlog8.service;

import com.myBlog8.payload.PostDto;
import com.myBlog8.payload.PostResponse;

import java.util.List;

public interface PostService {

    public PostDto createPost(PostDto postDto);

    public void deletePostBy(int postId);

    public PostDto getPostById(int postId);



  public PostResponse getAllPost(int pageNo, int pageSize, String sortBy, String sortDir);



}

