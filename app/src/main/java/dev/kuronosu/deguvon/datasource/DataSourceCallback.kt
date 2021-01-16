package dev.kuronosu.deguvon.datasource

interface DataSourceCallback<T> {
    fun onError(error: String)
    fun onSuccess(data: T, sourceType: DataSourceType)
}