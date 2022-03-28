package com.jeman.hateoas.user;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.*;
import static com.jeman.hateoas.user.UserModelAssembler.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("users")
@AllArgsConstructor
public class UserController {

    @Autowired
    private final UserRepository userRepository;

    @GetMapping("")
    public CollectionModel<EntityModel<User>> allUsers(){
        List<User> users = userRepository.findAll();
        List<EntityModel<User>> userModels = users.stream().map(UserModelAssembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(userModels,
                linkTo(methodOn(UserController.class).allUsers()).withSelfRel());
    }

    @GetMapping("/{uuid}")
    public EntityModel<User> user(@PathVariable String uuid){
        User user = userRepository.findByUuid(UUID.fromString(uuid));
        return toModel(user);
    }

    @GetMapping("/{id}/friends")
    public CollectionModel<User> friends(@PathVariable Long id){
        Collection<User> friends = userRepository.findById(id).get().getFriends();
        return CollectionModel.of(
                friends,
                linkTo(methodOn(UserController.class).friends(id)).withSelfRel());
    }

    @PutMapping("/")
    public User updateUser(User user){
        return userRepository.save(user);
    }
}
