/*
 * Copyright © 2010 Sebastian Hoß <mail@shoss.de>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See http://www.wtfpl.net/ for more details.
 */
package com.github.sebhoss.reguloj;

import javax.annotation.Nullable;

import com.github.sebhoss.common.annotation.Nullsafe;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;

/**
 * Standard implementation of the {@link Rule} interface.
 * 
 * @param <CONTEXT>
 *            The context type.
 */
final class RuleImplementation<CONTEXT extends Context<?>> implements Rule<CONTEXT> {

    private final String              name;
    private final Predicate<CONTEXT>  predicate;
    private final Conclusion<CONTEXT> conclusion;

    /**
     * Constructor for a new {@link Rule} implementation.
     * 
     * @param name
     *            The name of the new rule.
     * @param predicate
     *            The predicate of the new rule.
     * @param conclusion
     *            The conclusion of the new rule.
     */
    RuleImplementation(final String name, final Predicate<CONTEXT> predicate, final Conclusion<CONTEXT> conclusion) {
        this.name = Nullsafe.nullsafe(Preconditions.checkNotNull(name));
        this.predicate = Nullsafe.nullsafe(Preconditions.checkNotNull(predicate));
        this.conclusion = Nullsafe.nullsafe(Preconditions.checkNotNull(conclusion));
    }

    @Override
    public boolean run(final CONTEXT context) {
        final CONTEXT nullsafeContext = Nullsafe.nullsafe(Preconditions.checkNotNull(context));
        boolean changed = false;

        if (fires(nullsafeContext)) {
            changed = conclusion.apply(nullsafeContext);
        }

        return changed;
    }

    @Override
    public boolean fires(final CONTEXT context) {
        return predicate.apply(Preconditions.checkNotNull(context));
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, predicate, conclusion);
    }

    @Override
    public boolean equals(final @Nullable Object object) {
        if (object != null && object instanceof RuleImplementation) {
            final RuleImplementation<?> that = (RuleImplementation<?>) object;

            return Objects.equal(name, that.name) && Objects.equal(predicate, that.predicate)
                    && Objects.equal(conclusion, that.conclusion);
        }

        return false;
    }

    @Override
    public int compareTo(final Rule<? extends CONTEXT> object) {
        return name.compareTo(object.getName());
    }

}