package com.jeman.hateoas.message;

import com.jeman.hateoas.user.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import static com.jeman.hateoas.message.MessageModelAssembler.*;

@RestController
@RequestMapping("message")
@AllArgsConstructor
public class MessageController {

    @Autowired
    private final MessageRepository messageRepository;

    @GetMapping("/{id}")
    public EntityModel<Message> getMessage(@PathVariable Long id){
        Message message = messageRepository.findById(id).get();
        return toModel(message);
    }

    @GetMapping("/chat")
    public CollectionModel<Message> getChat(@RequestParam Long from, @RequestParam Long to, @RequestParam(required = false,defaultValue = "0") Integer page, @RequestParam(required = false,defaultValue = "5") Integer pageSize){
        Page<Message> messages = messageRepository.findByFrom_IdAndTo_id(from, to, Pageable.ofSize(pageSize).withPage(page));
        CollectionModel<Message> chatCollectionModel = CollectionModel.of(
                messages.getContent(),
                linkTo(methodOn(MessageController.class).getChat(from, to, page, pageSize)).withSelfRel());
        if(messages.hasNext()) {
            chatCollectionModel.add(linkTo(methodOn(MessageController.class).getChat(from, to, page+1, pageSize)).withRel(IanaLinkRelations.NEXT));
        }
        return chatCollectionModel;
    }

    @GetMapping("/{userId}/friends")
    public CollectionModel<User> getChattedFriends(@PathVariable Long userId){
        List<User> friendsWithMessages = messageRepository.findFriendsWithMessages(userId);
        return CollectionModel.of(friendsWithMessages);
    }
}
