package petite_appli.appli.service;


import org.springframework.stereotype.Service;
import petite_appli.appli.dto.UserDTO;
import petite_appli.appli.entity.User;
import petite_appli.appli.exception.ResourceNotFoundException;
import petite_appli.appli.repository.UserRepository;
import petite_appli.appli.exception.BadRequestException;


import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //recuperer tous les users
    public List<UserDTO> getAllUsers(){
        return userRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }
    // Récupérer un utilisateur par son ID
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", id));
        return convertToDTO(user);
    }

    // creer un user
    public UserDTO createUser(UserDTO dto){
        // le nom ne peux pas etre vide
        if (dto.getName() == null || dto.getName().isBlank()){
             throw new BadRequestException("Le nom de l'utilisateur est obligatoire");
        }
        User user = new User();
        user.setName(dto.getName());
        User saved = userRepository.save(user);
        return convertToDTO(saved);

    }

    //modifier un user

    public UserDTO updateUser(Long id, UserDTO dto){
        User user = userRepository.findById(id)
                 .orElseThrow(() -> new ResourceNotFoundException("User", id));

        user.setName(dto.getName());
        User updated = userRepository.save(user);
        return convertToDTO(updated);
    }

    //suprimer un utilisateur
    public void deleteUser(Long id){
        if (!userRepository.existsById(id)){
             throw new ResourceNotFoundException("User", id);
        }
        userRepository.deleteById(id);
    }

    // Méthode privée : convertit un User en UserDTO
    private UserDTO convertToDTO(User user){
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        //si l'utilisateur a un profil ,on recupére sa bio
        if (user.getProfile() != null){
            dto.setBio(user.getProfile().getBio());
        }
        return  dto;
    }
}
