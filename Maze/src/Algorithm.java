import java.util.LinkedList;
import java.util.Queue;

public class Algorithm {
    class Coordinate implements Comparable<Coordinate> {
        int row;
        int column;
        int distance;
        String path;
        Queue<int[]> queue = new LinkedList<>();

        Coordinate(int row, int column, int distance, String path, int[] l) {
            this.row = row;
            this.column = column;
            this.distance = distance;
            this.path = path + " (" + (column + 1) + ", " + (row + 1) +")\n" ;
            this.queue.add(l);
        }


        @Override
        public int compareTo(Coordinate coordinate) {
            return this.distance == coordinate.distance ? this.path.compareTo(coordinate.path) : this.distance - coordinate.distance;
        }


        @Override
        public String toString() {
            return "Total distance: " + distance + "\nSTART: " + path ;
        }
    }

    public String shortestDistance(int[][] maze, int[] ball, int[] hole) {
        int rows = maze.length, cols = maze[0].length;

        boolean[][] visited = new boolean[rows][cols];

        Queue<Coordinate> pq = new LinkedList<>();
        pq.offer(new Coordinate(ball[0], ball[1], 0, "", new int[]{}));

        String[] movableDirection = {"Move up to", "Move down to", "Move left to", "Move right to"};
        int[][] movableCoordinate = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        while (!pq.isEmpty()) {
            Coordinate position = pq.poll();
            if (position.row == hole[0] && position.column == hole[1]) {
                return position.toString();
            }
            for (int i = 0; i < movableCoordinate.length; i++) {
                int row = position.row;
                int column = position.column;
                int distance = position.distance;
                String path = position.path;

                while (row >= 0 && row < rows && column >= 0
                        && column < cols && maze[row][column] == 0
                        && (row != hole[0] || column != hole[1])) {
                    row += movableCoordinate[i][0];
                    column += movableCoordinate[i][1];
                    distance += 1;
                }

                if (row != hole[0] || column != hole[1]) {
                    row -= movableCoordinate[i][0];
                    column -= movableCoordinate[i][1];
                    distance -= 1;
                }

                if (!visited[row][column]) {
                    visited[position.row][position.column] = true;
                    pq.offer(new Coordinate(row, column, distance, path + movableDirection[i], new int[]{row, column}));
                }
            }
        }
        return "Path not found!";
    }
}
