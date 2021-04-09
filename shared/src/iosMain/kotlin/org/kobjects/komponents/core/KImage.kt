package org.kobjects.komponents.core


actual class KImage(
    val svg: String
) {
    actual companion object {
        actual fun createSvg(code: String): KImage {
            //val source = SVGKSourceString.sourceFromContentsOfString(code)
            //val img = SVGKImage.imageWithSource(source)
            return KImage(code)
            //return KImage(
            //    img!!
            //)
         //   val data = NSData(code)
/*            val nsString = code as NSString
            val data = nsString.dataUsingEncoding(NSUTF8StringEncoding)
            return KImage(UIView.create(svgData = data, parser = null, completion = null))
*/
        }
    }


}