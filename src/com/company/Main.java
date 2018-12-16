package com.company;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        // init objects
        ExtendedAscii ascii = new ExtendedAscii();

        // read prameters
        String filename = args[0];
        int dictSize = Integer.parseInt(args[1]);
        String param = args[2];

        // compress or decompress
        String rename = filename;
        boolean compress;
        if (param.equals("-c")) {
            compress = true;
            rename = rename+".dlap";
        } else if (param.equals("-d")) {
            compress = false;
            if(filename.contains(".dlap"))
            rename = filename.replace(".dlap","");
        }


        // dictionary
        Map<String, Integer> dictionary = new HashMap<>();
        for (int i = 0; i < dictSize; i++) {
            dictionary.put("" + ascii.getAscii(i), i);
        }

        List<Integer> result = new ArrayList<>();
        String w = "";
        // read file byte by byte with decimal representation
        try (FileInputStream fileInputStream = new FileInputStream(filename); FileOutputStream out = new FileOutputStream(rename)) {
            int c;
            while ((c = fileInputStream.read()) != -1) {
                char ch = ascii.getAscii(c);
                String wc= w + ch;
                if (dictionary.containsKey(wc)) {
                    w = wc;
                }
                else {
                    dictionary.put(wc, dictSize++);
                    result.add(dictionary.get(w));
                    // Add wc to the dictionary.
                    w = "" + ch;
                }
            }
            // Output the code for w.
            if (!w.equals("")) {
                result.add(dictionary.get(w));
            }
            /*for(int i=0; i<result.size();i++) {
                System.out.println(result.get(i)+"-"+getKeyByValue(dictionary,result.get(i)));
            }*/
            for(int i=0; i<dictionary.size();i++){
                System.out.println(i+"-"+getKeyByValue(dictionary,i));
            }
        } catch (IOException ex) {
            System.out.println("Cannot read from file!"+ex);
        }
    }


    public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }
}
