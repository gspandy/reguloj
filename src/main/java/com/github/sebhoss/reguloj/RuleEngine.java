/*
 * Copyright © 2010 Sebastian Hoß <mail@shoss.de>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See http://www.wtfpl.net/ for more details.
 */
package com.github.sebhoss.reguloj;

import java.util.Collection;

/**
 * <p>
 * The {@link RuleEngine} is responsible for logical reasoning. For that it analyzes a given {@link Context context}
 * based upon a set of {@link Rule rules}.
 * </p>
 * <p>
 * Both the context and the set of rules have to be supplied with each invocation of any of the available methods. This
 * means that users of this interface will have to hold on to their set of rules somewhere else. You can optimize this
 * set of rules by removing unnecessary elements or combining elements that are known to always fire together.
 * </p>
 * <h1>Caveats</h1>
 * <ul>
 * <li>The current API design forces every user to call an instance of this engine explicitly and supply both the
 * context and a set of rules. This may be inefficient for large sets of rules or overly complex context types.</li>
 * </ul>
 * <h1>Examples</h1>
 * <ol>
 * <li>
 * <p>
 * Test whether any rule would fire for a given context:
 * </p>
 *
 * <pre>
 * RuleEngine&lt;Context&lt;X&gt;&gt; engine = ...;
 * Collection&lt;Rule&lt;Context&lt;X&gt;&gt;&gt; rules = ...;
 * Context&lt;X&gt; context = ...;
 *
 * boolean fired = engine.analyze(rules, context);
 * </pre>
 *
 * </li>
 * <li>
 * <p>
 * Launch the engine and fire all valid rules:
 * </p>
 *
 * <pre>
 * RuleEngine&lt;Context&lt;X&gt;&gt; engine = ...;
 * Collection&lt;Rule&lt;Context&lt;X&gt;&gt;&gt; rules = ...;
 * Context&lt;X&gt; context = ...;
 *
 * engine.infer(rules, context);
 * </pre>
 *
 * </li>
 * </ol>
 * <h1>How to help</h1>
 * <ul>
 * <li>Test the interface and write back about errors, bugs and wishes.</li>
 * <li>Evaluate whether something like RETE can be applied to this interface and how it can be done.</li>
 * </ul>
 *
 * @param <CONTEXT>
 *            The context type.
 */
public interface RuleEngine<CONTEXT extends Context<?>> {

    /**
     * Performs a dry-run with this engine by analyzing a given context based upon a set of rules. It will only check
     * whether any rule would fires inside the given context but does not apply any conclusions. For that call the
     * {@link RuleEngine#infer(Collection, Context) infer}-method.
     *
     * @param rules
     *            The rules to check.
     * @param context
     *            The context to use.
     * @return <code>true</code> if any rule would fire, <code>false</code> otherwise.
     */
    boolean analyze(Collection<Rule<CONTEXT>> rules, CONTEXT context);

    /**
     * Launches this engine and lets it analyze and execute a set of rules on a given context.
     *
     * @param rules
     *            The rules to run.
     * @param context
     *            The context to use.
     */
    void infer(Collection<Rule<CONTEXT>> rules, CONTEXT context);

}
