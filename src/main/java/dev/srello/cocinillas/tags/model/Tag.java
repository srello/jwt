package dev.srello.cocinillas.tags.model;

import dev.srello.cocinillas.tags.enums.TagType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "tags")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tag {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ElementCollection(targetClass = TagType.class, fetch = EAGER)
    @CollectionTable(name = "tag_tag_types", joinColumns = @JoinColumn(name = "tag_id"))
    @Column(name = "type")
    @Enumerated()
    private List<TagType> types;
}
