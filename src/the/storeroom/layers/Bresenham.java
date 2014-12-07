package the.storeroom.layers;

/**
 *
 * @author Remi
 */
public final class Bresenham {

    public static void line(int x, int y, int x2, int y2, int tile, int[][] fovMap, ObstaclesLayer obstacles) {
        int w = x2 - x;
        int h = y2 - y;
        int dx1 = 0, dy1 = 0, dx2 = 0, dy2 = 0;
        if (w < 0) {
            dx1 = -1;
        } else if (w > 0) {
            dx1 = 1;
        }
        if (h < 0) {
            dy1 = -1;
        } else if (h > 0) {
            dy1 = 1;
        }
        if (w < 0) {
            dx2 = -1;
        } else if (w > 0) {
            dx2 = 1;
        }
        int longest = Math.abs(w);
        int shortest = Math.abs(h);
        if (!(longest > shortest)) {
            longest = Math.abs(h);
            shortest = Math.abs(w);
            if (h < 0) {
                dy2 = -1;
            } else if (h > 0) {
                dy2 = 1;
            }
            dx2 = 0;
        }
        int numerator = longest >> 1;
        for (int i = 0; i <= longest; i++) {
//            putpixel(x, y, color);
//            System.out.println("x:" + x + " y:" + y);

            try {
                fovMap[x][y] = FOVLayer.VISIBLE;
            } catch (Exception e) {
            }
            if (!obstacles.isPassable(x, y)) {
                return;
            }

            numerator += shortest;
            if (!(numerator < longest)) {
                numerator -= longest;
                x += dx1;
                y += dy1;
            } else {
                x += dx2;
                y += dy2;
            }
        }
    }
}
