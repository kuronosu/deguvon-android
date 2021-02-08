package dev.kuronosu.deguvon.datasource

interface Mapper<T, U> {
    fun map(data: T): U
}
