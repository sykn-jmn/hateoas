package com.jeman.hateoas;

import com.jeman.hateoas.message.Message;
import com.jeman.hateoas.message.MessageRepository;
import com.jeman.hateoas.user.User;
import com.jeman.hateoas.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@AllArgsConstructor
public class Loader implements CommandLineRunner {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final MessageRepository messageRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        Random rand = new Random();
        List<User> users = new ArrayList<>();
        users.add(new User("Jeman Mama"));
        users.add(new User("Jejemon Pikachu"));
        users.add(new User("Charizard Marawi"));
        users.add(new User("Diglet Raichu"));
        users.add(new User("Charmander Squirtle"));
        users.add(new User("Dead lizard"));
        users.add(new User("PC Cover"));
        users = userRepository.saveAll(users);
        users.get(0).addFriend(users.get(1));
        users.get(0).addFriend(users.get(2));
        List<Message> messages = new ArrayList<>();
        for (int j = 0; j <users.size(); j++){
            for(int i = 0; i <100; i++) {
                int randomIndex = rand.nextInt(users.size());
                if(randomIndex==j) {
                    continue;
                }
                User user = users.get(j);
                messages.add(new Message(user, users.get(randomIndex), LocalDateTime.now(), "I am very handsome"));
            }
        }
        messageRepository.saveAll(messages);
    }
}
