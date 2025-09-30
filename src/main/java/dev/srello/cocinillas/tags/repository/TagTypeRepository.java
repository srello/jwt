package dev.srello.cocinillas.tags.repository;

import dev.srello.cocinillas.tags.model.TagType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagTypeRepository extends JpaRepository<TagType, Long> {
}
