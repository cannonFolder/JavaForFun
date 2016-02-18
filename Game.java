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
    Country CountryFromLastMoveChessman=Country.BLACK;
    Pattern validInputPattern = Pattern.compile("\\d,\\d,\\d,\\d");
    
    while(!(end.equals("exit")) || cb.isWin())
    {
      System.out.print("fromX,toX,fromY,toY==> ");
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
        
        try //catch NullPointerException for [fromX,fromY]
        {
          Chessman chessman = cb.board.get(new Point(fromX, fromY));

          // check each side only move once chess at a time
          if(chessman.getCountry() != CountryFromLastMoveChessman)
          {
            System.out.println("from:["+fromX+","+fromY+"], to:["+toX+","+toY+"]");
            try
            {
              cb.move(fromX, fromY, toX, toY);
            }
            catch(NullPointerException e)
            {
              System.out.println("there is no chess at [fromX,fromY]");
            }

            //change side to play iff move is successful
            if(cb.isMoveSucceed(fromX,fromY,toX,toY) == true)
            {
              CountryFromLastMoveChessman=chessman.getCountry();
              cb.printBoard();
            }
          }
          else
          {
            System.out.println("one side only move once at a time");
            continue;
          }
        }
        catch(NullPointerException e) 
        {
          System.out.println("no chess at [fromX,fromY]");
          continue;
        }
      }
      else
      {
        System.out.println("invalid format");
      }
    }
    sc.close();
  }
}
