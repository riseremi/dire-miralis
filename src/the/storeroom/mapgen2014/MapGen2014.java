package the.storeroom.mapgen2014;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Remi
 */
public final class MapGen2014 {

    /**
     * 1) построить начальную комнату (currentStructure = ROOM => TUNNEL) 1.1)
     * если currentStructure == ROOM, то строится только TUNNEL 2) выбрать стену
     * 3) сгенерировать тоннель 4) проверить, возможно ли разместить тоннель а)
     * если нет, вернуться к шагу 2 б) если да, разместить (currentStructure =
     * TUNNEL => ROOM || TUNNEL) 5) если currentStructure == TUNNEL, то
     * выбираем, что строить 6) если строим тоннель, goto 2 7) если строим
     * комнату а) выбрать направление б) сгенерировать комнату в) проверить,
     * возможно ли разместить комнату г) если нет, goto а д) если да, разместить
     * (currentStructure = ROOM => TUNNEL) е) goto 1.1
     */
    private static final int ROOM_MIN_W = 2, ROOM_MIN_H = 2;
    private static final int ROOM_MAX_W = 6 - ROOM_MIN_W, ROOM_MAX_H = 6 - ROOM_MIN_H;
    private static final int TUNNEL_MIN_LENGTH = 1;
    private static final int TUNNEL_MAX_LENGTH = 4 - TUNNEL_MIN_LENGTH;
    private final int MAP_W, MAP_H;
    private int currentRooms;
    //
    STRUCTURE structure;
    //
    private final static Random rnd = new Random();
    private final ArrayList<RectangleRoom> structures = new ArrayList<>();
    private static final int TUNNEL_WIDTH = 1;

    private enum STRUCTURE {

        ROOM, TUNNEL
    }

    public MapGen2014(int MAX_ROOMS, int MAP_W, int MAP_H) {
        this.MAP_W = MAP_W;
        this.MAP_H = MAP_H;
        this.structure = STRUCTURE.ROOM;
        generateDungeon(MAX_ROOMS);
    }

    public void generateDungeon(int MAX_ROOMS) {
        RectangleRoom initRoom = addInitialRoom();

        for (int i = 0; currentRooms <= MAX_ROOMS; i++) {
            if (structure == STRUCTURE.ROOM) {
                structure = STRUCTURE.TUNNEL;
            } else {
                structure = STRUCTURE.ROOM;
//                structure = selectStructure();
            }
            switch (structure) {
                case ROOM:
                    currentRooms++;
                    initRoom = buildRoom(initRoom);
                    break;
                case TUNNEL:
                    initRoom = buildTunnel(initRoom);
                    break;
            }
        }

    }

    private RectangleRoom buildTunnel(RectangleRoom srcRoom) {
        RectangleRoom tunnel1 = generateTunnel(srcRoom);
        if (!checkTunnel(tunnel1) && !isOutOfBounds(tunnel1)) {
            return buildStructure(tunnel1);
        } else {
            return buildTunnel(srcRoom);
        }
    }

    private RectangleRoom buildRoom(RectangleRoom srcRoom) {
        RectangleRoom room = generateRoom(srcRoom);
        if (!checkRoom(room) && !isOutOfBounds(room)) {
            return buildStructure(room);
        } else {
            return buildRoom(srcRoom);
        }
    }

    private RectangleRoom addInitialRoom() {
        //TODO
        int w = rnd.nextInt(ROOM_MAX_W) + ROOM_MIN_W;
        int h = rnd.nextInt(ROOM_MAX_H) + ROOM_MIN_H;

//        int x = rnd.nextInt(MAP_W - w);
//        int y = rnd.nextInt(MAP_H - h);
        int x = MAP_W / 2 - w / 2;
        int y = MAP_H / 2 - h / 2;

        RectangleRoom room = new RectangleRoom(x, y, w, h);
        buildStructure(room);

        return room;
    }

    private RectangleRoom generateTunnel(RectangleRoom srcRoom) {
//        int direction = rnd.nextInt(4); //FIXME
        int direction = rnd.nextInt(4);
        int tunnelLength = rnd.nextInt(TUNNEL_MAX_LENGTH) + TUNNEL_MIN_LENGTH;
        int x = 0, y = 0, w, h;

        switch (direction) {
            case 0: //up
                x = srcRoom.x + rnd.nextInt(srcRoom.width);
                y = srcRoom.y - tunnelLength;
                break;
            case 1: //down
                x = srcRoom.x + rnd.nextInt(srcRoom.width);
                y = srcRoom.y + srcRoom.height;
                break;
            case 2: //left
                x = srcRoom.x - tunnelLength;
                y = srcRoom.y + rnd.nextInt(srcRoom.height);
                break;
            case 3: //right
                x = srcRoom.x + srcRoom.width;
                y = srcRoom.y + rnd.nextInt(srcRoom.height);
                break;
        }

        w = direction < 2 ? TUNNEL_WIDTH : tunnelLength;
        h = direction >= 2 ? TUNNEL_WIDTH : tunnelLength;

        RectangleRoom tunnel = new RectangleRoom(x, y, w, h);

        return tunnel;
    }

    private RectangleRoom generateRoom(RectangleRoom srcRoom) {
        //TODO
        int direction = rnd.nextInt(4);
        int type = rnd.nextInt(8);

        int w = type != 4 ? rnd.nextInt(ROOM_MAX_W) + ROOM_MIN_W : ROOM_MIN_W * 2;
        int h = type != 4 ? rnd.nextInt(ROOM_MAX_H) + ROOM_MIN_H : ROOM_MIN_H * 3;

        int x = rnd.nextInt(MAP_W - w);
        int y = rnd.nextInt(MAP_H - h);

        switch (direction) {
            case 0: //up
                x = srcRoom.x + rnd.nextInt(srcRoom.width);
                y = srcRoom.y - h;
                break;
            case 1: //down
                x = srcRoom.x + rnd.nextInt(srcRoom.width);
                y = srcRoom.y + srcRoom.height;
                break;
            case 2: //left
                x = srcRoom.x - w;
                y = srcRoom.y + rnd.nextInt(srcRoom.height);
                break;
            case 3: //right
                x = srcRoom.x + srcRoom.width;
                y = srcRoom.y + rnd.nextInt(srcRoom.height);
                break;
        }

//        int x = rnd.nextInt(MAP_MAX_W - w);
//        int y = rnd.nextInt(MAP_MAX_H - h);
//        int x = srcRoom.x + rnd.nextInt(srcRoom.width);
//        int y = srcRoom.y + rnd.nextInt(srcRoom.height);
        final RectangleRoom room = new RectangleRoom(x, y, w, h, direction);

        return room;
    }

    private boolean checkRoom(RectangleRoom room) {
        int offset = room.getDirection();
        int x = room.x;
        int y = room.y;
        int w = room.width;
        int h = room.height;

        switch (offset) {
            case 0: //up
                x -= 1;
                y -= 1;
                w += 1;
                break;
            case 1: //down
                x -= 1;
                y += 1;
                w += 1;
                break;
            case 2: //left
                x -= 1;
                y -= 1;
                h += 1;
                break;
            case 3: //right
                x += 1;
                y -= 1;
                h += 1;
                break;

        }

        RectangleRoom rectangleRoom = new RectangleRoom(x, y, w, h);

        return intersects(rectangleRoom);
    }

    private boolean checkTunnel(RectangleRoom tunnel) {
        RectangleRoom outerTunnel;
        boolean vertical;
        vertical = tunnel.width == TUNNEL_WIDTH;
        if (vertical) {
            outerTunnel = new RectangleRoom(tunnel.x - TUNNEL_WIDTH, tunnel.y, tunnel.width * 3, tunnel.height);
        } else {
            outerTunnel = new RectangleRoom(tunnel.x, tunnel.y - TUNNEL_WIDTH, tunnel.width, tunnel.height * 3);
        }
        return intersects(outerTunnel);
    }

    private RectangleRoom buildStructure(RectangleRoom structure) {
        //TODO
        structures.add(structure);
        return structure;
    }

    public boolean intersects(RectangleRoom room) {
        for (RectangleRoom s : structures) {
            if (s.intersects(room)) {
                return true;
            }
        }
        return false;
    }

    private boolean isOutOfBounds(RectangleRoom room) {
        boolean o1 = room.x + room.width > MAP_W - 1 || room.x < 1;
        boolean o2 = room.y + room.height > MAP_H - 1 || room.y < 1;
        return o1 || o2;
    }

    public ArrayList<RectangleRoom> getRooms() {
        return structures;
    }
}
