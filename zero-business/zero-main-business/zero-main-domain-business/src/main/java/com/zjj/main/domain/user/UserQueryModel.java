package com.zjj.main.domain.user;

import com.zjj.autoconfigure.component.security.UserProfile;
import com.zjj.main.domain.user.model.UserRecord;
import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import org.jmolecules.architecture.cqrs.QueryModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年03月24日 16:46
 */
@Service
@QueryModel
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserQueryModel {

    private final UserReadRepository userRepository;

    public Option<UserRecord> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public UserProfile findProfileByUsername(String username) {
        return userRepository.findProfileByUsername(username);
    }
}
