package dev.srello.cocinillas.meal.repository;

import dev.srello.cocinillas.meal.model.Meal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MealRepository extends JpaRepository<Meal, Long> {
    List<Meal> findByUserIdAndDateTimeBetweenOrderByDateTime(Long userId, LocalDateTime start, LocalDateTime end);

    Optional<Meal> findByIdAndUserId(Long id, Long userId);

}
