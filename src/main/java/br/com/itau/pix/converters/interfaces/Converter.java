package br.com.itau.pix.converters.interfaces;

import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

public interface Converter<T,R> extends Function<T,R> {

    static <T,R> R to(T object, Converter<T,R> converter) {
        return Objects.nonNull(object) ? converter.apply(object) : null;
    }

    static <T,R> List<R> toList(Collection<T> objects, Converter<T,R> converter) {
        return CollectionUtils.isEmpty(objects) ? List.of() :
                objects.stream().map(converter).toList();
    }

    default R apply(T target) {
        return Optional.ofNullable(target).map(this::convert).orElse(null);
    }

    R convert(T target);

}
