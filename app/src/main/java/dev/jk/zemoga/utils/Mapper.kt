package dev.jk.zemoga.utils

abstract class Mapper<I, O> {
    abstract fun map(entry: I): O
}