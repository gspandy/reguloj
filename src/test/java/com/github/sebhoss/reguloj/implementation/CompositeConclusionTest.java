/* This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. See
 * http://sam.zoy.org/wtfpl/COPYING for more details.
 */
package com.github.sebhoss.reguloj.implementation;

import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import com.github.sebhoss.common.annotation.CompilerWarnings;
import com.github.sebhoss.reguloj.Conclusion;
import com.google.common.collect.Lists;

/**
 * Test cases for {@link CompositeConclusion}.
 */
@SuppressWarnings({ CompilerWarnings.NULL, CompilerWarnings.STATIC_METHOD })
public class CompositeConclusionTest {

    /** Checks expected exception inside single test cases. */
    @org.junit.Rule
    public ExpectedException thrown = ExpectedException.none();

    /**
     * Ensures that an empty list of conclusions won't be accepted by a CompositeConclusion.
     */
    @SuppressWarnings(CompilerWarnings.UNUSED)
    @Test
    public void shouldNotAllowEmptyCollection() {
        final Collection<Conclusion<Object>> conclusions = Lists.newArrayList();

        thrown.expect(IllegalArgumentException.class);

        new CompositeConclusion<Object>(conclusions);
    }

    /**
     * Ensures that all given conclusions are called.
     */
    @SuppressWarnings(CompilerWarnings.BOXING)
    @Test
    public void shouldCallAllGivenConclusions() {
        final Object target = new Object();
        final Collection<Conclusion<Object>> conclusions = Lists.newArrayList();
        final Conclusion<Object> conclusion1 = Mockito.mock(Conclusion.class);
        BDDMockito.given(conclusion1.apply(target)).willReturn(true);
        final Conclusion<Object> conclusion2 = Mockito.mock(Conclusion.class);
        BDDMockito.given(conclusion2.apply(target)).willReturn(false);
        conclusions.add(conclusion1);
        conclusions.add(conclusion2);

        final Conclusion<Object> composite = new CompositeConclusion<>(conclusions);

        Assert.assertTrue(composite.apply(target));
    }

}