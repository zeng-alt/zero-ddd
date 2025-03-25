package com.zjj.main.domain.user;

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

    private final UserRepository userRepository;

    public Option<UserAgg> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
