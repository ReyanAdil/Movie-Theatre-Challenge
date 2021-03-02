# Movie Theatre Challenge

Overview
The goal is to Implement an algorithm for assigning seats within a movie theater to
fulfill reservation requests. Assume the movie theater has the seating
arrangement of 10 rows x 20 seats as image shown below.
Your homework assignment is to design and write a seat assignment
program to maximize both customer satisfaction and customer
safety. For the purpose of public safety, assume that a buffer of three
seats and/or one row is required.

[[ SCREEN ]]
--------------------
A ssssssssssssssssssss
B ssssssssssssssssssss
C ssssssssssssssssssss
D ssssssssssssssssssss
E ssssssssssssssssssss
F ssssssssssssssssssss
G ssssssssssssssssssss
H ssssssssssssssssssss
I ssssssssssssssssssss
J ssssssssssssssssssss
1 ... 20

***Files***
1. Main.java
2. Input.txt
3. Output.txt

***Input File Structure***
Input file is a txt file which will contain one line of input for each
reservation request. The order of the lines in the file reflects the order in
which the reservation requests were received. Each line in the file will be
comprised of a reservation identifier, followed by a space, and then the
number of seats requested. The reservation identifier will have the
format: R####.
Example Input File Rows:
R001 2
R002 4
R003 4
R004 3
...


***Output File Structure***
The program will output a file containing the seating assignments for
each request. Each row in the file will include the reservation number
followed by a space, and then a comma-delimited list of the assigned
seats. See the Example Output File Rows section for an example of
the output file content.
Example Output File Rows:
R001 I1,I2
R002 F16,F17,F18,F19
R003 A1,A2,A3,A4
R004 J4,J5,J6

## Instructions
1. Compile the Main.java file by using command *javac Main.java*
2. Run the generated Main file by using command *java Main*
3. Enter the location of the input file(Input file structure mentioned above) in the console and press ENTER/RETURN
3. The program generates an output file with comma seperated reserved seats and displays the path to the output file generated.

***Minimum Requirements***
Java SE 8

***Dependencies***
None

***API***
None

## Description
On a higher level the algorithm keeps the row with most available seats on top to be accessed when a reservation needs to be done.
Once the seats in the row are filled it moves the next row with most available seats on top to be accessed while updating the Total Number of available seats
By default it tries to assign all seats in a request together, but if thats not possible then it assigns wherever it finds free space in the next row.
An individual pointer for each row keeps a track of the next empty seat in the row in question.
The algorithm keeps a distance of three seats or one row between reservation requests.

## Issues
1. It fills up another row even if seats are available in the previous row. For example if we have 1 seat available in Row A and 20 available in Row B and the reservation request is for One seat, it will fill Row B because it has the most empty seats.
2. The seat allocation does not necessarily maximize the available space in the theatre.

## Better Approach
A better approach will be to rather map available seats to the rows which have them available.
So if I have a reservation request for lets say 5 seats we could just look for the available row corresponding to 5. If none are found then we could start moving down till 20 and see if we can seat them together. If we still have reservation requests left we could move up and seat them seperately.
