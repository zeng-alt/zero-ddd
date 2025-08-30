package com.zjj.camunda.infrastructure.identity;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.impl.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年08月27日 21:01
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {

    private final UserRepository userRepository;

    public long countUsers(String userId, String userEmail, String userFirstName, String groupId) {
        return 1;
    }

    public List<MainUser> findUsers(String userId, String userEmail, String userFirstName, String groupId, Page page) {
        MainUser mainUser = new MainUser();
        mainUser.setId(userId);
        mainUser.setFirstName("admin");
        mainUser.setLastName("admin");

        return List.of(mainUser);
    }

    public boolean validatePassword(String userId, String password) {
        return true;
    }

    public MainUser findById(String userId) {
        MainUser mainUser = new MainUser();
        mainUser.setId(userId);
        mainUser.setFirstName("admin");
        mainUser.setLastName("admin");
        return mainUser;
    }
}
