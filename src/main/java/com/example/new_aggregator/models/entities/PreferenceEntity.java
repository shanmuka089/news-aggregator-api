package com.example.new_aggregator.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "preferences")
public class PreferenceEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long preferenceId;
    
    @Column(nullable = false, unique = true)
    private Long userId;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, mappedBy = "preferences")
    private List<CategoryEntity> categories = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinTable(name = "source_preferences",
            joinColumns = @JoinColumn(name = "source_id"),
            inverseJoinColumns = @JoinColumn(name = "preference_id"))
    private List<SourceEntity> sources = new ArrayList<>();

    @Column(length = 10)
    private String language;

    @Column(length = 100)
    private String region;

    private boolean enabled = true;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

}
