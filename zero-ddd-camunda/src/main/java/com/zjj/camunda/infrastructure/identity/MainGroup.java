package com.zjj.camunda.infrastructure.identity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.camunda.bpm.engine.identity.Group;

@Data
@Entity
@Table(name = "main_role")
public class MainGroup implements Group {

    @Id
    @Column(name = "id")
    private Long groupId;

    @Column(name = "code")
    private String id;
    
    @Column(name = "name")
    private String name;

    @Override
    public String getType() {
        return null;
    }

    @Override
    public void setType(String string) {

    }
}