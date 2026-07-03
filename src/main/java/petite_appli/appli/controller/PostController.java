package petite_appli.appli.controller;

import petite_appli.appli.dto.PostDTO;
import petite_appli.appli.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // GET /api/posts
    @GetMapping
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    // GET /api/posts/1
    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    // GET /api/posts/user/1 → tous les posts de l'utilisateur 1
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostDTO>> getPostsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(postService.getPostsByUser(userId));
    }

    // POST /api/posts/user/1 → crée un post pour l'utilisateur 1
    @PostMapping("/user/{userId}")
    public ResponseEntity<PostDTO> createPost(
            @PathVariable Long userId,
            @RequestBody PostDTO dto) {
        PostDTO created = postService.createPost(userId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // PUT /api/posts/1
    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> updatePost(
            @PathVariable Long id,
            @RequestBody PostDTO dto) {
        return ResponseEntity.ok(postService.updatePost(id, dto));
    }

    // DELETE /api/posts/1
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}