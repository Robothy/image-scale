interface ImageCompressor {

    companion object {

        fun compress() {
            println("Compressing image...")
        }

        fun asyncCompress() {
            println("Compressing image asynchronously...")
        }

    }

    fun compress()

    fun asyncCompress()

}