package lib.core;


import lib.transformations.ImageTransform;

import java.awt.Point;
import java.awt.image.BufferedImage;

import java.io.Serializable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * a fractal model represents the compressed form of the image.<br />
 * <br />
 * {@code from <Range, <Domain, Transform>>      }<br />
 * {@code ..to <Domain, <Transform, {Range}>>  }<br />
 * <br />
 * or from:
 * <br />
 * {@code Point1 - Domain1 - Transform1}<br />
 * {@code Point2 - Domain2 - Transform1}<br />
 * {@code Point3 - Domain1 - Transform2}<br />
 * {@code Point4 - Domain1 - Transform2}<br />
 * {@code Point5 - Domain2 - Transform1}<br />
 * <br />
 * to:
 * <br />
 * {@code Domain1 -[ Transform1 -[ Point1 ]] }<br />
 * {@code ....... .[ Transform2 -[ Point3 ]  }<br />
 * {@code ....... ............. .[ Point4 ]] }<br />
 * {@code Domain2 -[ Transform1 -[ Point2 ]  }<br />
 * {@code ....... ............. .[ Point5 ]] }<br />
 * <br />
 * in other words, instead of storing for each range the transform <br />
 * and the domain, we store the domain once, along with a set of   <br />
 * transforms, with each transform, we store a set of points that  <br />
 * represent the position of the ranges in the original image.
 * 
 * @see Compressor
 */
public class FractalModel implements Serializable {

    private Map<ImageHolder, Map<ImageTransform, Set<Point>>> fmodel;

    public FractalModel(Map<Point, Map.Entry<BufferedImage, ImageTransform>> simplemodel) {
        fmodel = new HashMap<ImageHolder, Map<ImageTransform, Set<Point>>>();

        analyze(simplemodel);
    }

    private void analyze(Map<Point, Map.Entry<BufferedImage, ImageTransform>> simplemodel) {

        /*
         * for each point, extract the appropriate domain and transform
         */
        for (Point point : simplemodel.keySet()) {
            ImageHolder    domain    = new ImageHolder(simplemodel.get(point).getKey());
            ImageTransform transform = simplemodel.get(point).getValue();

            /*
             * if the domain is new to the model, add it and create
             * a map for the transform and the range points.
             */
            if (!fmodel.containsKey(domain)) {
                fmodel.put(domain, new HashMap<ImageTransform, Set<Point>>());
                fmodel.get(domain).put(transform, new HashSet<Point>());
            } else if (!fmodel.get(domain).containsKey(transform)) {

                /*
                 * if the domain is not new, but the transform is new,
                 * add the trasform and create a map for the range points
                 */
                fmodel.get(domain).put(transform, new HashSet<Point>());
            }

            /*
             * finally add the point
             */
            fmodel.get(domain).get(transform).add(point);
        }
    }

    public Map<ImageHolder, Map<ImageTransform, Set<Point>>> getModel() {
        return fmodel;
    }
}
