package app.configuration.converters;

import app.configuration.Options;

import com.beust.jcommander.IStringConverter;
import lib.tilers.RectangularPixelTiler;

/**
 * a converter that turns a series of string arguments into a pixel tiler
 * 
 * @see RectangularPixelTiler
 */
public class PixelTilerConverter implements IStringConverter<RectangularPixelTiler> {

    @Override
    public RectangularPixelTiler convert(String arg) {
        String[] wh = arg.split(Options.tilerdelimit);
        return new RectangularPixelTiler(Integer.parseInt(wh[0]), Integer.parseInt(wh[1]));
    }
}
