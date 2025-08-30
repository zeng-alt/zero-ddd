package com.zjj.camunda.infrastructure.identity;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.impl.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zengJiaJun
 * @version 1.0
 * @crateTime 2025年08月27日 17:01
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GroupService {

    private final GroupRepository groupRepository;

    public long countGroups(String groupId, String groupName, String userId) {
        return 1;
    }

    public List<MainGroup> findGroups(String groupId, String groupName, String userId, Page page) {
        MainGroup mainGroup = new MainGroup();
        mainGroup.setId("admin");
        mainGroup.setType("admin");
        mainGroup.setName("管理员");
        return List.of(mainGroup);
    }

    public MainGroup findById(String groupId) {
        MainGroup mainGroup = new MainGroup();
        mainGroup.setId("admin");
        mainGroup.setType("admin");
        mainGroup.setName("管理员");
        return mainGroup;
    }
}
