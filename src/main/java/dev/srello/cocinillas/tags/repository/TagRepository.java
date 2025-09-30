package dev.srello.cocinillas.tags.repository;

import dev.srello.cocinillas.tags.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
