package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static java.lang.Integer.parseInt;

public class Main {
    //Number of seats present in each row
    private static final int numberOfSeatsEachRow = 20;

    //Tags for each row
    private static final String[] rowTags = {"A", "B", "C", "D", "E", "F", "G", "I", "J", "K"};

    //Total Number of seats
    private static int totalSeats = rowTags.length * numberOfSeatsEachRow;

    // Setting initial data(maxNumberOfSeatsEachRow for each row)
    private static TreeMap<String, Integer> theatre = setInitialData();

    //Hashmap to keep track of the position of the empty seat in the
    private static HashMap<String, Integer> emptyRowPointer = setEmptyRowPointer();


    public static void main(String[] args) throws FileNotFoundException {

        //readFile and store the reservation requests in an ordered treemap with Id as key and number of seats as value
        Scanner scan = new Scanner(System.in);
        String filepath = scan.nextLine();
        TreeMap<String, Integer> resRequest = readFile(filepath);

        //TreeMap to store Reservation Ids and allotted seats
        TreeMap<String, ArrayList<String>> reservations = new TreeMap<>();

        //for each requestId in reservationRequest
        for(String requestId:resRequest.keySet()){

            int requestedSeats = resRequest.get(requestId);
            if(requestedSeats <= totalSeats && requestedSeats > 1){
                reservations.put(requestId, fillRequest(resRequest.get(requestId)));
            }else{
                reservations.put(requestId, new ArrayList<>());
            }
        }

        //Store generated reservations in to a text file and get its path
        String outputFilePath = writeToFile(reservations);
        System.out.println(outputFilePath);


    }

    /**
     * WRITETOFILE
     * method to output the reservations to a file and save it
     * @param reservations
     * @return String :file output path
     */
    private static String writeToFile(TreeMap<String, ArrayList<String>> reservations){
        try{
            FileWriter myWriter = new FileWriter("Output.txt");
            for(String res : reservations.keySet()) {
                ArrayList<String> arr = reservations.get(res);
                StringBuffer sb = new StringBuffer();
                for(int i =  0; i < arr.size(); i++)
                    if(i==0)
                        sb.append(arr.get(i));
                    else sb.append("," + arr.get(i));
                myWriter.write(res + " " + sb.toString() + "\n");
            }
            myWriter.close();
        } catch(IOException e){
            System.out.println("An IO error occured");
            e.printStackTrace();
        }
        return new File("Output.txt").getAbsolutePath().toString();
    }

    /**
     * Method to read file from a given file path
     * Time Complexity O(n)
     * @param filePath
     * @return TreeMap<> :an ordered TreeMap containing request Id as key and no. of seats requested as value
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
     * Method to set each row with numberOfSeatsEachRow, emptySeatPointer and row tag
     * @return TreeMap<> :an ordered TreeMap with row tag as key and array of size 2 having maxseats and emptyseatpointer
     */
    private static TreeMap<String, Integer> setInitialData(){
        TreeMap<String, Integer> theatre = new TreeMap<>();
        for(String row:rowTags){
            theatre.put(row, numberOfSeatsEachRow);
        }
        return theatre;
    }

    /**
     * SETEMPTYROWPOINTER
     * Method to set initial row pointers to 0 for each row
     * @return HashMap<String, Integer> :with initial seat pointer set to 0
     */
    private static HashMap<String, Integer> setEmptyRowPointer(){
        HashMap<String, Integer> emptySeatPointer = new HashMap<>();
        for(String row : rowTags)
            emptySeatPointer.put(row, 0);
        return emptySeatPointer;
    }

    /**
     * Method to fill the requested seats in the theatre
     * @param requestedSeats
     * @return int[] array of alloted seat numbers
     */
    private static ArrayList<String> fillRequest(int requestedSeats){
        //new arraylist object to store allotted seats for a request
        ArrayList<String> allottedSeats = new ArrayList<>();

        //While requested seats are not confirmed
        while(requestedSeats > 0){
            //Sort the theatre to get the maximum availability on top
            TreeMap<String, Integer> sortedTheatre = valueSort(theatre);
            //get the max available row
            String row = sortedTheatre.firstEntry().getKey();
            //get the number of available seats in that row
            int availableSeats = theatre.get(row);
            //get the position of the first empty seat in the row
            int emptySeatPointer = emptyRowPointer.get(row);
            //keep alloting seats till either available seats or requested seats fill up
            for(int i = 0; i < Integer.min(availableSeats, requestedSeats); i++){
                allottedSeats.add(row + (emptySeatPointer + 1));
                emptySeatPointer++;
            }
            //move the empty seat pointer offset to account for social distancing
            emptySeatPointer += 3;

            //update requestedSeats after this wave of allotments
            int oldRequestedSeats = requestedSeats;
            requestedSeats -= Integer.min(availableSeats, requestedSeats);

            //if all seats in the row are exhausted, update totalseats accordingly
            if(emptySeatPointer >= numberOfSeatsEachRow){
                totalSeats -= availableSeats;
                availableSeats = 0;
            }else {
                availableSeats = numberOfSeatsEachRow - emptySeatPointer;
                totalSeats = totalSeats -  (oldRequestedSeats + 3);
            }
            //Update theatre and empty seat pointer for next run
            theatre.put(row, availableSeats);
            emptyRowPointer.put(row, emptySeatPointer);
        }
        return allottedSeats;
    }


    /**
     * Sort theatre tree map based on available seats
     * @param map
     * @param <K>
     * @param <V>
     * @return TreeMap<K, V> : Sorted map based on available seats in decreasing order
     */
    public static <K, V extends Comparable<V> > TreeMap<K, V>
    valueSort(final Map<K, V> map)
    {
        /* Static Method with return type Map and extending comparator class which compares values
        associated with two keys return comparison results of values of two keys */
        Comparator<K> valueComparator = (k1, k2) -> {
            int comp = map.get(k2).compareTo(
                    map.get(k1));
            if (comp == 0)
                return 1;
            else
                return comp;
        };

        // SortedMap created using the comparator
        TreeMap<K, V> sorted = new TreeMap<K, V>(valueComparator);

        sorted.putAll(map);

        return sorted;
    }
}
