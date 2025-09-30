package dev.srello.cocinillas.core.initialization;


import com.github.javafaker.Faker;
import dev.srello.cocinillas.product.enums.ProductCategory;
import dev.srello.cocinillas.product.model.Product;
import dev.srello.cocinillas.product.reposiroty.ProductRepository;
import dev.srello.cocinillas.recipe.enums.RecipeVisibility;
import dev.srello.cocinillas.recipe.model.Recipe;
import dev.srello.cocinillas.recipe.repository.RecipeRepository;
import dev.srello.cocinillas.tags.model.Tag;
import dev.srello.cocinillas.tags.model.TagType;
import dev.srello.cocinillas.tags.repository.TagRepository;
import dev.srello.cocinillas.tags.repository.TagTypeRepository;
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

import java.util.ArrayList;
import java.util.List;

import static dev.srello.cocinillas.user.enums.Role.ADMIN;
import static dev.srello.cocinillas.user.enums.Role.USER;
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
    private final TagTypeRepository tagTypeRepository;
    private final PasswordEncoder passwordEncoder;
    @Value("${LOAD_INITIAL_DATA:false}")
    private boolean loadInitialData;

    @Bean
    @Profile("DEV")
    public InitializingBean intialize() {
        return this::createDbData;
    }

    private void createDbData() {
        if (!loadInitialData) return;

        Faker faker = new Faker();

        userRepository.saveAllAndFlush(generateUsers());

        List<TagType> tagTypes = tagTypeRepository.saveAllAndFlush(generateTagTypes());

        tagRepository.saveAllAndFlush(generateRandomTags(faker, tagTypes));

        List<Product> ingredients = productRepository.saveAllAndFlush(generateRandomIngredients(faker));

        recipeRepository.saveAllAndFlush(generateRandomRecipes(faker, ingredients));


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

    private List<TagType> generateTagTypes() {
        TagType recipeTagType = TagType.builder()
                .name("recipe")
                .build();
        TagType ingredientTagType = TagType.builder()
                .name("ingredient")
                .build();
        TagType menuTagType = TagType.builder()
                .name("menu")
                .build();
        return of(recipeTagType, ingredientTagType, menuTagType);
    }

    private List<Tag> generateRandomTags(Faker faker, List<TagType> tagTypes) {
        List<Tag> tags = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            shuffle(tagTypes);
            tags.add(Tag.builder()
                    .name(faker.funnyName().name())
                    .tagTypes(of(tagTypes.getFirst()))
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
                    .fat(current().nextDouble(1, 500))
                    .calories(current().nextDouble(1, 500))
                    .carbohydrates(current().nextDouble(1, 500))
                    .protein(current().nextDouble(1, 500))
                    .unitConversions(of(unitConversion))
                    .build());
        }
        return ingredients;
    }

    private List<Recipe> generateRandomRecipes(Faker faker, List<Product> ingredients) {
        List<Recipe> recipes = new ArrayList<>();
        RecipeVisibility[] recipeVisibilities = RecipeVisibility.values();
        for (int i = 0; i < 50; i++) {
            shuffle(ingredients);
            String name = faker.food().dish();
            recipes.add(Recipe.builder()
                    .name(name)
                    .visibility(recipeVisibilities[current().nextInt(recipeVisibilities.length)])
                    .ingredients(ingredients.subList(0, current().nextInt(5, 10)))
                    .imageUrls(of("recipes/" + name))
                    .build());
        }
        return recipes;
    }
}
