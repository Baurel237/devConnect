package petite_appli.appli.controller;

import petite_appli.appli.dto.CommentDTO;
import petite_appli.appli.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // GET /api/comments
    @GetMapping
    public ResponseEntity<List<CommentDTO>> getAllComments() {
        return ResponseEntity.ok(commentService.getAllComments());
    }

    // GET /api/comments/1
    @GetMapping("/{id}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable Long id) {
        return ResponseEntity.ok(commentService.getCommentById(id));
    }

    // GET /api/comments/post/1 → tous les commentaires du post 1
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentDTO>> getCommentsByPost(@PathVariable Long postId) {
        return ResponseEntity.ok(commentService.getCommentsByPost(postId));
    }

    // GET /api/comments/search?keyword=spring → recherche par mot clé
    @GetMapping("/search")
    public ResponseEntity<List<CommentDTO>> searchComments(@RequestParam String keyword) {
        return ResponseEntity.ok(commentService.searchComments(keyword));
    }

    // POST /api/comments/post/1 → commente le post 1
    @PostMapping("/post/{postId}")
    public ResponseEntity<CommentDTO> createComment(
            @PathVariable Long postId,
            @RequestBody CommentDTO dto) {
        CommentDTO created = commentService.createComment(postId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // PUT /api/comments/1
    @PutMapping("/{id}")
    public ResponseEntity<CommentDTO> updateComment(
            @PathVariable Long id,
            @RequestBody CommentDTO dto) {
        return ResponseEntity.ok(commentService.updateComment(id, dto));
    }

    // DELETE /api/comments/1
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}