package io.pivotal.edu.gemfire.support;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The AbstractCacheableService class...
 *
 * @author John Blum
 * @since 1.0.0
 */
public class AbstractCacheableService {

	private final AtomicBoolean cacheMiss = new AtomicBoolean(false);

	/* (non-Javadoc) */
	public boolean isCacheHit() {
		return !isCacheMiss();
	}

	/* (non-Javadoc) */
	public boolean isCacheMiss() {
		return cacheMiss.compareAndSet(true, false);
	}

	/* (non-Javadoc) */
	protected boolean setCacheMiss() {
		return cacheMiss.getAndSet(true);
	}
}
