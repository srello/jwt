package dev.srello.cocinillas.core.initialization;


import com.github.javafaker.Faker;
import dev.srello.cocinillas.product.enums.ProductCategory;
import dev.srello.cocinillas.product.model.Product;
import dev.srello.cocinillas.product.reposiroty.ProductRepository;
import dev.srello.cocinillas.recipe.enums.RecipeVisibility;
import dev.srello.cocinillas.recipe.model.Ingredient;
import dev.srello.cocinillas.recipe.model.Instruction;
import dev.srello.cocinillas.recipe.model.Recipe;
import dev.srello.cocinillas.recipe.repository.RecipeRepository;
import dev.srello.cocinillas.tags.model.Tag;
import dev.srello.cocinillas.tags.repository.TagRepository;
import dev.srello.cocinillas.unit.enums.Unit;
import dev.srello.cocinillas.unit.model.UnitConversion;
import dev.srello.cocinillas.user.model.User;
import dev.srello.cocinillas.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static dev.srello.cocinillas.tags.enums.TagType.values;
import static dev.srello.cocinillas.user.enums.Role.ADMIN;
import static dev.srello.cocinillas.user.enums.Role.USER;
import static java.time.LocalDateTime.now;
import static java.util.Collections.shuffle;
import static java.util.List.of;
import static java.util.concurrent.ThreadLocalRandom.current;

@Component
@RequiredArgsConstructor
@Profile("!pro")
public class InitializationData {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final RecipeRepository recipeRepository;
    private final TagRepository tagRepository;
    private final PasswordEncoder passwordEncoder;
    private final String[] devImageKeys = {"carbonara.png", "ensalada cesar.png", "paella.png", "reina pepiada.png", "verduras thai.jpg"};
    @Value("${LOAD_INITIAL_DATA:false}")
    private boolean loadInitialData;

    @Bean
    @Profile("DEV")
    public InitializingBean initialize() {
        return this::createDbData;
    }

    private void createDbData() {
        if (!loadInitialData) return;

        Faker faker = new Faker();

        List<User> users = userRepository.saveAllAndFlush(generateUsers());

        tagRepository.saveAllAndFlush(generateRandomTags(faker));

        List<Product> ingredients = productRepository.saveAllAndFlush(generateRandomIngredients(faker));

        recipeRepository.saveAllAndFlush(generateRandomRecipes(faker, ingredients, users));


    }

    private List<User> generateUsers() {
        User admin = User.builder()
                .password(passwordEncoder.encode("1234Abc$"))
                .role(ADMIN)
                .email("admin@admin.com")
                .username("admin")
                .name("Aname")
                .surname("Asurname")
                .build();
        User user = User.builder()
                .password(passwordEncoder.encode("1234Abc$"))
                .role(USER)
                .email("user@user.com")
                .username("user")
                .name("Uname")
                .surname("Usurname")
                .build();

        return of(admin, user);
    }

    private List<Tag> generateRandomTags(Faker faker) {
        List<Tag> tags = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            tags.add(Tag.builder()
                    .name(faker.funnyName().name())
                    .types(of(values()[current().nextInt(values().length)]))
                    .build());
        }
        return tags;
    }

    private List<Product> generateRandomIngredients(Faker faker) {
        List<Product> ingredients = new ArrayList<>();
        ProductCategory[] productCategories = ProductCategory.values();
        Unit[] units = Unit.values();
        for (int i = 0; i < 100; i++) {
            UnitConversion unitConversion = UnitConversion.builder()
                    .conversionFactor(current().nextDouble(1, 50))
                    .targetUnit(units[current().nextInt(units.length)])
                    .build();
            ingredients.add(Product.builder()
                    .name(faker.food().ingredient())
                    .productCategory(productCategories[current().nextInt(productCategories.length)])
                    .baseUnit(units[current().nextInt(units.length)])
                    .fat(current().nextDouble(1, 100))
                    .calories(current().nextDouble(1, 150))
                    .carbohydrates(current().nextDouble(1, 100))
                    .protein(current().nextDouble(1, 100))
                    .unitConversions(of(unitConversion))
                    .build());
        }
        return ingredients;
    }

    private List<Recipe> generateRandomRecipes(Faker faker, List<Product> products, List<User> users) {
        List<Recipe> recipes = new ArrayList<>();
        RecipeVisibility[] recipeVisibilities = RecipeVisibility.values();
        for (int i = 0; i < 200; i++) {
            shuffle(products);
            String name = faker.food().dish();
            List<Ingredient> ingredients = products.subList(0, current().nextInt(5, 10))
                    .stream().map(product -> Ingredient.builder()
                            .product(product)
                            .quantity(current().nextDouble(10, 100))
                            .build())
                    .toList();
            LocalDateTime now = now();
            recipes.add(Recipe.builder()
                    .name(name)
                    .description(faker.backToTheFuture().quote())
                    .visibility(recipeVisibilities[current().nextInt(recipeVisibilities.length)])
                    .ingredients(ingredients)
                    .instructions(generateRandomInstructions(faker))
                    .imageKeys(of(devImageKeys[current().nextInt(devImageKeys.length)]))
                    .totalDuration(current().nextInt(5, 240))
                    .creationDate(now.minusDays(current().nextLong(60)))
                    .likes(current().nextLong(1000))
                    .author(users.get(current().nextInt(users.size())))
                    .build());
        }
        return recipes;
    }

    private List<Instruction> generateRandomInstructions(Faker faker) {
        List<Instruction> instructions = new ArrayList<>();
        int totalInstructions = current().nextInt(5, 10);
        for (int i = 0; i < totalInstructions; i++) {
            instructions.add(Instruction.builder()
                    .description(faker.chuckNorris().fact())
                    .timer(current().nextBoolean() ? null : current().nextDouble(2, 60))
                    .build());
        }
        return instructions;
    }
}
