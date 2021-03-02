package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeMap;

import static java.lang.Integer.parseInt;

public class BetterApproach {

    //Number of seats present in each row
    private static final int numberOfSeatsEachRow = 20;

    //Tags for each row
    private static final String[] rowTags = {"A", "B", "C", "D", "E", "F", "G", "I", "J", "K"};

    //Total Number of seats
    private static int totalSeats = rowTags.length * numberOfSeatsEachRow;

    //Hashmap to store rows based on empty seats
    private static HashMap<Integer, ArrayList<String>> seatMap = initiate();

    //Hashmap to store empty seat pointer map
    private static HashMap<String, Integer> emptySeatPointerMap = firstPointerEntry();



    public static void main(String[] args) throws FileNotFoundException {

        //readFile and store the reservation requests in an ordered treemap with Id as key and number of seats as value
        TreeMap<String, Integer> resRequest = readFile("./INPUT.txt");

        //TreeMap to store Reservation Ids and allotted seats
        TreeMap<String, String[]> reservations = new TreeMap<>();
        System.out.println(seatMap);
        System.out.println(emptySeatPointerMap);

        for(String requestId : resRequest.keySet()){
            int requestedSeats = resRequest.get(requestId);
            if(requestedSeats <= totalSeats && requestedSeats > 1){
                reservations.put(requestId, fillRequest(requestedSeats));
            }else{
                reservations.put(requestId, new String[0]);
            }
        }
    }

    /**
     * Method to read file from a given file path
     * Time Complexity O(n)
     * @param filePath
     * @return an ordered TreeMap containing request Id as key and no. of seats requested as value
     */
    private static TreeMap<String, Integer> readFile(String filePath){
        TreeMap<String, Integer> resRequest = new TreeMap<>();
        try{
            File file = new File(filePath);
            Scanner scan = new Scanner(file);
            while(scan.hasNextLine()) {
                String[] data = scan.nextLine().split("\\s+");
                resRequest.put(data[0], parseInt(data[1]));
            }
        } catch(FileNotFoundException e){
            System.out.println("File not found");
            e.printStackTrace();
            System.exit(0);
        }
        return resRequest;
    }

    /**
     * INITIATE:
     * Method to fill the seatMap with initial value of 20 seats each
     * @return
     */
    private static HashMap<Integer, ArrayList<String>> initiate(){
        HashMap<Integer, ArrayList<String>> seatMap = new HashMap<>();
        for(int i = 0; i < numberOfSeatsEachRow; i++){
            seatMap.put(i, new ArrayList<String>());
        }
        for(int i = 0; i < rowTags.length; i++)
            seatMap.get(rowTags.length - 1).add(rowTags[i]);
        return seatMap;
    }

    private static HashMap<String, Integer> firstPointerEntry(){
        HashMap<String, Integer> pointers = new HashMap<>();
        for(String row : rowTags){
            pointers.put(row, 0);
        }
        return pointers;
    }

    /**
     * Method to fill the requested seats in the theatre
     * @param requestedSeats
     * @return int[] array of alloted seat numbers
     */
    private static String[] fillRequest(int requestedSeats){
        String[] allottedSeats = new String[requestedSeats];
        while(requestedSeats != 0){
            int fillNumber = Integer.min(requestedSeats, numberOfSeatsEachRow);
            for(int i = 0; i < Integer.min(requestedSeats, numberOfSeatsEachRow); i++){

            }
        }
        return allottedSeats;
    }

}
