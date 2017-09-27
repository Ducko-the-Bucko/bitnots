package com.benjishults.bitnots.model.util

interface SkippableIterator<T> : Iterator<T> {
    /**
     * Skips zero or more elements to get to the next.
     */
    fun skipToNext(): T

    var skipMode: Boolean
    
    /**
     * If skip mode is on, behaves like skipToNext()
     */
    override fun next(): T 
    /**
     * If skip mode is on, this behaves like hasSkipToNext().
     */
    override fun hasNext(): Boolean

    /**
     * Returns true if skipToNext() would return a value.
     */
    fun hasSkipToNext(): Boolean
}