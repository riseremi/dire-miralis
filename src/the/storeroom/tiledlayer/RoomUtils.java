package the.storeroom.tiledlayer;

import java.util.Random;
import the.storeroom.Game;
import the.storeroom.entity.Player;

/**
 *
 * @author Remi
 */
public class RoomUtils {

    private static final Random rnd = new Random();
    public static final int[] STANDARD_ROOM = {3, 3, 3, 3, 13, 3, 3, 3, 3};
//    public static final int[] STANDARD_ROOM = {0, 1, 2, 8, 33, 10, 16, 17, 18};

    private static final int FLOOR_TILE = 13,
            WALL_TILE = 12,
            EMPTY_TILE = 28;

    public static void buildRoom(Map map, int x, int y, int roomWidth, int roomHeight, int[] sides) {
        TiledLayer layer = map.getTiles();
        TiledLayer obstacles = map.getObstacles();
        //sides = {topleft, up, topright,
        //         left, floor, right,
        //         botleft, down, botright}

        boolean canBuildRoom = true, canDrawTunnel = false;
        if (roomWidth < 2 || roomHeight < 2) {
            canBuildRoom = false;
        }
        if (roomWidth == 1 || roomHeight == 1) {
            canDrawTunnel = true;
        }

        if (canDrawTunnel) {
            layer.fillRectTile(x, y, roomWidth, roomHeight, sides[4]);

//            for (int i = 0; i < roomWidth; i++) {
//                if (layer.getTile(x, y) != 14 && layer.getTile(x, y + 1) != 13) {
//                    layer.setTile(x, y - 1, 14);
//                }
//            }
//            layer.fillRectTile(x, y - 1, roomWidth, 1, 14);
            obstacles.fillRectTile(x, y, roomWidth, roomHeight, 0);
        }

        if (canBuildRoom) {
            layer.fillRectTile(x, y, roomWidth, roomHeight, sides[4]);

//            for (int i = 0; i < roomWidth; i++) {
//                if (layer.getTile(x, y) != 14 && layer.getTile(x, y + 1) != 13) {
//                    layer.setTile(x, y - 1, 14);
//                }
//            }
//            layer.fillRectTile(x, y - 1, roomWidth, 1, 14);
            obstacles.fillRectTile(x, y, roomWidth, roomHeight, 0);
//            layer.fillRectTile(x, y, roomWidth, roomHeight, sides[4]);
//
//            //upper + down walls
//            layer.fillRectTile(x, y, roomWidth, 1, sides[1]);
//            obstacles.fillRectTile(x, y, roomWidth, 1, 1);
//
//            layer.fillRectTile(x, y + roomHeight - 1, roomWidth, 1, sides[1]);
//            obstacles.fillRectTile(x, y + roomHeight - 1, roomWidth, 1, 1);
//
//            //left + right walls
//            layer.fillRectTile(x, y, 1, roomHeight, sides[3]);
//            obstacles.fillRectTile(x, y, 1, roomHeight, 1);
//
//            layer.fillRectTile(x + roomWidth - 1, y, 1, roomHeight, sides[3]);
//            obstacles.fillRectTile(x + roomWidth - 1, y, 1, roomHeight, 1);
//
//            //corners
//            layer.setTile(x, y, sides[0]);
//            obstacles.setTile(x, y, 1);
//
//            layer.setTile(x + roomWidth - 1, y, sides[2]);
//            obstacles.setTile(x + roomWidth - 1, y, 1);
//
//            layer.setTile(x, y + roomHeight - 1, sides[6]);
//            obstacles.setTile(x, y + roomHeight - 1, 1);
//
//            layer.setTile(x + roomWidth - 1, y + roomHeight - 1, sides[8]);
//            obstacles.setTile(x + roomWidth - 1, y + roomHeight - 1, 1);
        }
    }

    public static void fillWalls(Map map) {
        //WALL = 20
        TiledLayer layer = map.getTiles();

        for (int i = 0; i < layer.getHorizontalTilesNumber(); i++) {
            for (int j = 0; j < layer.getVerticalTilesNumber(); j++) {
                int tile = layer.getTile(i, j);

                int up = layer.getTile(i, j - 1);
                int down = layer.getTile(i, j + 1);
                int left = layer.getTile(i - 1, j);
                int right = layer.getTile(i + 1, j);
                //checking
                if (tile == FLOOR_TILE) {
                    checkTile(i, j - 1, up, layer);
                    checkTile(i, j + 1, down, layer);
                    checkTile(i - 1, j, left, layer);
                    checkTile(i + 1, j, right, layer);
                }
            }
        }

        //
        //additional random floor tiles
        for (int i = 0; i < layer.getHorizontalTilesNumber(); i++) {
            for (int j = 0; j < layer.getVerticalTilesNumber(); j++) {
                int tile = layer.getTile(i, j);

                int up = layer.getTile(i, j - 1);
                int down = layer.getTile(i, j + 1);
                if (tile == FLOOR_TILE) {
                    if (tile == WALL_TILE && up == FLOOR_TILE && down == FLOOR_TILE) {
                        if (rnd.nextInt(8) == 0) {
                            layer.setTile(i, j, FLOOR_TILE);
                        }
                    }
                }
            }
        }

    }

    private static void checkTile(int x, int y, int tile, TiledLayer layer) {
        if (tile == EMPTY_TILE) {
            layer.setTile(x, y, WALL_TILE);
        }
    }

    public static void placePlayer(Player player, Map map) {
        TiledLayer obstacles = map.getObstacles();

        final int w = obstacles.getHorizontalTilesNumber();
        final int h = obstacles.getVerticalTilesNumber();

        while (true) {
            int wPos = rnd.nextInt(w / 2);
            int hPos = rnd.nextInt(h / 2);

            if (obstacles.getTile(wPos + player.getBlocksX(), hPos + player.getBlocksY()) == 0) {
                map.setPosition(wPos, hPos);
                break;
            }
        }

    }
}
