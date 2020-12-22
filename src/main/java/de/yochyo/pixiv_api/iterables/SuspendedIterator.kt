package de.yochyo.pixiv_api.iterables

interface SuspendedIterator<E> {
    suspend fun hasNext(): Boolean
    suspend fun next(): E
}