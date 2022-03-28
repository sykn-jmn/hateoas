package com.jeman.hateoas.message;

import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

public class MessageModelAssembler {

    public static EntityModel<Message> toModel(Message message){
        EntityModel<Message> model = EntityModel.of(message);
        model.add(linkTo(methodOn(MessageController.class).getMessage(message.getId())).withSelfRel());
        return model;
    }
}
