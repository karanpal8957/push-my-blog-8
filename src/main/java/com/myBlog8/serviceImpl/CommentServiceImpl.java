package com.myBlog8.serviceImpl;

import com.myBlog8.entity.Comment;
import com.myBlog8.entity.Post;
import com.myBlog8.exception.ResourceNotFound;
import com.myBlog8.payload.CommentDto;
import com.myBlog8.repository.CommentRepository;
import com.myBlog8.repository.PostRepository;
import com.myBlog8.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

   private PostRepository postRepo;
    private CommentRepository commentRepo;

    private ModelMapper modelMapper;

    public CommentServiceImpl(CommentRepository commentRepo,PostRepository postRepo,ModelMapper modelMapper){
        this.commentRepo=commentRepo;
        this.postRepo=postRepo;
        this.modelMapper=modelMapper;

    }


    @Override
    public   CommentDto saveComment(CommentDto commentDto, long postId) {
       Post post =  postRepo.findById(postId).orElseThrow(
               ()-> new ResourceNotFound("post not found by id:"+postId)
       );
//       Comment comment = new Comment();
//       comment.setId(commentDto.getId());
//       comment.setName(commentDto.getName());
//       comment.setEmail(commentDto.getEmail());
//       comment.setBody(commentDto.getBody());
//       comment.setPost(post);
       Comment comment = mapToComment(commentDto);
       comment.setPost(post);

      Comment saveComment= commentRepo.save(comment);
      CommentDto dto = new CommentDto();
      dto.setId(saveComment.getId());
        dto.setName(saveComment.getName());
        dto.setBody(saveComment.getBody());
        dto.setEmail(saveComment.getEmail());
        return dto;

    }
    @Override
    public void deleteComment(long id){
       Comment comment= commentRepo.findById(id).orElseThrow(
                ()->new ResourceNotFound("comment not found id:"+id)
        );
        commentRepo.deleteById(id);

    }
    @Override
    public CommentDto updateComment(CommentDto commentDto, long id){
    Comment comment=    commentRepo.findById(id).orElseThrow(
                ()-> new ResourceNotFound("post not found by id:"+id)
        );
    comment.setName(commentDto.getName());
    comment.setEmail(commentDto.getEmail());
    comment.setBody(commentDto.getBody());
 Comment saveComment=   commentRepo.save(comment);
 CommentDto dto = new CommentDto();
 dto.setId(saveComment.getId());
 dto.setName(saveComment.getName());
 dto.setEmail(saveComment.getEmail());
 dto.setBody(saveComment.getBody());
 return dto;

    }
    public Comment mapToComment(CommentDto commentDto){
    Comment comment=    modelMapper.map(commentDto,Comment.class);
//        Comment comment = new Comment();
//        comment.setId(commentDto.getId());
//        comment.setName(commentDto.getName());
//        comment.setEmail(commentDto.getEmail());
//        comment.setBody(commentDto.getBody());
        return comment;

    }

}
