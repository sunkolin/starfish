package com.starfish.experiment.lock;

/**
 * Lock
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2024-09-04
 */
public interface Lock {

    boolean lock();

    boolean unlock();

}
