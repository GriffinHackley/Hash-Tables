import java.io.File;
import java.io.IOException;
import java.util.Scanner;

class Element{
    public int key;
    public int flag; //0: empty; 1: active; 2: deleted
}

class HashTable // open addressing
{
    private int m; // the size of the table
    Element[] T; //the hash table
    int hash(int x) // the hash function
    {
        return x % m;
    }
    int probe(int x, int i) // the i-th probe
    {
        return linearProbe(x, i);// we will use linear probe
    }
    int linearProbe(int x, int i)//the linear probe function
    {
        return (hash(x) + i) % m;
    }

    public HashTable(int size)// initialize the table
    {
        m = size;
        T = new Element[m];
        for(int i = 0; i < m; i++)
        {
            T[i] = new Element();
            T[i].flag = 0;
        }
    }
    public void printTable()//print the entire table
    {
        System.out.println("The following is the current hash table:");

        for (int i = 0; i < m; i++)
        {
            switch (T[i].flag)
            {
                case 0:
                    System.out.println("The entry " + i + " is empty.");
                    break;

                case 1:
                    System.out.println("The entry " + i + " is " + T[i].key);
                    break;

                case 2:
                    System.out.println("The entry " + i + " is " + T[i].key + ", but is deleted.");
                    break;
                default:
                    System.out.println("No such entry flag.");
            }
        }
    }

	//please complete the following three methods
	public void insert(int x)// insert a new element with key x
    {
        int index = hash(x);
        int i = 1;

        if(T[index].flag==0){
            T[index].key = x;
            T[index].flag = 1;
            return;
        }
        while(T[index].flag != 0){
            if(T[index].flag == 2){
                T[index].key = x;
                T[index].flag = 1;
                return;
            }
            index = probe(x,i);
            i++;

        }

        T[index].key = x;
        T[index].flag = 1;
    }
    public int search(int x) // search an element whose key is x and return the index, and if there is no such key, return -1
    {
        int index = hash(x);
        int i = 1;

        while(T[index].key != x){
            index = probe(x,i);
            i++;
            if(T[index].flag==0){
                return -1;
            }
        }
        if(T[index].flag == 2){
            return -1;
        }
        return index;

    }
    public void remove(int x)// remove an element whose key is x
    {
        int index = hash(x);
        int i = 1;

        while(T[index].key != x){
            index = probe(x,i);
            i++;


            if(T[index].flag==0){
                break;
            }
        }
        T[index].flag = 2;
    }
}

public class hw5_Q2 {
    public static void main(String[] args) throws IOException
    {
        String inputFile = "hw5_Q2_input.txt"; // input file

        //open the input file
        File myFile = new File(inputFile);
        Scanner input = new Scanner(myFile);

        //read the table size, which is the number in the first line of the input file
        Scanner nextLine = new Scanner(input.nextLine());
        int table_size = nextLine.nextInt();
        //int table_size = 41;

        //create a hash table of size equal to table_size
        HashTable table = new HashTable(table_size);

        //read operations from the input file
        String op;
        int x;
        while(input.hasNext())
        {
            nextLine = new Scanner(input.nextLine());
            op = nextLine.next();

            if (op.equals("insert"))
            {
                x = nextLine.nextInt(); // read the value x for insert
                table.insert(x);
            }
            if (op.equals("remove"))
            {
                x = nextLine.nextInt(); // read the value x for remove
                table.remove(x);
            }
            if (op.equals("search"))
            {
                x = nextLine.nextInt();
                int index = table.search(x);
                if (index != -1)
                    System.out.println("The key " + x + " is in T[" + index + "].");
                else// x is not in the hash table
                    System.out.println("The key " + x + " is not in the current hash table.");
            }
        }

        System.out.println();

        //print the table out on the console
        table.printTable();

        input.close();
    }
}
