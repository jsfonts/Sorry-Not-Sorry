package models;

import java.awt.Color;
import models.Pawn;
import java.io.Serializable;

public class Tile implements Serializable{

        private TType type;
        private TType firstFew; //to help ComputerPlayer
        private Tile prev;
        private Tile next;
        private Tile fork;       //fork at the endzone
        private Color color;        //for the endzone tiles 
        private Pawn pawn;
        private int [] coords;
        private static int count = 0;

        public enum TType{
            START, HOME, NORMAL, ENDZONE, ENDZONE_FIRST, SLIDE_START, SLIDE_END, FIRST, SECOND, THIRD;
        }

        Tile(TType t, Tile prev, Tile next, Color c){
            type = t;
            firstFew = TType.NORMAL;
            this.prev = prev;
            this.next = next;
            color = c;
            fork = null;
            coords = new int[2];
            count++;
            //System.out.println(count);
        }

        Tile(TType t, Tile prev, Tile next){
            this(t, prev, next, Color.WHITE);
        }

        public void setSecondType(TType t){
            firstFew = t;
        }

        public boolean empty(){
            return pawn == null;
        }

        public Pawn pawnAt(){
            return pawn;
        }

        public Tile next(){
            return next;
        }

        public Tile prev(){
            return prev;
        }

        public Tile fork(){
            return fork;
        }

        public Color getColor(){
            return color;
        }

        public void setCoords(int height, int width){
            coords[0] = height;
            coords[1] = width;
        }

        public int [] getCoords(){
            return coords;
        }

        public void removePawn(){
            pawn = null;
        }

        public TType getType(){
            return type;
        }

        public TType getSecondType(){
            return firstFew;
        }

        public void setPawnAt(Pawn pToAdd){
            //System.out.println("setPawnAt() function");
            //if(pawn == null)
                //System.out.println("Pawn that was at tile " + type + " is null.");
           // else
            //System.out.println("Pawn that was at tile " + type + " is " + pawn);

            pawn = pToAdd;

            //if(pawn == null)
             //   System.out.println("Pawn that is now at tile " + type + " is null.");
           // else
              //  System.out.println("Pawn is now at tile " + type + " is " + pawn);
        }

        public boolean isFirstThree(Color c){           //for ComputerPlayer
            if(firstFew != TType.NORMAL && c == color)
                return true;
            return false;
        }

        public void setNext(Tile t){
            this.next = t;
        }

        public static void makefork(Tile prev, Color c){
            Tile current = prev;

            if(c == Color.YELLOW){   //yellow 
                current.fork = new Tile(TType.ENDZONE_FIRST, current, null, c);
                current = current.fork;
                current.setCoords(14, 13);
                //make the endzone
                for(int i = 13; i >= 10; i--){
                    current.next = new Tile(TType.ENDZONE, current, null, c);
                    current = current.next;
                    current.setCoords(i, 13);
                }
                
                //add the HOME tile
                current.next = new Tile(TType.HOME, current, null, c);
                current = current.next();
                current.setCoords(9, 13);
            }
            else if(c == Color.GREEN){    //green
                current.fork = new Tile(TType.ENDZONE_FIRST, current, null, c);
                current = current.fork;
                current.setCoords(13, 1);
                //make the endzone
                for(int i = 2; i <= 5; i++){
                    current.next = new Tile(TType.ENDZONE, current, null, c);
                    current = current.next;
                    current.setCoords(13, i);
                }
                
                //add the HOME tile
                current.next = new Tile(TType.HOME, current, null, c);
                current = current.next();
                current.setCoords(13,6);
            }

            if(c == Color.RED){    //red
                current.fork = new Tile(TType.ENDZONE_FIRST, current, null, c);
                current = current.fork;
                current.setCoords(1, 2);
                //make the endzone
                for(int i = 2; i <= 5; i++){
                    current.next = new Tile(TType.ENDZONE, current, null, c);
                    current = current.next;
                    current.setCoords(i, 2);
                }
                
                //add the HOME tile
                current.next = new Tile(TType.HOME, current, null, c);
                current = current.next();
                current.setCoords(6, 2);
            }

            if(c == Color.BLUE){    //blue
                current.fork = new Tile(TType.ENDZONE_FIRST, current, null, c);
                current = current.fork;
                current.setCoords(2, 14);
                //make the endzone
                for(int i = 13; i >= 10; i--){
                    current.next = new Tile(TType.ENDZONE, current, null, c);
                    current = current.next;
                    current.setCoords(2, i);
                }
                
                //add the HOME tile
                current.next = new Tile(TType.HOME, current, null, c);
                current = current.next();
                current.setCoords(2, 9);
            }
        }
    }