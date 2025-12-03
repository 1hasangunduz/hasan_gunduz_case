package com.insider.utilities;

public final class StepInit {

    private StepInit() {
        throw new IllegalStateException("Utility class – cannot be instantiated.");
    }

    @SuppressWarnings("unchecked")
    public static <T> T get(Class<T> pageClass) {
        try {
            return pageClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Cannot create page: " + pageClass.getName(), e);
        }
    }
}
