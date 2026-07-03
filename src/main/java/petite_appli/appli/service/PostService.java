package petite_appli.appli.service;

import petite_appli.appli.dto.PostDTO;
import petite_appli.appli.entity.Post;
import petite_appli.appli.entity.User;
import petite_appli.appli.exception.ResourceNotFoundException;
import petite_appli.appli.repository.PostRepository;
import petite_appli.appli.repository.UserRepository;
import org.springframework.stereotype.Service;
import petite_appli.appli.exception.BadRequestException;


import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    // Récupérer tous les posts
    public List<PostDTO> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    // Récupérer un post par son ID
    public PostDTO getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", id));
        return convertToDTO(post);
    }

    // Récupérer tous les posts d'un utilisateur
    public List<PostDTO> getPostsByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId));
        return user.getPosts()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    // Créer un post
    public PostDTO createPost(Long userId, PostDTO dto) {
        // Règles métier
        if (dto.getTitle() == null || dto.getTitle().isBlank()) {
            throw new RuntimeException("Le titre est obligatoire");
        }
        if (dto.getContent() == null || dto.getContent().isBlank()) {
            throw new BadRequestException("Le contenu est obligatoire");

        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId));
        Post post = new Post();
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setUser(user);

        Post saved = postRepository.save(post);
        return convertToDTO(saved);
    }

    // Modifier un post
    public PostDTO updatePost(Long id, PostDTO dto) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", id));
        if (dto.getTitle() != null && !dto.getTitle().isBlank()) {
            post.setTitle(dto.getTitle());
        }
        if (dto.getContent() != null && !dto.getContent().isBlank()) {
            post.setContent(dto.getContent());
        }

        Post updated = postRepository.save(post);
        return convertToDTO(updated);
    }

    // Supprimer un post
    public void deletePost(Long id) {
        if (!postRepository.existsById(id)) {
            throw new ResourceNotFoundException("Post", id);
        }
        postRepository.deleteById(id);
    }

    // Conversion Post → PostDTO
    private PostDTO convertToDTO(Post post) {
        PostDTO dto = new PostDTO();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());

        // Nom de l'auteur
        if (post.getUser() != null) {
            dto.setAuthorName(post.getUser().getName());
        }

        // Nombre de commentaires
        if (post.getComments() != null) {
            dto.setCommentCount(post.getComments().size());
        }

        return dto;
    }
}