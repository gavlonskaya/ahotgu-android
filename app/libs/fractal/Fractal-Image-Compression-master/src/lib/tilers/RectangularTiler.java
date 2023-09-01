package lib.tilers;

import java.awt.image.BufferedImage;

import java.util.ArrayList;

/**
 * Rectangle tiler. Given the rows and columns, it
 * splits the image into rows x columns rectangles.
 */
public class RectangularTiler implements Tiler<BufferedImage> {

    private int rows, cols;

    /**
     *
     * @param rows the rows to tile the image
     * @param cols the columns to tile the image
     */
    public RectangularTiler(final int rows, final int cols) {
        this.rows = rows;
        this.cols = cols;
    }

    @Override
    public ArrayList<BufferedImage> tile(final BufferedImage image) {
        BufferedImage img = adjustImageSizeDown(image, rows, cols);

        int blockheight = img.getHeight() / rows;
        int blockwidth  = img.getWidth() / cols;
        
        ArrayList<BufferedImage> blockslist  = new ArrayList<BufferedImage>(rows * cols);
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                blockslist.add(img.getSubimage(blockwidth * x, blockheight * y,
                                                 blockwidth,     blockheight));
            }
        }

        return blockslist;
    }

    /**
     * Adjust image size such that it can be split by
     * the given rows and columns.
     * Adjust takes place by reducing the image size.
     *
     * @param image the image to split and fit in rows and columns
     * @param rows the rows to split the image
     * @param cols the columns to split the image
     * @return the adjusted image, the image with the correct size
     */
    private BufferedImage adjustImageSizeDown(final BufferedImage image, final int rows, final int cols) {
        int width = image.getWidth();

        while (width % cols != 0) {
            width--;
        }

        int height = image.getHeight();

        while (height % rows != 0) {
            height--;
        }

        return image.getSubimage(0, 0, width, height);
    }
}
