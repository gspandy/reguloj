/*
 * Copyright © 2010 Sebastian Hoß <mail@shoss.de>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See http://www.wtfpl.net/ for more details.
 */
package com.github.sebhoss.reguloj;

import java.lang.reflect.Constructor;
import java.util.function.Consumer;
import java.util.function.Predicate;

import com.github.sebhoss.warnings.CompilerWarnings;
import com.google.common.truth.Truth;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Test cases for the {@link Rules} utility class.
 *
 * @see Rules
 */
@SuppressWarnings({ CompilerWarnings.STATIC_METHOD })
public final class RulesTest {

    /** Constant name for all rules inside this test. */
    private static final String NAME = "test rule"; //$NON-NLS-1$

    /**
     * <p>
     * Test method for {@link Rules#rule()}
     * </p>
     * <p>
     * Ensures that the returned object is not <code>null</code>.
     * </p>
     */
    @Test
    public void shouldCreateBuilder() {
        RuleBuilder<Context<Object>> builder;

        builder = Rules.<Context<Object>> rule();

        Truth.assertThat(builder).isNotNull();
    }

    /**
     * <p>
     * Test method for {@link Rules#rule()}
     * </p>
     * <p>
     * Ensures that the returned object is not <code>null</code>.
     * </p>
     */
    @Test
    @SuppressWarnings("unchecked")
    public void shouldCreateRule() {
        final RuleBuilder<Context<Object>> builder = Rules.<Context<Object>> rule();
        builder.called(RulesTest.NAME).when(Mockito.mock(Predicate.class));

        final Rule<Context<Object>> rule = builder.then(Mockito.mock(Consumer.class));

        Truth.assertThat(rule).isNotNull();
    }

    /**
     * <p>
     * Test method for {@link Rules Rules()}
     * </p>
     * <p>
     * Ensures that the constructor is not accessible from the outside.
     * </p>
     */
    @Test
    @SuppressWarnings("boxing")
    public void shouldNotBeInvocable() {
        final Class<?> clazz = Rules.class;

        final Constructor<?>[] constructors = clazz.getDeclaredConstructors();

        for (final Constructor<?> constructor : constructors) {
            Truth.assertThat(constructor.isAccessible()).isFalse();
        }
    }

    /**
     * <p>
     * Test method for {@link Rules Rules()}
     * </p>
     * <p>
     * Ensures that the constructor is accessible via reflection.
     * </p>
     *
     * @throws Exception
     *             When no new instance can be created.
     */
    @Test
    public void shouldBeInvocableViaReflection() throws Exception {
        final Class<?> clazz = Rules.class;
        final Constructor<?> constructor = clazz.getDeclaredConstructors()[0];

        constructor.setAccessible(true);
        final Object instance = constructor.newInstance((Object[]) null);

        Assert.assertNotNull(instance);
    }

}
