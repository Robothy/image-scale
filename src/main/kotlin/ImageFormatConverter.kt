import java.io.FileInputStream
import java.nio.file.Paths
import javax.imageio.ImageIO

fun main() {

    val sourceImgPath = "C:\\tmp\\large-images\\Александр_Андреевич_Иванов_-_Явление_Христа_народу_(Явление_Мессии)_-_Google_Art_Project.jpg"
    val destinationFormat = "png"

    ImageIO.createImageInputStream(FileInputStream(sourceImgPath)).use { imageInputStream ->
        val imageReader = ImageIO.getImageReaders(imageInputStream).next()
        imageReader.input = imageInputStream
        val image = imageReader.read(0)
        val w = image.width
        val h = image.height
        ImageIO.write(image, destinationFormat, Paths.get("C:\\tmp\\large-images\\large-${w}x${h}.$destinationFormat").toFile())
    }


}