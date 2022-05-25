package org.semanticweb.owlapi.impltest.concurrent;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.impl.concurrent.NoOpReadWriteLock;

/**
 * Matthew Horridge Stanford Center for Biomedical Informatics Research 13/04/15
 */
public class NoOpReadWriteLock_TestCase {

    private NoOpReadWriteLock lock;

    @Before
    public void setUp() {
        lock = new NoOpReadWriteLock();
    }

    @Test
    public void shouldNotReturnNullForReadLock() {
        assertThat(lock.readLock(), is(not(nullValue())));
    }

    @Test
    public void shouldNotReturnNullForWriteLock() {
        assertThat(lock.writeLock(), is(not(nullValue())));
    }
}
