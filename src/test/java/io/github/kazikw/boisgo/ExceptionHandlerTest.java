package io.github.kazikw.boisgo;

import io.github.kazikw.boisgo.exception.GlobalExceptionHandler;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExceptionHandlerTest {

    @Test
    void allExceptionsShouldHaveHandlers() {

        Reflections reflections = new Reflections("io.github.kazikw.boisgo.exception");

        // wszystkie wyjątki
        Set<Class<?>> exceptions =
                reflections.getSubTypesOf(RuntimeException.class)
                        .stream()
                        .map(c -> (Class<?>) c)
                        .collect(Collectors.toSet());

        // wszystkie klasy obsłużone przez @ExceptionHandler
        Set<Class<?>> handled = Arrays.stream(GlobalExceptionHandler.class.getDeclaredMethods())
                .filter(m -> m.isAnnotationPresent(ExceptionHandler.class))
                .flatMap(m -> Arrays.stream(m.getAnnotation(ExceptionHandler.class).value()))
                .collect(Collectors.toSet());

        // wyjątki bez handlera
        List<Class<?>> missing = exceptions.stream()
                .filter(ex -> !handled.contains(ex))
                .toList();

        assertTrue(missing.isEmpty(), "Brak handlerów dla: " + missing);
    }

}
