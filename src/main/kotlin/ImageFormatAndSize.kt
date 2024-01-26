import java.awt.Rectangle
import java.awt.image.BufferedImage
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import javax.imageio.ImageIO

fun main() {


    val sourceImagePaths = arrayOf(
        "C:\\tmp\\pexels-frans-van-heerden-631317.jpg",
        "C:\\tmp\\untitled.png",
        "C:\\tmp\\BlackMarble_2016_928m_india.tif",
        "C:\\tmp\\BlackMarble_2016_928m_asia_southeast_labeled.png",
    )

    val imageFormats = arrayOf(
        "jpg", "png"
    )

    val scaleAlgos = arrayOf(
        BufferedImage.SCALE_AREA_AVERAGING,
        BufferedImage.SCALE_REPLICATE,
        BufferedImage.SCALE_SMOOTH,
        BufferedImage.SCALE_FAST,
        BufferedImage.SCALE_DEFAULT
    )

    val scaleSizes = arrayOf(
        Rectangle(0, 0, 100, 100),
        Rectangle(0, 0, 200, 200),
        Rectangle(0, 0, 300, 300),
        Rectangle(0, 0, 400, 400),
        Rectangle(0, 0, 500, 500)
    )

    val imageTypes = arrayOf(
        BufferedImage.TYPE_INT_RGB,
        BufferedImage.TYPE_INT_ARGB
    )




    val header = arrayOf(
        "Source Image Path", "Source Image Format", "Source Image Type", "Source Image Size", "Source Image Width", "Source Image Height", "Source Image w * h",
        "Scale Algorithm", "Target Image Format", "Target Image Type", "Target Image Size", "Target Image Width", "Target Image Height", "Target Image w * h",
        "Time Cost(ms)"
    ).joinToString(", ")
    println(header)

    for (imgPath in sourceImagePaths) {

        val reader = ImageIO.getImageReadersBySuffix(imgPath.substring(imgPath.lastIndexOf(".") + 1)).next()
        ImageIO.createImageInputStream(Files.newInputStream(Paths.get(imgPath))).use {
            reader.input = it

            val bufferedImage = reader.read(0)

            for (scaleAlgo in scaleAlgos) {

                for (scaleSize in scaleSizes) {
                    for (imageType in imageTypes) {
                        for (imageFormat in imageFormats) {
                            if (imageFormat == "jpg" && imageType == BufferedImage.TYPE_INT_ARGB) {
                                continue
                            }

                            val start = System.currentTimeMillis()
                            val scaledImage = BufferedImage(scaleSize.width, scaleSize.height, imageType)
                            val graphics = scaledImage.createGraphics()
                            graphics.drawImage(bufferedImage, 0, 0, scaleSize.width, scaleSize.height, null)
                            graphics.dispose()
                            val targetFilename =
                                "${imgPath}-${scaleAlgo}-${scaleSize.width}x${scaleSize.height}-${imageType}.${imageFormat}"
                            ImageIO.write(scaledImage, imageFormat, File(targetFilename))
                            print("$imgPath, ${reader.formatName}, ${bufferedImage.type}, ${Files.size(Paths.get(imgPath))}, ${bufferedImage.width}, ${bufferedImage.height}, ${bufferedImage.width*bufferedImage.height},")
                            println("$scaleAlgo, $imageFormat, $imageType, ${Files.size(Paths.get(targetFilename))}, ${scaledImage.width}, ${scaledImage.height}, ${scaledImage.width*scaledImage.height}, ${System.currentTimeMillis() - start}")
                        }
                    }
                }

            }
        }
    }




}