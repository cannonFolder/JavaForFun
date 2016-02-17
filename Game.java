import java.util.*;
import java.io.*;
class Game
{
  public static void main(String[] args)
  {
    /*check isWin == false
      chess from one country move once at a time
    */     
    Scanner sc = new Scanner(System.in);
    
    ChessBoard cb = new ChessBoard();
    //while(cb.isWin() == false)
    System.out.println("enter");
    String end ="";
    while(!(end.equals("exit")))
    {
      String input = sc.next();
      if(input.equals("exit"))
        end = "exit";
      else
      {
        String[] splitInput = input.split(","); //split user input based on ","
        int fromX = Integer.parseInt(splitInput[0]);
        int fromY = Integer.parseInt(splitInput[1]);
        int toX  = Integer.parseInt(splitInput[2]);
        int toY  = Integer.parseInt(splitInput[3]);
        System.out.println("fromX "+fromX);
        System.out.println("fromY "+fromY);
        System.out.println("toX "+toX);
        System.out.println("toY "+toY);
        //cb.move(fromX, formY, toX, toY);
      }
    }
  }
}
