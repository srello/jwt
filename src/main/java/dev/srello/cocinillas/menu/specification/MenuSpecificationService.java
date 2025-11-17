package dev.srello.cocinillas.menu.specification;

import dev.srello.cocinillas.menu.dto.GetMenusIDTO;
import dev.srello.cocinillas.menu.model.Menu;
import org.springframework.data.jpa.domain.Specification;

public interface MenuSpecificationService {
    Specification<Menu> buildMenusPaginatedSpecification(GetMenusIDTO getMenusIDTO);

    Specification<Menu> buildUserMenusPaginatedSpecification(GetMenusIDTO getMenusIDTO);
}
