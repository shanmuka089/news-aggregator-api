package com.example.new_aggregator.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "sources")
public class SourceEntity
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sourceId;
    private String name;
    private String description;
    private boolean enabled = true;
    
    @ManyToMany(mappedBy = "sources")
    @JsonIgnore
    private List<PreferenceEntity> preferences;
    
    @CreationTimestamp
    private Date createdAt;
    
    @UpdateTimestamp
    private Date updatedAt;
}
