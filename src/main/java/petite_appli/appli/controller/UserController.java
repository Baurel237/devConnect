package petite_appli.appli.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import petite_appli.appli.dto.UserDTO;
import petite_appli.appli.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // GET /api/users → liste tous les utilisateurs

    @GetMapping
    public ResponseEntity<List <UserDTO>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // GET /api/users/1 → récupère l'utilisateur avec l'id 1

    @GetMapping("/{id}")
    public ResponseEntity <UserDTO> getUserById(@PathVariable Long id){
        return ResponseEntity.ok(userService.getUserById(id));
    }

    // POST /api/users → crée un nouvel utilisateur
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO dto){
        UserDTO created = userService.createUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    // PUT /api/users/1 → modifie l'utilisateur avec l'id 1
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO dto){
        return ResponseEntity.ok(userService.updateUser(id, dto));
    }
    // DELETE /api/users/1 → supprime l'utilisateur avec l'id 1

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}
