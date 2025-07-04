package org.arexdev.rickmortyapp.data.paginator

class Paginator<T>(
    private val initialPage: Int = 1,
    private val onRequest: suspend (page: Int) -> List<T>,
    private val onError: (Throwable) -> Unit = {},
    private val onSuccess: (List<T>, Boolean) -> Unit
) {
    private var currentPage = initialPage
    private var isRequestInProgress = false
    private var endReached = false

    suspend fun loadNext() {
        if (isRequestInProgress || endReached) return
        isRequestInProgress = true
        try {
            val items = onRequest(currentPage)
            endReached = items.isEmpty()
            onSuccess(items, endReached)
            currentPage++
        } catch (t: Throwable) {
            onError(t)
        } finally {
            isRequestInProgress = false
        }
    }

    fun reset() {
        currentPage = initialPage
        endReached = false
    }
}
