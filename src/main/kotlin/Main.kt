import java.awt.Image
import java.awt.Rectangle
import java.awt.image.BufferedImage
import java.io.File
import java.io.FileInputStream
import java.nio.file.Files
import java.nio.file.Paths
import javax.imageio.ImageIO
import javax.imageio.ImageReadParam
import javax.imageio.ImageReader
import javax.imageio.ImageWriter

val MAX_CONVERTED_FILE_SIZE = 1024 * 1024 * 5

val METADATA_RESERVED_SIZE = 1024 * 10

fun main(args: Array<String>) {

    val sliceWidth = 1024
    val sliceHeight = 1024
    val targetImageFileSize = 1024 * 1024 * 5


    val sourceImagePath = "C:\\tmp\\BlackMarble_2016_928m_india.tif"

    println("Max Heap Size: " + Runtime.getRuntime().maxMemory() / 1024 / 1024 + "MB")

    val tifReader = getImageReader("tif")

    ImageIO.createImageInputStream(FileInputStream(File(sourceImagePath))).use { imgIn ->
        tifReader.input = imgIn
        val sourceImageWidth = tifReader.getWidth(0)
        val sourceImageHeight = tifReader.getHeight(0)
        val scaleRatio = calculateScaleRatio(Files.size(Paths.get(sourceImagePath)).toInt(), targetImageFileSize)
        val targetImageWidth = (sourceImageWidth * scaleRatio).toInt()
        val targetImageHeight = (sourceImageHeight * scaleRatio).toInt()

        println("Source Image Width: $sourceImageWidth, Height: $sourceImageHeight")
        println("Scale Ratio: $scaleRatio, Target Image Width: $targetImageWidth, Height: $targetImageHeight")

        val targetImage = BufferedImage(targetImageWidth, targetImageHeight, BufferedImage.TYPE_INT_ARGB)
        val graphics = targetImage.createGraphics()

        var sourceX = 0
        var sourceY = 0
        var targetX = 0
        var targetY = 0

        while (sourceY < sourceImageHeight) {

            var drawnHeight : Int = -1

            while (sourceX < sourceImageWidth) {
                val readParam = ImageReadParam().apply {
                    sourceRegion = Rectangle(sourceX, sourceY, sliceWidth, sliceHeight)
                }
                val slice = tifReader.read(0, readParam)
                val scaledSlice = scaleSlice(slice, scaleRatio)
                println("Scaling x: $sourceX, y: $sourceY, width: ${slice.width}, height: ${slice.height}")
                println("Target x: $targetX, y: $targetY, width: ${scaledSlice.getWidth(null)}, height: ${scaledSlice.getHeight(null)}")
                graphics.drawImage(scaledSlice, targetX, targetY, null)
                sourceX += sliceWidth
                targetX += scaledSlice.getWidth(null)
                drawnHeight = scaledSlice.getHeight(null)
            }

            sourceX = 0
            targetX = 0
            sourceY += sliceHeight
            assert(drawnHeight != -1)
            targetY += drawnHeight
        }

        ImageIO.write(targetImage, "tif", File("target.tif"))
    }


}

fun getImageReader(suffix: String): ImageReader {
    val imageReaders = ImageIO.getImageReadersBySuffix(suffix)
    if (!imageReaders.hasNext()) {
        throw Exception("No $suffix readers found")
    }
    return imageReaders.next()
}

fun getImageWriter(suffix: String): ImageWriter {
    val imageWriters = ImageIO.getImageWritersBySuffix(suffix)
    if (!imageWriters.hasNext()) {
        throw Exception("No $suffix writers found")
    }
    return imageWriters.next()
}

fun calculateScaleRatio(originalImageSize: Int, targetImageSize: Int): Double {
    return targetImageSize.toDouble() / originalImageSize.toDouble()
}


fun scaleSlice(slice: BufferedImage, scaleRatio: Double): Image {
    val targetImageWidth = (slice.width * scaleRatio).toInt()
    val targetImageHeight = (slice.height * scaleRatio).toInt()
    return slice.getScaledInstance(targetImageWidth, targetImageHeight, BufferedImage.SCALE_DEFAULT)
}