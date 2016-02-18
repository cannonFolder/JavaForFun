import java.util.*;
import java.util.regex.*;
import java.awt.*;
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

    cb.printBoard();
    String end ="";
    Country CountryFromlastMoveChessman=Country.BLACK;
    Pattern validInputPattern = Pattern.compile("\\d,\\d,\\d,\\d");
    
    outter:
    while(!(end.equals("exit")) || cb.isWin())
    {
      System.out.print("please follow fomat: fromX,toX,fromY,toY ");
      //restrict input type based on regular expression
      String input = sc.next();
      Matcher inputVerifier = validInputPattern.matcher(input);
      //type "exit" to end the game
      if(input.equals("exit"))
        end = "exit";

      else if(inputVerifier.find() == true)
      {
        String[] splitInput = input.split(","); //split user input based on ","
        int fromX = Integer.parseInt(splitInput[0]);
        int fromY = Integer.parseInt(splitInput[1]);
        int toX  = Integer.parseInt(splitInput[2]);
        int toY  = Integer.parseInt(splitInput[3]);

        Chessman chessman = cb.board.get(new Point(fromX, fromY));

        // check each side only move once chess at a time
        if(chessman.getCountry() != CountryFromlastMoveChessman)
        {
          System.out.println("from:["+fromX+","+fromY+"], to:["+toX+","+toY+"]");
          CountryFromlastMoveChessman=chessman.getCountry();
          cb.move(fromX, fromY, toX, toY);
          cb.printBoard();
        }
        else
        {
          System.out.println("one side only move once at a time");
          continue outter;
        }
      }
      else
      {
        System.out.println("invalid format");
        System.out.println("please follow: fromX,toX,fromY,toY");
      }
    }
    sc.close();
  }
}
