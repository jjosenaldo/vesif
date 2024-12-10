package ui.model

enum class UiZoomLevel(val factor: Float) {
    ZOOM_025(0.25f),
    ZOOM_050(0.50f),
    ZOOM_075(0.75f),
    ZOOM_100(1f),
    ZOOM_125(1.25f),
    ZOOM_150(1.5f),
    ZOOM_200(2f),
    ZOOM_300(3f),
    ZOOM_400(4f);

    fun zoomIn(): UiZoomLevel {
        return entries[(this.ordinal + 1).coerceAtMost(entries.size - 1)]
    }

    fun zoomOut(): UiZoomLevel {
        return entries[(this.ordinal - 1).coerceAtLeast(0)]
    }

}