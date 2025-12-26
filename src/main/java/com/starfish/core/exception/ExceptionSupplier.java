package com.starfish.core.exception;

/**
 * ExceptionSupplier
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2025-09-30
 */
public interface ExceptionSupplier {

    Integer getCode();

    String getMessage();

    String getDescription();

}
