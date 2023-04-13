package com.rahul.lib.data.remote

interface Mapper<T, E> {
    fun from(e: E?): T
    fun to(t: T?): E
}