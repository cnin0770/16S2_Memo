package cnin0770.memo;

import android.graphics.Bitmap;

/**
 * Created by cnin0770 on 08/10/2016.
 */
public class GridItem {
    Bitmap image;
    String name;

    public GridItem(Bitmap image, String name) {
        super();
        this.image = image;
        this.name = name;
    }

    public Bitmap getImage() {
        return image;
    }

    public String getTitle() {
        return name;
    }
}
