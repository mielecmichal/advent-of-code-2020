package pl.mielecmichal.utilities

class Resource(private val path : String) {
    fun readLines(): List<String> {
        val resource = this::class.java.classLoader.getResource(path) ?: return listOf()
        return resource.readText().lines().filter { it.isNotEmpty() }
    }
}