package com.petwellnes.petwellnes_backend.controller;

import com.petwellnes.petwellnes_backend.model.dto.postDto.*;
import com.petwellnes.petwellnes_backend.model.entity.Comment;
import com.petwellnes.petwellnes_backend.model.entity.Post;
import com.petwellnes.petwellnes_backend.model.entity.User;
import com.petwellnes.petwellnes_backend.service.CommentService;
import com.petwellnes.petwellnes_backend.service.PostService;
import com.petwellnes.petwellnes_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "URL FRONT")
@RequestMapping("/api/v1/post")
public class PostController {

    private final PostService postService;
    private final UserService userService;
    private final CommentService commentService;

    @Autowired
    public PostController(PostService postService, UserService userService, CommentService commentService) {
        this.postService = postService;
        this.userService = userService;
        this.commentService = commentService;
    }

    // Create Post
    @PostMapping("/discover/{id}/create_post")
    public ResponseEntity<?> createPost(@RequestBody CreatePostDTO createPostDTO, @PathVariable Long id) {
        try {
            User user = userService.getAuthUser(); // Obtenemos el usuario autenticado

            // Validations
            if (createPostDTO.getContent() == null || createPostDTO.getContent().isEmpty()) {
                return ResponseEntity.badRequest().body("All required fields must be filled.");
            }

            if (createPostDTO.getContent().length() < 100) {
                return ResponseEntity.badRequest().body("Content must be at least 100 characters long.");
            }

            Post post = postService.createPost(new Post(user, createPostDTO.getCategory(), createPostDTO.getTopic(), createPostDTO.getPetType(),
                    createPostDTO.getImage(), createPostDTO.getVideo(), createPostDTO.getContent(), createPostDTO.getAnimalBreed()));

            return ResponseEntity.ok(new CreatedPostDTO(post.getDate(), post.getTime(), post.getCategory(), post.getUser().getUserId(),
                    post.getTopic().getId(), post.getPetType().getId(), post.getContent(),
                    post.getImage(), post.getReactions(), post.getRace() != null ? post.getRace().getId() : null));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }

    // View My Posts
    @GetMapping("/discover/{id}/my_posts")
    public ResponseEntity<List<PostDTO>> myPosts(@PathVariable Long id) {
        try {
            User user = userService.getAuthUser(); // Obtenemos el usuario autenticado
            List<PostDTO> posts = postService.viewMyPosts(user);
            return ResponseEntity.ok(posts);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    // Comment on Post
    @PostMapping("/discover/{idU}/comment/{idP}")
    public ResponseEntity<?> comment(@RequestBody String content, @PathVariable Long idU, @PathVariable Long idP) {
        try {
            if (content.length() > 1000) { // Assuming a 1000-character limit for comments
                return ResponseEntity.badRequest().body("Comment exceeds the character limit.");
            }

            User user = userService.getAuthUser(); // Obtenemos el usuario autenticado
            Post post = postService.findPost(idP);
            Comment comment = new Comment(content, user, post);
            commentService.comment(comment);
            return ResponseEntity.ok(new CreatedCommentDTO(comment.getContent(), comment.getId(), comment.getUser().getUserId(), comment.getPost().getId(), user.getUsername()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }

    // View Comments
    @GetMapping("/discover/{id}/view_comments")
    public ResponseEntity<?> viewComments(@PathVariable Long id) {
        try {
            Post post = postService.findPost(id);
            List<CommentDTO> comments = commentService.findAllByPost(post);
            if (comments.isEmpty()) {
                return ResponseEntity.ok("Be the first to comment.");
            }
            return ResponseEntity.ok(comments);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }

    // React to Post
    @PutMapping("/discover/{id}/react")
    public ResponseEntity<Integer> reactPost(@PathVariable Long id) {
        try {
            int reactions = postService.reactPost(id);
            return ResponseEntity.ok(reactions);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    // Filter Posts
    @GetMapping("/discover/{category}/{petType}/{breed}/{topic}")
    public ResponseEntity<List<PostDTO>> filterPost(@PathVariable String category, @PathVariable String petType, @PathVariable String breed, @PathVariable String topic) {
        try {
            List<PostDTO> posts = postService.filterPosts(category, petType, breed, topic);
            return ResponseEntity.ok(posts);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    // Delete Post
    @DeleteMapping("/discover/{id}/delete")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        try {
            Post post = postService.findPost(id);
            if (post != null) {
                postService.deletePost(post);
                return ResponseEntity.ok("Post deleted successfully.");
            } else {
                return ResponseEntity.status(404).body("Post not found.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }

}


