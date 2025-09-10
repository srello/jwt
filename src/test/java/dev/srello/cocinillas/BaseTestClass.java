package dev.srello.cocinillas;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith({MockitoExtension.class})
public abstract class BaseTestClass {
    PodamFactory factory = new PodamFactoryImpl();

    protected <T> T generateData(Class<T> clazz) {
        return factory.manufacturePojo(clazz);
    }
}
