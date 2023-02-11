import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.io.*;
import javax.imageio.ImageIO;

class Pixel {
   BufferedImage image;
   int width;
   int height;
   public static void main(String args[]) throws Exception {
    Pixel obj = new Pixel();
     }
   //Source used for image IO: stack overflow
    public Pixel() {

    ArrayList<String> rgb = new ArrayList<>();
      try {
        
         File input = new File("1.png");
         image = ImageIO.read(input);
         width = image.getWidth();
         height = image.getHeight();
         
         
         for(int i=0; i<height; i++) {
         
            for(int j=0; j<width; j++) {
            
               Color c = new Color(image.getRGB(j, i));
               rgb.add(c.getRed() + "," + c.getGreen() + "," + c.getBlue());
               
             
            }
         }
            System.out.println("Three Most Prominent Colors: ");
            System.out.println(rgbToColor(findMainColors(rgb).get(0)));
            System.out.println(rgbToColor(findMainColors(rgb).get(1)));
            System.out.println(rgbToColor(findMainColors(rgb).get(2)));
            System.out.println("Upside Down Test (are the all warm or all cool?) :");
            String[] colorList = {rgbToColor(findMainColors(rgb).get(0)), rgbToColor(findMainColors(rgb).get(1)), rgbToColor(findMainColors(rgb).get(2))};
            System.out.println(upsideDown(colorList));
            System.out.println("Color Groups Test (are all 3 colors adjacent to each other on a color wheel?): ");
            System.out.println(colorGroupsTest(colorList));
      } catch (Exception e) {}
   }
  
    public static ArrayList<String> findMainColors(ArrayList<String> strings) {
    Map<String, Integer> frequencyMap = new HashMap<>();
    for (String str : strings) {
        if (frequencyMap.containsKey(str)) {
            frequencyMap.put(str, frequencyMap.get(str) + 1);
        } else {
            frequencyMap.put(str, 1);
        }
    }

    ArrayList<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(frequencyMap.entrySet());
    sortedEntries.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));

    ArrayList<String> result = new ArrayList<>();
    for (int i = 0; i < 3 && i < sortedEntries.size(); i++) {
        result.add(sortedEntries.get(i).getKey());
    }
    return result;
}
    
    public static String rgbToColor(String rgb){
        int r = Integer.parseInt(rgb.split(",")[0]);
        int g = Integer.parseInt(rgb.split(",")[1]);
        int b = Integer.parseInt(rgb.split(",")[2]);
        int max = Math.max(r, Math.max(g, b));
         int min = Math.min(r, Math.min(g, b));
        int delta = max - min;
    
     if (delta == 0) {
        if (max == 0) {
            return "Black";
        } else {
            return "White";
        }
    } else if (max == r) {
        if (g >= b) {
            // Yellow - Yellow Green
            if (delta <= (255 - max) / 4) {
                return "Yellow";
            } else {
                return "Yellow Green";
            }
        } else {
            // Red - Red Violet
            if (delta <= (255 - max) / 4) {
                return "Red";
            } else {
                return "Red Violet";
            }
        }
    } else if (max == g) {
        if (r >= b) {
            // Yellow Green - Green
            if (delta <= (255 - max) / 4) {
                return "Yellow Green";
            } else {
                return "Green";
            }
        } else {
            // Blue Green - Blue
            if (delta <= (255 - max) / 4) {
                return "Blue Green";
            } else {
                return "Blue";
            }
        }
    } else {
        if (r >= g) {
            // Red Violet - Violet
            if (delta <= (255 - max) / 4) {
                return "Red Violet";
            } else {
                return "Violet";
            }
        } else {
            // Blue - Blue Violet
            if (delta <= (255 - max) / 4) {
                return "Blue";
            } else {
                return "Blue Violet";
            }
        }
    }
    }

    public static boolean colorGroupsTest(String[] rgb){
        ArrayList<String> rgbList = new ArrayList<>(Arrays.asList(rgb));
    String[] colors = { "Red", "Red Orange", "Orange", "Yellow Orange", "Yellow", "Yellow Green", "Green",
            "Blue Green", "Blue", "Blue Violet", "Violet", "Red Violet" };

    for (int i = 0; i < rgbList.size(); i++) {
        int temp = findIndex(colors, rgbList.get(i));
        if (temp == -1) {
            rgbList.remove(i);
        }
    }

    if (rgbList.size() >= 2) {
        for (int i = 0; i < rgbList.size() - 1; i++) {
            int index1 = findIndex(colors, rgbList.get(i));
            int index2 = findIndex(colors, rgbList.get(i + 1));
            if (Math.abs(index1 - index2) != 1) {
                return false;
            }
        }
    }

    return true;
}
    
    public static String upsideDown(String[] arr){
        ArrayList<String> rgbList = new ArrayList<>(Arrays.asList(arr));
        String[] warm = {"Red Violet", "Red", "Red Orange", "Orange", "Yellow Orange", "Yellow"};
        String[] cool = {"Violet", "Violet Blue", "Blue", "Blue Green", " Green", " Yellow Green"};
        for(int i = 0; i < rgbList.size(); i++){ // remove black and whites
            int temp = findIndex(warm, rgbList.get(i));
            int temp2 = findIndex(cool, rgbList.get(i));
            if(temp == -1 && temp2 == -1){
                rgbList.remove(i);
            }
        }
        if(findIndex(warm, rgbList.get(0)) != -1){
            for(int i = 0; i < rgbList.size(); i++){
                if(findIndex(warm,rgbList.get(i)) == -1){
                    return "failed";
                } 
            }
            return "passed";
        }
        for(int i = 0; i < rgbList.size(); i++){
            if(findIndex(cool,rgbList.get(i)) == -1){
                return "failed";
            }
        }
        return "passed";
    }

    public static int findIndex(String[] arr, String str){
        int i = 0;
        while(i < arr.length){
            if(arr[i].equals(str)){
                return i;
            }
            else{
                i++;
            }
        }
        return -1;
    }
}