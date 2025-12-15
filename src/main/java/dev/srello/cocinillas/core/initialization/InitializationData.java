package dev.srello.cocinillas.core.initialization;


import com.github.javafaker.Faker;
import dev.srello.cocinillas.menu.model.Menu;
import dev.srello.cocinillas.menu.model.MenuMeal;
import dev.srello.cocinillas.menu.repository.MenuRepository;
import dev.srello.cocinillas.product.enums.ProductCategory;
import dev.srello.cocinillas.product.model.Product;
import dev.srello.cocinillas.product.reposiroty.ProductRepository;
import dev.srello.cocinillas.recipe.model.Ingredient;
import dev.srello.cocinillas.recipe.model.Instruction;
import dev.srello.cocinillas.recipe.model.Recipe;
import dev.srello.cocinillas.recipe.repository.RecipeRepository;
import dev.srello.cocinillas.settings.model.Settings;
import dev.srello.cocinillas.shared.enums.Visibility;
import dev.srello.cocinillas.shoppinglist.model.ShoppingList;
import dev.srello.cocinillas.shoppinglist.model.ShoppingListItem;
import dev.srello.cocinillas.shoppinglist.repository.ShoppingListRepository;
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
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static dev.srello.cocinillas.shared.enums.Visibility.OFFICIAL;
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
    private final MenuRepository menuRepository;
    private final ShoppingListRepository shoppingListRepository;
    private final PasswordEncoder passwordEncoder;
    private final String[] devImageKeys = {"carbonara.png", "ensalada cesar.png", "paella.png", "reina pepiada.png", "verduras thai.jpg"};
    private final Map<String, LocalTime> mealNamesHours = Map.of(
            "Desayuno", LocalTime.of(9, 0),
            "Almuerzo", LocalTime.of(11, 0),
            "Comida", LocalTime.of(14, 0),
            "Merienda", LocalTime.of(17, 0),
            "Cena", LocalTime.of(21, 0)
    );
    private final Faker faker = new Faker();
    @Value("${LOAD_INITIAL_DATA:false}")
    private boolean loadInitialData;

    @Bean
    @Profile("DEV")
    public InitializingBean initialize() {
        return this::createDbData;
    }

    private void createDbData() {
        if (!loadInitialData) return;

        List<User> users = userRepository.saveAllAndFlush(generateUsers());

        tagRepository.saveAllAndFlush(generateRandomTags());

        List<Product> productsWithoutConversions = productRepository.saveAllAndFlush(generateRandomProducts(users));

        List<Product> products = productRepository.saveAllAndFlush(generateUnitConversions(productsWithoutConversions));

        List<Recipe> recipes = generateRandomRecipes(products, users);
        recipeRepository.saveAllAndFlush(recipes);

        menuRepository.saveAllAndFlush(generateMenus(users, recipes));

        shoppingListRepository.saveAllAndFlush(generateShoppingLists(products, users));
    }

    private Iterable<ShoppingList> generateShoppingLists(List<Product> products, List<User> users) {
        List<ShoppingList> shoppingLists = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            LocalDateTime now = now();
            LocalDateTime startDate = now.plusDays(7L * (i - 4));
            User author = users.get(current().nextInt(0, users.size()));
            shuffle(products);
            List<ShoppingListItem> shoppingListItems = products.subList(0, current().nextInt(20, 40))
                    .stream()
                    .map(product -> ShoppingListItem.builder()
                            .product(product)
                            .checked(false)
                            .addedManually(current().nextBoolean())
                            .quantity((double) current().nextInt(1, 5))
                            .build())
                    .toList();
            ShoppingList shoppingList = ShoppingList.builder()
                    .items(shoppingListItems)
                    .startDate(startDate)
                    .endDate(startDate.plusDays(7))
                    .completed(false)
                    .author(author)
                    .build();
            shoppingLists.add(shoppingList);
        }
        return shoppingLists;
    }

    private Iterable<Menu> generateMenus(List<User> users, List<Recipe> recipes) {
        List<Menu> menus = new ArrayList<>();
        for (int i = 0; i < 100; i++) {

            User author = users.get(current().nextInt(users.size()));
            Visibility[] visibilities = Visibility.values();
            Menu menu = Menu.builder()
                    .author(author)
                    .menuMeals(generateRandomMeals(recipes))
                    .visibility(visibilities[current().nextInt(visibilities.length)])
                    .likes(0L)
                    .name(faker.funnyName().name())
                    .description(faker.howIMetYourMother().quote())
                    .build();
            menus.add(menu);
        }
        return menus;
    }

    private List<MenuMeal> generateRandomMeals(List<Recipe> recipes) {
        List<MenuMeal> menuMeals = new ArrayList<>();
        for (int i = 0; i < current().nextInt(7, 15); i++) {
            String name = mealNamesHours.keySet().stream().toList().get(current().nextInt(0, mealNamesHours.size()));
            MenuMeal menuMeal = MenuMeal.builder()
                    .name(name)
                    .recipeIds(of(recipes.get(current().nextInt(0, recipes.size())).getId()))
                    .hour(mealNamesHours.get(name))
                    .dayIndex(current().nextInt(1, 7))
                    .build();
            menuMeals.add(menuMeal);
        }
        return menuMeals;
    }

    private List<User> generateUsers() {
        User admin = User.builder()
                .password(passwordEncoder.encode("1234Abc$"))
                .role(ADMIN)
                .email("admin@admin.com")
                .username("admin")
                .name("Aname")
                .surname("Asurname")
                .settings(Settings.builder().defaultDiners(2).build())
                .build();
        User user = User.builder()
                .password(passwordEncoder.encode("1234Abc$"))
                .role(USER)
                .email("user@user.com")
                .username("user")
                .name("Uname")
                .surname("Usurname")
                .settings(Settings.builder().defaultDiners(4).build())
                .build();

        return of(admin, user);
    }

    private List<Tag> generateRandomTags() {
        List<Tag> tags = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            tags.add(Tag.builder()
                    .name(faker.funnyName().name())
                    .types(of(values()[current().nextInt(values().length)]))
                    .build());
        }
        return tags;
    }

    private List<Product> generateUnitConversions(List<Product> products) {
        Unit[] units = Unit.values();
        List<Product> list = new ArrayList<>();
        for (Product product : products) {
            UnitConversion unitConversion = UnitConversion.builder()
                    .conversionFactor(current().nextDouble(1, 50))
                    .targetUnit(units[current().nextInt(units.length)])
                    .product(product)
                    .build();
            product.setUnitConversions(of(unitConversion));
            list.add(product);
        }
        return list;
    }

    private List<Product> generateRandomProducts(List<User> users) {
        List<Product> products = new ArrayList<>();
        ProductCategory[] productCategories = ProductCategory.values();
        Unit[] units = Unit.values();
        for (int i = 0; i < 100; i++) {

            products.add(Product.builder()
                    .name(faker.food().ingredient())
                    .productCategory(productCategories[current().nextInt(productCategories.length)])
                    .baseUnit(units[current().nextInt(units.length)])
                    .fat(current().nextDouble(1, 100))
                    .calories(current().nextDouble(1, 150))
                    .carbohydrates(current().nextDouble(1, 100))
                    .protein(current().nextDouble(1, 100))
                    .visibility(OFFICIAL)
                    .author(users.get(current().nextInt(users.size())))
                    .build());
        }
        return products;
    }

    private List<Recipe> generateRandomRecipes(List<Product> products, List<User> users) {
        List<Recipe> recipes = new ArrayList<>();
        Visibility[] visibilities = Visibility.values();
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
                    .visibility(visibilities[current().nextInt(visibilities.length)])
                    .ingredients(ingredients)
                    .instructions(generateRandomInstructions(faker))
                    .imageKeys(of(devImageKeys[current().nextInt(devImageKeys.length)]))
                    .totalDuration(current().nextLong(5, 240))
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
