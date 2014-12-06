package the.storeroom;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Remi
 */
public final class MapGenerator {

    private ArrayList<Rectangle> rooms;
    private final Random rnd = new Random();
    private final int mapWidth, mapHeight;
    private final int maxRoomWidth, maxRoomHeight;
    private final int maxRooms;

    public MapGenerator(int maxRooms, int mapWidth, int mapHeight, int maxRoomWidth, int maxRoomHeight) {
        rooms = new ArrayList<>();
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.maxRoomWidth = maxRoomWidth;
        this.maxRoomHeight = maxRoomHeight;
        this.maxRooms = maxRooms;

        //initial room
//        rooms.add(new Rectangle(0, 0, 0, 0));
        //build as listed
        addInitialRoom();
        addTunnel(0);

//        for (int i = 0; i < maxRooms; i++) {
//            addRoom();
//        }
    }

    public void addRoom(Rectangle room) {
//        if (!intersects(room)) {
        rooms.add(room);
//        } else {
//            System.out.println("Error while adding room.");
//        }
    }

    public void addRoom() {
        Rectangle room = new Rectangle();

//        int w = rnd.nextInt(maxRoomWidth - maxRoomWidth / 2) + maxRoomWidth / 2;
//        int h = rnd.nextInt(maxRoomHeight - maxRoomHeight / 2) + maxRoomHeight / 2;
        Dimension size = getRandomRoomDimension();

        int x = rnd.nextInt(mapWidth - size.width);
        int y = rnd.nextInt(mapHeight - size.height);

        room.setBounds(x, y, size.width, size.height);

        if (!intersects(room)) {
            rooms.add(room);
        } else {
            addRoom();
        }
    }

    public void addTunnel(int roomId) {
        Rectangle room = rooms.get(roomId);
        int wall = rnd.nextInt(4); //up down left right
        int wallX = 0, wallY = 0;
        int wallLength = rnd.nextInt(maxRoomWidth - maxRoomWidth / 3) + maxRoomWidth / 3;
        int lengthX = 1, lengthY = 1;

        switch (wall) {
            case 0: //up
                wallY = room.y - wallLength;
                wallX = room.x + rnd.nextInt(room.width);
                lengthY = wallLength;
                break;
            case 1: //down
                wallY = room.y + room.height;
                wallX = room.x + rnd.nextInt(room.width);
                lengthY = wallLength;
                break;
            case 2: //left
                wallX = room.x - wallLength;
                wallY = room.y + rnd.nextInt(room.height);
                lengthX = wallLength;
                break;
            case 3: //right
                wallX = room.x + room.width;
                wallY = room.y + rnd.nextInt(room.height);
                lengthX = wallLength;
                break;
        }

        Rectangle tunnel = new Rectangle(wallX, wallY, lengthX, lengthY);
//        System.out.println("" + tunnel.x + ", " + tunnel.y);
//        System.out.println("" + tunnel.width + ", " + tunnel.height);
//        System.out.println("");

        boolean canBuild = intersects(tunnel) && !outOfBounds(tunnel);

        //FIX THIS SHIT
        if (canBuild) {
            addRoom(tunnel);

            int offsetX = lengthX != 1 ? rnd.nextInt(4) + 1 : 0;
            int offsetY = lengthY != 1 ? rnd.nextInt(4) + 1 : 0;
            final int newRoomX = wallX + lengthX - offsetX;
            final int newRoomY = wallY + lengthY - offsetY;
            final Dimension newRoomDimension = getRandomRoomDimension();
            final Rectangle newRoom = new Rectangle(newRoomX, newRoomY, newRoomDimension.width,
                    newRoomDimension.height);

            addRoom(newRoom);
            if (roomId < maxRooms) {
                addTunnel(++roomId);
            }
        } else {
            addTunnel(roomId);
        }

    }

    private Dimension getRandomRoomDimension() {
        return new Dimension(rnd.nextInt(maxRoomWidth - maxRoomWidth / 2) + maxRoomWidth / 2,
                rnd.nextInt(maxRoomHeight - maxRoomHeight / 2) + maxRoomHeight / 2);
    }

    public void addInitialRoom() {
        Rectangle room = new Rectangle();

        int w = rnd.nextInt(maxRoomWidth - maxRoomWidth / 2) + maxRoomWidth / 2;
        int h = rnd.nextInt(maxRoomHeight - maxRoomHeight / 2) + maxRoomHeight / 2;

        int x = rnd.nextInt(mapWidth - w);
        int y = rnd.nextInt(mapHeight - h);

        room.setBounds(x, y, w, h);

        rooms.add(room);
    }

    public boolean intersects(Rectangle room) {
        Rectangle room2 = new Rectangle(room.x - 1, room.y - 1, room.width + 1, room.height + 1);
        return rooms.stream().anyMatch((room1) -> (room2.intersects(room1)));
    }

    public boolean intersectsOriginal(Rectangle room) {
        return rooms.stream().anyMatch((room1) -> (room.intersects(room1)));
    }

    public ArrayList<Rectangle> getRooms() {
        return rooms;
    }

    private boolean outOfBounds(Rectangle tunnel) {
        boolean one = tunnel.x + tunnel.width > mapWidth;
        boolean two = tunnel.y + tunnel.height > mapHeight;
//        System.out.println(one || two);
        return one || two;
    }
}
