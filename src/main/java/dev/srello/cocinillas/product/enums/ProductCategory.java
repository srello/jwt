package dev.srello.cocinillas.product.enums;

import dev.srello.cocinillas.shared.enums.EnumMethods;

import java.util.Arrays;
import java.util.List;

import static dev.srello.cocinillas.product.enums.ProductCategory.Group.*;

public enum ProductCategory implements EnumMethods {
    // --- Food ---
    MEAT_AND_POULTRY(FOOD),
    FISH_AND_SEAFOOD(FOOD),
    FRUITS(FOOD),
    VEGETABLES(FOOD),
    LEGUMES(FOOD),
    CEREALS_AND_PASTA(FOOD),
    RICE_AND_GRAINS(FOOD),
    BAKERY_AND_PASTRY(FOOD),
    EGGS(FOOD),
    DAIRY(FOOD),
    PLANT_BASED_DRINKS(FOOD),
    OILS_AND_FATS(FOOD),
    SUGARS_AND_SWEETENERS(FOOD),
    SAUCES_AND_CONDIMENTS(FOOD),
    SNACKS(FOOD),
    CANNED_FOOD(FOOD),
    FROZEN_FOOD(FOOD),
    READY_MEALS(FOOD),
    DRINKS(FOOD),
    ALCOHOLIC_DRINKS(FOOD),

    // --- Baby ---
    BABY_FOOD(BABY),
    DIAPERS_AND_WIPES(BABY),
    BABY_HYGIENE(BABY),
    BABY_ACCESSORIES(BABY),

    // --- Personal care ---
    SHOWER_AND_SHAMPOO(PERSONAL_CARE),
    DEODORANTS(PERSONAL_CARE),
    ORAL_CARE(PERSONAL_CARE),
    SHAVING_AND_HAIR_REMOVAL(PERSONAL_CARE),
    HAIR_CARE(PERSONAL_CARE),
    FEMININE_CARE(PERSONAL_CARE),
    PERFUMERY_AND_COSMETICS(PERSONAL_CARE),

    // --- Home cleaning ---
    GENERAL_CLEANING(CLEANING),
    KITCHEN_CLEANING(CLEANING),
    BATHROOM_CLEANING(CLEANING),
    DETERGENTS_AND_SOFTENERS(CLEANING),
    AIR_FRESHENERS(CLEANING),
    CLEANING_ACCESSORIES(CLEANING),

    // --- Pets ---
    DOG_FOOD(PETS),
    CAT_FOOD(PETS),
    OTHER_PETS_FOOD(PETS),
    PET_ACCESSORIES(PETS),

    // --- Home and others ---
    TOILET_PAPER_AND_NAPKINS(HOME),
    BATTERIES_AND_LIGHTBULBS(HOME),
    DISPOSABLE_TABLEWARE(HOME),
    CANDLES_AND_CHARCOAL(HOME);

    private final Group group;

    ProductCategory(Group group) {
        this.group = group;
    }

    public static List<ProductCategory> getByGroup(Group group) {
        return Arrays.stream(values())
                .filter(c -> c.getGroup() == group)
                .toList();
    }

    public Group getGroup() {
        return group;
    }

    public enum Group {
        FOOD,
        BABY,
        PERSONAL_CARE,
        CLEANING,
        PETS,
        HOME
    }
}

