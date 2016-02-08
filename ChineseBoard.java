import java.util.*;
import java.awt.*;

public class ChineseBoard
{
  Map<Point, Chessman> board = new HashMap<Point, Chessman>();

  ChineseBoard()
  {
    init();
  }

  void init()
  {
    board.put(new Point(0,0), new Chessman(Country.RED, Unit.JV));
    board.put(new Point(1,0), new Chessman(Country.RED, Unit.MA));
    board.put(new Point(2,0), new Chessman(Country.RED, Unit.XIANG));
    board.put(new Point(3,0), new Chessman(Country.RED, Unit.SHI));
    board.put(new Point(4,0), new Chessman(Country.RED, Unit.JIANG)); //change back after adjusting 
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
    board.put(new Point(4,9), new Chessman(Country.BLACK, Unit.JIANG));
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

  /* check have chessman on (fromX, fromY)
     verify move based on chessmans' Unit 
     check have chessman on (toX, toY), if(have) verify country on (toX, toY) 
     put chessman to (toX, toY) remove chessman (fromX, fromY)
  */
  void move(int fromX, int fromY, int toX, int toY)
  {
    if(haveChess(fromX, fromY) && isMoveValid(fromX, fromY, toX, toY))
    {
      Chessman fromChess = board.get(new Point(fromX, fromY));
      Chessman toChess   = board.get(new Point(toX, toY));
      if(haveChess(toX, toY) && (fromChess.getCountry() == toChess.getCountry())) //can not eat chessman from same country
      {
        //board.put(new Point(toX, toY), fromChess); 
        //board.remove(new Point(fromX, fromY));
        System.out.println("illegel move");
      }
      else
      {
        //System.out.println("moved succesful");
        board.put(new Point(toX, toY), fromChess);
        board.remove(new Point(fromX, fromY));
      }
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
    System.out.println("invalid move");
    System.out.println(Math.abs(toX-fromX)+" "+Math.abs(toY-fromY));
    return false;
  }

  boolean isWin(int toX, int toY) //if target of the chess is JIANG, then win after move
  {
    Chessman chessman = board.get(new Point(toX, toY));
    return chessman.getUnit() == Unit.JIANG;
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
    for(int y=0; y<=9; y++)
    {
      for(int x=0; x<=8; x++)
      {
        if(haveChess(x, y))
        {
          Unit chessUnit = board.get(new Point(x, y)).getUnit();
          switch(chessUnit)
          {
            case JIANG : System.out.print("将");
                         break;
            case SHI :   System.out.print("士");
                         break;
            case XIANG : System.out.print("相");
                         break;
            case MA :    System.out.print("马");
                         break;
            case JV :    System.out.print("车");
                         break;
            case PAO :   System.out.print("炮");
                         break;
            case BING:   System.out.print("兵");
                         break;
          }
        }
        else
        {
          System.out.print("口");
        }
      }
      System.out.println();
    }
  }

  public static void main(String[] args)
  {
    ChineseBoard cb = new ChineseBoard();
    cb.move(0,3,0,4);
    cb.move(0,4,0,5);
    cb.move(0,5,1,5);
    cb.move(1,5,1,4);
    cb.move(0,6,0,5);
    cb.move(0,5,0,4);
    cb.move(0,4,1,4);
    cb.move(1,4,0,4);
    cb.printBoard();
    System.out.println(cb.isWin(4,9));
  } 
}
