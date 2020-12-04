package pl.mielecmichal.utilities

class Resource(private val path : String) {

    fun readText() : String{
        val resource = this::class.java.classLoader.getResource(path) ?: return ""
        return resource.readText()
    }

    fun readLines(): List<String> {
        return readText().lines().filter { it.isNotEmpty() }
    }
}