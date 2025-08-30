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
    private String id;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "code")
    private String type;
}