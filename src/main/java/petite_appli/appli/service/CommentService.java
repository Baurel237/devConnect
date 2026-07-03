package petite_appli.appli.service;

import petite_appli.appli.dto.CommentDTO;
import petite_appli.appli.entity.Comment;
import petite_appli.appli.entity.Post;
import petite_appli.appli.repository.CommentRepository;
import petite_appli.appli.repository.PostRepository;
import org.springframework.stereotype.Service;
import petite_appli.appli.exception.BadRequestException;
import petite_appli.appli.exception.ResourceNotFoundException;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentService(CommentRepository commentRepository,
                          PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    // Tous les commentaires
    public List<CommentDTO> getAllComments() {
        return commentRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    // Un commentaire par son ID
    public CommentDTO getCommentById(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", id));
        return convertToDTO(comment);
    }

    // Tous les commentaires d'un post
    public List<CommentDTO> getCommentsByPost(Long postId) {
        if (!postRepository.existsById(postId)) {
            throw new ResourceNotFoundException("Post", postId);
        }
        return commentRepository.findByPostId(postId)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    // Rechercher des commentaires par mot clé
    public List<CommentDTO> searchComments(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            throw new BadRequestException("Le mot clé de recherche est obligatoire");
        }
        return commentRepository.searchByKeyword(keyword)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    // Créer un commentaire sur un post
    public CommentDTO createComment(Long postId, CommentDTO dto) {
        // Règles métier
        if (dto.getText() == null || dto.getText().isBlank()) {
            throw new BadRequestException("Le texte du commentaire est obligatoire");
        }

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", postId));

        Comment comment = new Comment();
        comment.setText(dto.getText());
        comment.setPost(post);

        Comment saved = commentRepository.save(comment);
        return convertToDTO(saved);
    }

    // Modifier un commentaire
    public CommentDTO updateComment(Long id, CommentDTO dto) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", id));

        if (dto.getText() == null || dto.getText().isBlank()) {
            throw new BadRequestException("Le texte du commentaire est obligatoire");
        }

        comment.setText(dto.getText());
        Comment updated = commentRepository.save(comment);
        return convertToDTO(updated);
    }

    // Supprimer un commentaire
    public void deleteComment(Long id) {
        if (!commentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Comment", id);
        }
        commentRepository.deleteById(id);
    }

    // Conversion Comment → CommentDTO
    private CommentDTO convertToDTO(Comment comment) {
        CommentDTO dto = new CommentDTO();
        dto.setId(comment.getId());
        dto.setText(comment.getText());

        if (comment.getPost() != null) {
            dto.setPostId(comment.getPost().getId());
            dto.setPostTitle(comment.getPost().getTitle());
        }

        return dto;
    }
}