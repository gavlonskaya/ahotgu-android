package lib;

import java.awt.Point;
import lib.comparators.Distanceator;

import lib.tilers.Tiler;

import lib.transformations.ImageTransform;
import lib.transformations.ScaleTransform;

import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import lib.core.FractalModel;
import lib.utils.Utils;

/**
 * fractal compressor instance. combines the tiler and
 * comparator classes to create a fractal image model
 */
public class Compressor extends Observable {

    private final ScaleTransform              scaleTransform;
    private final Tiler<BufferedImage>        tiler;
    private final Distanceator<BufferedImage> comparator;
    private final Set<ImageTransform>         transforms;
    private final Set<BufferedImageOp>        filters;

    /**
     * @param scaleTransform the scale difference between the ranges and the domains
     * @param tiler the tiler used to tile the image
     * @param comparator the comparator used to compare the tiles of the image
     * @param transforms a list of transform to apply to the tiles of the image
     * @param observer an observer receiving progress results for the compression - allowed to be null
     *
     * @throws NullPointerException if any field is null
     *
     * @see #compress(java.awt.image.BufferedImage)
     * @see Observable
     * @see Observer#update(java.util.Observable, java.lang.Object)
     */
    public Compressor(final ScaleTransform scaleTransform,
                      final Tiler<BufferedImage> tiler,
                      final Distanceator<BufferedImage> comparator,
                      final Set<ImageTransform> transforms,
                      Observer observer) throws NullPointerException {
        this(scaleTransform, tiler, comparator, transforms,
             new HashSet<BufferedImageOp>(0), observer);
    }

    /**
     * @param scaleTransform the scale difference between the ranges and the domains
     * @param tiler the tiler used to tile the image
     * @param comparator the comparator used to compare the tiles of the image
     * @param transforms a set of transform to apply to the tiles of the image
     * @param filters a set of filters to apply to the image for normalization
     * @param observer an observer receiving progress results from {@code compress} - allowed to be null
     *
     * @throws NullPointerException if any field is null
     *
     * @see #compress(java.awt.image.BufferedImage)
     * @see Observable
     * @see Observer#update(java.util.Observable, java.lang.Object)
     */
    public Compressor(final ScaleTransform scaleTransform,
                      final Tiler<BufferedImage> tiler,
                      final Distanceator<BufferedImage> comparator,
                      final Set<ImageTransform> transforms,
                      final Set<BufferedImageOp> filters,
                      Observer observer) throws NullPointerException {
        assert (comparator != null) && (transforms != null) && (filters != null)
               && (tiler != null) && (scaleTransform != null) : "Null elements now allowed";

        this.comparator = comparator;
        this.tiler      = tiler;
        this.filters    = filters;
        this.transforms = transforms;
        this.scaleTransform = scaleTransform;

        if (observer != null) {
            this.addObserver(observer);
        }
    }

    /**
     * Compress a given image. Compressions takes place as a mapping of
     * small images and transforms to points. Applying the transforms
     * to the images and placing the resulted transformed images
     * to the mapped points, the original image is reassembled.
     *
     * @param image the image to compress
     * @return a mapping of points to images and transforms.
     */
    public FractalModel compress(BufferedImage image) {
        assert image != null : "Cannot compress null image";

        /*
         * Normalization. Before tiling the image, pass it throw a set of filters.
         * This might improve results, if used wisely.
         */
        for (BufferedImageOp filter : filters) {
            image = filter.filter(image, null);
        }

        /*
         * range blocks are the tiles of the original(but filtered) image.
         */
        ArrayList<BufferedImage> rangeblocks = tiler.tile(image);
        int rangessize = rangeblocks.size();

        /*
         * the domain image to tile, is a scaled factor of the size
         * of the original image (scalex * width; scaley * height).
         * domain and range blocks have the same size, but, domain
         * blocks are less in number than range blocks.
         *
         *     #domains = (scalex * w) * (scaley * h)
         *     #domains = scalex * scaley * #ranges
         *
         * We pre-calculate all transforms to hold all possible
         * transformed and original domain blocks.
         *
         *     #alldomains = #transforms * scalex * scaley * #ranges
         *
         * We map each result of a transform with the original
         * domain and transform operation which resulted it.
         */
        Map<BufferedImage, Map.Entry<BufferedImage, ImageTransform>> domainblocks =
            new HashMap<BufferedImage, Map.Entry<BufferedImage, ImageTransform>>(
                (int) (transforms.size() * rangessize * scaleTransform.getScalex() * scaleTransform.getScaley()));

        for (BufferedImage domainImg : tiler.tile(scaleTransform.transform(image))) {
            for (ImageTransform transform : transforms) {
                domainblocks.put(transform.transform(domainImg),
                                 new SimpleEntry<BufferedImage, ImageTransform>(domainImg, transform));
            }
        }

        /* mapping between a range position and
         * most suitable domainblock and transform
         * that produce that range
         */
        Map<Point, Map.Entry<BufferedImage, ImageTransform>> simplemodel =
            new HashMap<Point, Map.Entry<BufferedImage, ImageTransform>>(rangessize);
        int width = image.getWidth() / rangeblocks.get(0).getWidth();

        /*
         * After the end of each domain loop, or in other words, before
         * we iterate to the next range image, we have found the best
         * match for the current (and all previous) range images.
         * Thus, we do not need to hold a mapping between the range image
         * and the domain image along with its distance. We hold the best
         * (minimum) distance found so far, in the a variable, which is
         * reset in every range image iteration.
         *
         * for each rangeblock, compare it to each domainblock
         * if the difference is smaller than the best current
         * then add the rangeblock's position and appropriate
         * domainblock and transformation.
         */
        for (int rangeidx = 0; rangeidx < rangessize; rangeidx++) {
            Point  point   = Utils.indexToPoint(rangeidx, width);
            double mindiff = Double.MAX_VALUE;

            for (BufferedImage domainblock : domainblocks.keySet()) {
                double diff = comparator.distance(rangeblocks.get(rangeidx), domainblock);

                /*
                 * If we haven't seen the image before (which means
                 * this is a new range image iteration), we store
                 * the distance and map the range and domain images.
                 * If we've seen the image before, but the comparison
                 * yeilds better results (the new difference is smaller
                 * than the best (minimum) so far), we update the best
                 * difference and map the range to the new domain image.
                 */
                if (!simplemodel.containsKey(point) || (mindiff > diff)) {
                    simplemodel.put(point, domainblocks.get(domainblock));
                    mindiff = diff;
                }
            }

            this.setChanged();
            this.notifyObservers(new int[]{rangeidx, rangessize});
        }

        return new FractalModel(simplemodel);
    }
}
