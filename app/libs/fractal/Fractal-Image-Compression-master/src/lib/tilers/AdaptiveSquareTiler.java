package lib.tilers;

import java.awt.image.BufferedImage;

import java.util.ArrayList;

/**
 * Wrapper class around {@code AdaptiveRectangularTiler}
 *
 * @see AdaptiveRectangularTiler
 */
public class AdaptiveSquareTiler implements Tiler<BufferedImage> {

    private AdaptiveRectangularTiler adaptRectTiler;

    public AdaptiveSquareTiler(final int initialBlocksOnOneSide) {
        this.adaptRectTiler = new AdaptiveRectangularTiler(initialBlocksOnOneSide, initialBlocksOnOneSide);
    }

    @Override
    public ArrayList<BufferedImage> tile(final BufferedImage image) {
        return this.adaptRectTiler.tile(image);
    }
}
