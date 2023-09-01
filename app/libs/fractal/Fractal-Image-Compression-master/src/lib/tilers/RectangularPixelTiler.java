package lib.tilers;

import java.awt.image.BufferedImage;

import java.util.ArrayList;

/**
 * Rectangle tiler. Given the pixels in the x and y axis that a
 * block must be, it tiles the image into as many blocks can fit.
 */
public class RectangularPixelTiler implements Tiler<BufferedImage> {

    private int blockwidth, blockheight;

    /**
     * @param blockwidth the pixels each block will be in the x axle
     * @param blockheight the pixels each block will be in the y axle
     */
    public RectangularPixelTiler(final int blockwidth, final int blockheight) {
        assert (blockheight > 0) && (blockwidth > 0);
        
        this.blockwidth  = blockwidth;
        this.blockheight = blockheight;
    }

    @Override
    public ArrayList<BufferedImage> tile(final BufferedImage image) {

        // normalize image -- adjust size to blocks' width and height
        int cols = image.getWidth () / blockwidth;
        int rows = image.getHeight() / blockheight;

        assert (cols > 0) && (rows > 0) : String.format("Block sizes too big - width: %d - height: %d", blockwidth, blockheight);

        BufferedImage img = image.getSubimage(0, 0, cols * blockwidth, rows * blockheight);

        ArrayList<BufferedImage> blockslist = new ArrayList<BufferedImage>(rows * cols);
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                blockslist.add(img.getSubimage(blockwidth * col, blockheight * row,
                                               blockwidth,       blockheight));
            }
        }

        return blockslist;
    }

    @Override
    public String toString() {
        return String.format("%dx%d", blockwidth, blockheight);
    }
}
