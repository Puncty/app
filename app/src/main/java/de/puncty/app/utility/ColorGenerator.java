package de.puncty.app.utility;

public class ColorGenerator {
    public static String[] generate(int length){

        String[] colors = new String[length];
        float relativePosition;
        int redNumber, greenNumber, blueNumber;
        String redString, greenString, blueString;

        for(int i = 0; i < length; i++){
            //calculate relative position in Array
            relativePosition = (float)i/(float)length;

            //calculate color values
            redNumber = (int)(-222 * Math.pow(relativePosition, 4) +222);
            redString = Integer.toHexString(redNumber);

            greenNumber = (int)(-200 * Math.pow(relativePosition -1,4) + 200);
            greenString = Integer.toHexString(greenNumber);

            blueNumber = 19;
            blueString = Integer.toHexString(blueNumber);

            //add them as Sting
            colors[i] = ((redString.length() < 2)?("0"+redString):(redString)) +
                    ((greenString.length() < 2)?("0"+greenString):(greenString)) +
                    ((blueString.length() < 2)?("0"+blueString):(blueString));
        }

        return colors;
    }
}
