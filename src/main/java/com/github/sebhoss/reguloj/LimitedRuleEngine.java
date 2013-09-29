/*
 * Copyright © 2010 Sebastian Hoß <mail@shoss.de>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See http://www.wtfpl.net/ for more details.
 */
package com.github.sebhoss.reguloj;

import java.util.Set;

final class LimitedRuleEngine<CONTEXT extends Context<?>> extends AbstractRuleEngine<CONTEXT> {

    private final int maximumNumberOfRuns;

    LimitedRuleEngine(final int maximumNumberOfRuns) {
        this.maximumNumberOfRuns = maximumNumberOfRuns;
    }

    @Override
    public void infer(final CONTEXT context, final Set<Rule<CONTEXT>> rules) {
        int currentRuns = 0;
        boolean ruleFired = false;

        do {
            currentRuns++;
            for (final Rule<CONTEXT> rule : rules) {
                ruleFired |= rule.run(context);
            }
        } while (ruleFired && currentRuns <= maximumNumberOfRuns);
    }

}