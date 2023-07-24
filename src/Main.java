import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class DDDAlgorithm {
    private int numNodes;
    private int[][] waitForGraph;

    public DDDAlgorithm(int numNodes) {
        this.numNodes = numNodes;
        waitForGraph = new int[numNodes][numNodes];
    }

    public void collectStatusTables(String fileName) {
        try {
            Scanner scanner = new Scanner(new File(fileName));
            numNodes = scanner.nextInt();
            waitForGraph = new int[numNodes][numNodes];

            for (int i = 0; i < numNodes; i++) {
                for (int j = 0; j < numNodes; j++) {
                    waitForGraph[i][j] = scanner.nextInt();
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void printStatusTables() {
        System.out.println("Resource Status Table:");
        for (int i = 0; i < numNodes; i++) {
            for (int j = 0; j < numNodes; j++) {
                System.out.print(waitForGraph[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();

        System.out.println("Process Status Table:");
        for (int i = 0; i < numNodes; i++) {
            for (int j = 0; j < numNodes; j++) {
                System.out.print(waitForGraph[j][i] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public boolean hasCycle() {
        int[] inDegree = new int[numNodes];
        List<Integer> visited = new ArrayList<>();
        List<Integer> queue = new ArrayList<>();

        for (int i = 0; i < numNodes; i++) {
            for (int j = 0; j < numNodes; j++) {
                inDegree[i] += waitForGraph[j][i];
            }
            if (inDegree[i] == 0) {
                queue.add(i);
                visited.add(i);
            }
        }

        while (!queue.isEmpty()) {
            int node = queue.remove(0);
            for (int i = 0; i < numNodes; i++) {
                if (waitForGraph[node][i] == 1) {
                    inDegree[i]--;
                    if (inDegree[i] == 0 && !visited.contains(i)) {
                        queue.add(i);
                        visited.add(i);
                    }
                }
            }
        }

        return visited.size() != numNodes;
    }

    public static void main(String[] args) {
        DDDAlgorithm algorithm = new DDDAlgorithm(0);

        algorithm.collectStatusTables("data2.txt");

        algorithm.printStatusTables();

        if (algorithm.hasCycle()) {
            System.out.println("Deadlock detected!");
        } else {
            System.out.println("No deadlock detected.");
        }
    }
}
