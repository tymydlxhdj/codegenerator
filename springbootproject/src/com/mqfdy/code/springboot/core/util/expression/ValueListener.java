package com.mqfdy.code.springboot.core.util.expression;

/**
 * The interface that all those interested in receiving updates of the value of a Live expression 
 * should implement.
 * 
 * @author zjing
 */
public interface ValueListener<T> {
	void gotValue(LiveExpression<T> exp, T value);
}
