package com.jeman.hateoas.user;

import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

public class UserModelAssembler {
    public static EntityModel<User> toModel(User user){
        EntityModel<User> model = EntityModel.of(user);
        model.add(linkTo(methodOn(UserController.class).user(user.getUuid().toString())).withSelfRel());
        model.add(linkTo(methodOn(UserController.class).allUsers()).withRel("all"));
        model.add(linkTo(methodOn(UserController.class).updateUser(user)).withRel("update"));
        model.add(linkTo(methodOn(UserController.class).friends(user.getId())).withRel("friends"));
        return model;
    }
}
