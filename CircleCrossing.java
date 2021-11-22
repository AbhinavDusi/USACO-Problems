import java.io.*; 
import java.util.*; 
class CircleCrossing {
    public static int crossingPaths (int[] array) {
        List<Integer> firsts = new ArrayList<>(); 
        Map<Integer, Boolean> map = new HashMap<>(); 
        FenwickTree ft = new FenwickTree(array.length); 
        int[][] indices = new int[array.length / 2 + 1][2];
        int total = 0; 
        for (int i = 0; i < array.length; i ++)
            if (indices[array[i]][0] == 0)
                indices[array[i]][0] = i + 1;
            else
                indices[array[i]][1] = i + 1; 
        for (int i = 0; i < array.length; i ++)
            map.put(array[i], false); 
        for (int i = 0; i <array.length; i ++)
            if(!map.get(array[i])) {
                map.put(array[i], true); 
                firsts.add(array[i]); 
            }
        for (int i = 0; i < firsts.size(); i ++) {
             ft.update(indices[firsts.get(i)][0], 1); 
             ft.update(indices[firsts.get(i)][1], 1);  
             total += ft.getSumIndices(indices[firsts.get(i)][0] + 1, indices[firsts.get(i)][1] - 1); 
        }
        return total; 
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(“circlecross.in")); 
        PrintWriter pw = new PrintWriter(new File(“circlecross.out")); 
        int lines = Integer.parseInt(br.readLine()); 
        int[] cArray = new int[lines * 2];
        for (int i = 0; i < lines * 2; i ++)
            cArray[i] = Integer.parseInt(br.readLine()); 
        pw.println(crossingPaths(cArray));
        br.close();
        pw.close(); 
    }
}
class FenwickTree {
    public int[] arr; 
    public FenwickTree(int n) {
        arr = new int[n + 1];
    }
    public void update(int index, int value) {
        while (index < arr.length) {
            arr[index] += value; 
            index += Integer.lowestOneBit(index); 
        }
    }
    public int getSum(int index) {
        int total = 0; 
        while (index > 0) {
            total += arr[index]; 
            index -= Integer.lowestOneBit(index); 
        }
        return total; 
    }
    public int getSumIndices(int aIndex, int bIndex) {
        return getSum(bIndex) - getSum(aIndex - 1); 
    }
    
}