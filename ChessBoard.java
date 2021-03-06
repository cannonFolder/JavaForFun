import java.util.*;
import java.awt.*;

public class ChessBoard 
{
  Map<Point, Chessman> board = new HashMap<Point, Chessman>();
  private final Chessman RED_JIANG = new Chessman(Country.RED, Unit.JIANG);
  private final Chessman BLACK_JIANG = new Chessman(Country.BLACK, Unit.JIANG);

  ChessBoard()
  {
    init();
  }

  void init()
  {
    board.put(new Point(0,0), new Chessman(Country.RED, Unit.JV));
    board.put(new Point(1,0), new Chessman(Country.RED, Unit.MA));
    board.put(new Point(2,0), new Chessman(Country.RED, Unit.XIANG));
    board.put(new Point(3,0), new Chessman(Country.RED, Unit.SHI));
    board.put(new Point(4,0), RED_JIANG);
    board.put(new Point(5,0), new Chessman(Country.RED, Unit.SHI));
    board.put(new Point(6,0), new Chessman(Country.RED, Unit.XIANG));
    board.put(new Point(7,0), new Chessman(Country.RED, Unit.MA));
    board.put(new Point(8,0), new Chessman(Country.RED, Unit.JV));

    board.put(new Point(1,2), new Chessman(Country.RED, Unit.PAO));
    board.put(new Point(7,2), new Chessman(Country.RED, Unit.PAO));
    board.put(new Point(0,3), new Chessman(Country.RED, Unit.BING));
    board.put(new Point(2,3), new Chessman(Country.RED, Unit.BING));
    board.put(new Point(4,3), new Chessman(Country.RED, Unit.BING));
    board.put(new Point(6,3), new Chessman(Country.RED, Unit.BING));
    board.put(new Point(8,3), new Chessman(Country.RED, Unit.BING));

    board.put(new Point(0,9), new Chessman(Country.BLACK, Unit.JV));
    board.put(new Point(1,9), new Chessman(Country.BLACK, Unit.MA));
    board.put(new Point(2,9), new Chessman(Country.BLACK, Unit.XIANG));
    board.put(new Point(3,9), new Chessman(Country.BLACK, Unit.SHI));
    board.put(new Point(4,9), BLACK_JIANG);
    board.put(new Point(5,9), new Chessman(Country.BLACK, Unit.SHI));
    board.put(new Point(6,9), new Chessman(Country.BLACK, Unit.XIANG));
    board.put(new Point(7,9), new Chessman(Country.BLACK, Unit.MA));
    board.put(new Point(8,9), new Chessman(Country.BLACK, Unit.JV));

    board.put(new Point(1,7), new Chessman(Country.BLACK, Unit.PAO));
    board.put(new Point(7,7), new Chessman(Country.BLACK, Unit.PAO));
    board.put(new Point(0,6), new Chessman(Country.BLACK, Unit.BING));
    board.put(new Point(2,6), new Chessman(Country.BLACK, Unit.BING));
    board.put(new Point(4,6), new Chessman(Country.BLACK, Unit.BING));
    board.put(new Point(6,6), new Chessman(Country.BLACK, Unit.BING));
    board.put(new Point(8,6), new Chessman(Country.BLACK, Unit.BING));
  }

  /* check position is into chess board
     check have chessman on (fromX, fromY)
     verify move based on chessmans' Unit 
     check have chessman on (toX, toY), if(have) verify country on (toX, toY) 
     put chessman to (toX, toY) remove chessman (fromX, fromY)
   */
  void move(int fromX, int fromY, int toX, int toY)
  {
    if(outOfBoard(fromX, fromY, toX, toY) && haveChess(fromX, fromY) && isMoveValid(fromX, fromY, toX, toY))
    {
      Chessman fromChess = board.get(new Point(fromX, fromY));
      Chessman toChess   = board.get(new Point(toX, toY));
      if(haveChess(toX, toY) && (fromChess.getCountry() == toChess.getCountry())) //can not eat chessman from same country
      {
        System.out.println("can not eat chessman from same country");
      }
      else
      {
        board.put(new Point(toX, toY), fromChess);
        board.remove(new Point(fromX, fromY));
      }
    }
    else 
    {
      System.out.println("move fail:");
      System.out.println("position out of bound: "+outOfBoard(fromX,fromY,toX,toY));
      System.out.println("have chess on start position: "+haveChess(fromX,fromY));
      System.out.println("follow movement rule: "+isMoveValid(fromX,fromY,toX,toY));
    } 
  }  

  boolean isMoveSucceed(int fromX, int fromY, int toX, int toY)
  {
    return (haveChess(fromX,fromY) == false && haveChess(toX,toY) == true);
  }

  //check move out of board
  boolean outOfBoard(int fromX,int fromY, int toX, int toY)
  {
    if((fromX  >= 0 && fromX  <= 8)&&
        (toX    >= 0 && toX    <= 8)&&
        (fromY >= 0 && fromY <= 9)  &&
        (toY   >= 0 && toY   <= 9))
      return true;
    else
    {
      System.out.println("input position is out of board");
      return false;
    }
  }

  //check chess exist on given position
  boolean haveChess(int x, int y)
  {
    Point pos = new Point(x,y);
    return board.containsKey(pos);
  }

  boolean isMoveValid(int fromX, int fromY, int toX, int toY)
   {
    //check fromPos != toPos
    if(fromX == toX && fromY == toY)
    {
      System.out.println("start position should not be the same as end position");
      return false;
    }
    Chessman chessman = board.get(new Point(fromX,fromY));
    //need to add a if statement to judge chessman block the jurney to (toX, toY)
    switch(chessman.getUnit())
    {
      case JIANG: if((chessman.getCountry() == Country.RED)&&
                      (toX>=3 && toX<=5)&& //constrain range
                      (toY>=0 && toY<=2)&&
                      (Math.abs(toY-fromY) == 1 || Math.abs(toX-fromX) == 1)) //constrain distance
                  {
                    //at centre points & edge points
                    if((fromX == 4 &&fromY == 1)&&
                        (fromX == 3 &&fromY == 0)&&
                        (fromX == 5 &&fromY == 0)&&
                        (fromX == 5 &&fromY == 2)&&
                        (fromX == 3 &&fromY == 2))
                    {
                      //System.out.println("enter loop");
                      return true;
                    }
                    //at remain points
                    else if((Math.abs(toY-fromY) == 1 && Math.abs(toX-fromX) != 1)||
                        (Math.abs(toY-fromY) != 1 && Math.abs(toX-fromX) == 1))
                      return true;
                  }
                  //verify if JAING from BLACK country
                  if((chessman.getCountry() == Country.BLACK)&&
                      (toX>=3 && toX<=5)&& 
                      (toY>=7 && toY<=9)&&
                      (Math.abs(toX-fromX) == 1 || Math.abs(toY-fromY) == 1))
                  {
                    if((fromX == 4 && fromY == 8)&&
                        (fromX == 3 && fromY == 9)&&
                        (fromX == 5 && fromY == 9)&&
                        (fromX == 5 && fromY == 7)&&
                        (fromX == 3 && fromY == 7))
                      return true;

                    else if((Math.abs(toY-fromY) == 1 && Math.abs(toX-fromX) != 1)||
                        (Math.abs(toY-fromY) != 1 && Math.abs(toX-fromX) == 1))
                      return true;
                  }
                  System.out.println((Math.abs(toX-fromX))+" "+ (Math.abs(toY-fromY)));
                  break;

      case SHI: if((chessman.getCountry() == Country.RED)&&
                    (toX>=3 && toX <=5)&& (toY>=0 && toY <=2)&&
                    (Math.abs(toY-fromY) == 1 && Math.abs(toX-fromX) == 1)) //only allow to move 1 distance in diagnoal distance
                  return true;

                if((chessman.getCountry() == Country.BLACK)&&
                    (toX>=3 && toX <=5)&&
                    (toY>=7 && toY <=9)&&
                    (Math.abs(toY-fromY) == 1 && Math.abs(toX-fromX) == 1))
                  return true;
                break;

      case XIANG: if((chessman.getCountry() == Country.RED)&&
                      (Math.abs(toY-fromY) == 2 && Math.abs(toX-fromX) == 2)&&
                      (toX>=0 && toX<=8)&&
                      (toY>=0 && toY<=4)&&
                      (haveChess(Math.max(fromX, toX)-1, Math.max(fromY,toY)-1) == false)) //bie xiang tui
                    return true;

                  if((chessman.getCountry() == Country.BLACK)&&
                      (Math.abs(toY-fromY) == 2 && Math.abs(toX-fromX) == 2)&&
                      (toX>=0 && toX<=8)&&
                      (toY>=5 && toY<=9)&&
                      (haveChess(Math.max(fromX, toX)-1, Math.max(fromY,toY)-1) == false)) //bie xiang tui
                  {
                    System.out.println((toX-1)+" "+(toY-1));
                    return true;
                  }
                  break;

      case MA: if(Math.abs(toY-fromY) == 2 && Math.abs(toX-fromX) == 1)
               {
                 //bie ma tui
                 if(fromY > toY && haveChess(fromX, toY+1) == false)
                   return true;
                 if(fromY < toY && haveChess(fromX, fromY+1) == false)
                   return true;
               }
               if (Math.abs(toY-fromY) == 1 && Math.abs(toX-fromX) == 2)
               {
                 if(fromX > toX && haveChess(toX+1,fromY) == false)
                   return true;
                 if(fromX < toX && haveChess(fromX+1, fromY) == false)
                   return true;
               }
               break;

      case JV: if((countChessForward(fromX, fromY, toX, toY) == 0)&&
                   (fromX == toX || fromY == toY)) 
                 return true;
               break;


               //move similar JV when not attacking
      case PAO:  if((countChessForward(fromX,fromY,toX,toY) == 0)&&
                     (fromX == toX || fromY == toY)&&
                     (haveChess(toX, toY) == false)) 
                   return true;
                 //when attacking, there must have one unit in the middle
               else if((countChessForward(fromX,fromY,toX,toY) == 1)&&
                   (fromX == toX || fromY == toY))
               {
                 System.out.println(countChessForward(fromX,fromY,toX,toY));
                 return true;
               }
               break;

      case BING: if((chessman.getCountry() == Country.RED)&&
                     (fromX == toX)&&
                     (fromY < toY)&& //BING never move backward
                     (toY-fromY == 1)) 
                   return true;

                 if((chessman.getCountry() == Country.BLACK)&&
                     (fromX == toX)&&
                     (fromY > toY)&&
                     (fromY-toY == 1))
                   return true;

                 //horizontal move only allowed when BING has acrossed the river
                 if(fromY == toY)
                 {
                   if((chessman.getCountry() == Country.RED)&&
                       (fromY > 4)&& //RED BING had acrossed the river
                       (Math.abs(fromX-toX) == 1))
                     return true;

                   if((chessman.getCountry() == Country.BLACK)&&
                       (fromY < 5)&& //BLACK BING had acrossed the river
                       (Math.abs(fromX-toX) == 1))
                     return true;
                 }
                 break;
    }
    return false;
  }

  //check unit JIANG is still in the board, based on Country movement
  boolean isWin()
  {
    if(board.containsValue(BLACK_JIANG) == false)
    {
      System.out.println("Red group win");
      return true;
    }

    if(board.containsValue(RED_JIANG) == false)
    {
      System.out.println("BLACK group win");
    }
    return false;
  }

  int countChessForward(int fromX, int fromY, int toX, int toY)
  {
    //count number of chess between start point to end point
    int numChessForward = 0; 
    if (fromX == toX) //move vertically
    {
      if (fromY > toY) //upward
      {
        for(int i=toY+1; i<fromY; i++)
        {
          if(haveChess(fromX, i) == true)
            numChessForward ++;
        }
      }

      if (fromY < toY) //downward
      {     
        for(int i=fromY+1; i<toY; i++)
        {
          if(haveChess(fromX, i) == true)
            numChessForward ++;
        }
      }
    }

    if (fromY == toY) //move horizontally
    {
      if (fromX < toX) //to right 
      {
        for (int i=fromX+1; i<toX; i++)
        {
          if (haveChess(i, fromY) == true)
            numChessForward ++;
        }
      }

      if (fromX > toX) //to left
      {
        for (int i=toX+1; i<fromX; i++)
        {
          if(haveChess(i, fromX) == true)
            numChessForward ++;
        }
      }
    }
    return numChessForward;
  }

  public void printBoard()
  {
    String redColor = "\u001B[31m";
    String greenColor = "\u001B[32m";
    String whiteColor = "\u001B[37m";
    String blueColor = "\u001B[34m";
    for(int y=0; y<=10; y++)
    {
      for(int x=0; x<=9; x++)
      {
        if(haveChess(x, y))
        {
          Chessman chessman = board.get(new Point(x, y));
          switch(chessman.getUnit())
          {
            case JIANG : if(chessman.getCountry() == Country.RED) System.out.print(redColor+"帅");
                           else System.out.print(greenColor+"将");
                           break;

            case SHI :   if(chessman.getCountry() == Country.RED) System.out.print(redColor+"仕");
                           else System.out.print(greenColor+"士");
                           break;

            case XIANG : if(chessman.getCountry() == Country.RED) System.out.print(redColor+"相");
                           else System.out.print(greenColor+"象");
                           break;

            case MA :    if(chessman.getCountry() == Country.RED) System.out.print(redColor+"马");
                           else System.out.print(greenColor+"馬");
                           break;

            case JV :    if(chessman.getCountry() == Country.RED) System.out.print(redColor+"車");
                           else System.out.print(greenColor+"车");
                           break;

            case PAO :   if(chessman.getCountry() == Country.RED) System.out.print(redColor+"炮");
                           else System.out.print(greenColor+"砲");
                           break;

            case BING:   if(chessman.getCountry() == Country.RED) System.out.print(redColor+"兵");
                           else System.out.print(greenColor+"卒");
                           break;
          }
        }
	//print bord position
	else if(x==9)
	{
	    System.out.print(blueColor+y);
	}
	else if(y==10)
	{
     	    System.out.print(blueColor+x+" ");
	}
	//print free chess board space 
        else
        {
          System.out.print(whiteColor+"口");
        }
      }
      System.out.println();
    }
  }

}
