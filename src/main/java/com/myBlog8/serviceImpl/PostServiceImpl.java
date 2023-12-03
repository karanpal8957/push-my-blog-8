package com.myBlog8.serviceImpl;

import com.myBlog8.entity.Post;
import com.myBlog8.exception.ResourceNotFound;
import com.myBlog8.payload.PostDto;
import com.myBlog8.payload.PostResponse;
import com.myBlog8.repository.PostRepository;
import com.myBlog8.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public PostDto createPost(@RequestBody PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post updatedPost = postRepository.save(post);
        PostDto  dto = new PostDto();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setDescription(post.getDescription());
        dto.setContent(post.getContent());



        return dto;
    }
    @Override
    public void deletePostBy(int postId){
     Post post=   postRepository.findById((long)postId).orElseThrow(
                ()->new ResourceNotFound("post no found with id:"+postId)
        );

        postRepository.deleteById((long)postId);
    }

    @Override
    public PostDto getPostById(int postId){
     Post post=   postRepository.findById((long)postId).orElseThrow(
             ()->new ResourceNotFound("post not found:"+postId)
     );
     return mapToDto(post);

    }

    @Override
    public PostResponse getAllPost(int pageNo, int pageSize, String sortBy, String sortDir){
      Sort sort=  sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo,pageSize, Sort.by(sortBy));
        Page<Post> all = postRepository.findAll(pageable);
        List<Post> posts = all.getContent();
        List<PostDto> dtos=  posts.stream().map(post->mapToDto(post)).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(dtos);
        postResponse.setPageNo(all.getNumber());
        postResponse.setTotalPages(all.getTotalPages());
        postResponse.setTotalElements((int)all.getTotalElements());
        postResponse.setLast(all.isLast());
    return postResponse;
    }



    public PostDto mapToDto(Post post){
        PostDto dto=  modelMapper.map(post,PostDto.class);
//        dto.setId(post.getId());
//        dto.setTitle(post.getTitle());
//        dto.setDescription(post.getDescription());
//        dto.setContent(post.getContent());
        return dto;
    }



}
