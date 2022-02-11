package com.bready.xml2java;

import com.bready.xml2java.annotation.Match;
import com.bready.xml2java.annotation.Multiple;
import com.bready.xml2java.annotation.Single;
import com.bready.xml2java.annotation.Tag;
import com.bready.xml2java.matcher.TypeMatcher;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class AnnotationsHandler {

    private static final Logger LOG = LoggerFactory.getLogger(AnnotationsHandler.class);

    private static AnnotationsHandler instance;

    private final Map<String, Class<?>> types;
    private final Map<Class<?>, Container> containers;
    private final Map<Class<?>, TypeMatcher<?>> matchers;

    private AnnotationsHandler() {
        this.types = new HashMap<>();
        this.containers = new HashMap<>();
        this.matchers = new HashMap<>();
    }

    public Class<?> getType(String name) {
        return types.get(name.toLowerCase());
    }

    public Collection<Field> getSingleObjectFields(Class<?> type) {
        Container container = containers.get(type);

        if (container == null) {
            return Collections.emptySet();
        }

        return container.singleObjectFields;
    }

    public Collection<Field> getSingleReferenceFields(Class<?> type) {
        Container container = containers.get(type);

        if (container == null) {
            return Collections.emptySet();
        }

        return container.singleReferenceFields;
    }

    public Collection<Field> getMultipleFields(Class<?> type) {
        Container container = containers.get(type);

        if (container == null) {
            return Collections.emptySet();
        }

        return container.multipleFields;
    }

    @SuppressWarnings("java:S1452")
    public TypeMatcher<?> getMatcher(Class<?> type) {
        return matchers.get(type);
    }

    public static AnnotationsHandler getInstance() {
        if (instance == null) {
            instance = create();
        }

        return instance;
    }

    public static boolean isFieldNullable(Field field) {
        return getAnnotatedValue(field, Single::nullable, Multiple::nullable);
    }

    public static String getFieldName(Field field) {
        String value = getAnnotatedValue(field, Single::name, Multiple::name);

        if (value.isEmpty()) {
            value = field.getName();
        }

        return value;
    }

    private static AnnotationsHandler create() {
        AnnotationsHandler handler = new AnnotationsHandler();

        Collection<Reflections> c = filterPackages()
                .stream()
                .flatMap(AnnotationsHandler::findRootDirectories)
                .distinct()
                .map(Reflections::new)
                .collect(Collectors.toList());

        c.stream()
                .map(reflections -> reflections.getTypesAnnotatedWith(Tag.class))
                .flatMap(Collection::stream)
                .forEach(type -> addContainer(handler, type));

        c.stream()
                .map(reflections -> reflections.getTypesAnnotatedWith(Match.class))
                .flatMap(Collection::stream)
                .forEach(type -> addMatcher(handler, type));

        return handler;
    }

    private static void addContainer(AnnotationsHandler handler, Class<?> type) {
        Container container = new Container();

        for (Field field : type.getDeclaredFields()) {
            if (field.isAnnotationPresent(Single.class)) {
                if (field.getAnnotation(Single.class).reference()) {
                    container.singleReferenceFields.add(field);
                } else {
                    container.singleObjectFields.add(field);
                }
            } else if (field.isAnnotationPresent(Multiple.class)) {
                container.multipleFields.add(field);
            }
        }

        handler.types.put(getTypeName(type), type);
        handler.containers.put(type, container);
    }

    @SuppressWarnings("java:S3011")
    private static void addMatcher(AnnotationsHandler handler, Class<?> type) {
        try {
            Constructor<?> constructor = type.getDeclaredConstructor();

            boolean isAccessible = constructor.isAccessible();

            if (!isAccessible) {
                constructor.setAccessible(true);
            }

            handler.matchers.put(type.getAnnotation(Match.class).value(), (TypeMatcher<?>) constructor.newInstance());
            constructor.setAccessible(isAccessible);
        } catch (ReflectiveOperationException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    private static String getTypeName(Class<?> type) {
        String typeName = type.getAnnotation(Tag.class).value();

        if (typeName.isEmpty()) {
            typeName = type.getSimpleName();
        }

        return typeName.toLowerCase();
    }

    private static <T> T getAnnotatedValue(Field field, Function<Single, T> sFunction,
                                           Function<Multiple, T> mFunction) {
        if (field.isAnnotationPresent(Single.class)) {
            return sFunction.apply(field.getAnnotation(Single.class));
        } else if (field.isAnnotationPresent(Multiple.class)) {
            return mFunction.apply(field.getAnnotation(Multiple.class));
        } else {
            throw new IllegalArgumentException();
        }
    }

    private static List<File> filterPackages() {
        return Stream.of(((URLClassLoader) AnnotationsHandler.class.getClassLoader()).getURLs())
                .map(URL::getFile)
                .map(File::new)
                .filter(File::isDirectory)
                .collect(Collectors.toList());
    }

    private static Stream<String> findRootDirectories(File folder) {
        File[] files = folder.listFiles();

        if (files == null) {
            throw new IllegalArgumentException();
        }

        return Stream.of(files)
                .filter(File::isDirectory)
                .map(file -> file.getPath().replace(folder.getPath() + "\\", ""));
    }

    private static class Container {

        private final Collection<Field> singleObjectFields;
        private final Collection<Field> singleReferenceFields;
        private final Collection<Field> multipleFields;

        private Container() {
            this.singleObjectFields = new HashSet<>();
            this.singleReferenceFields = new HashSet<>();
            this.multipleFields = new HashSet<>();
        }
    }
}
