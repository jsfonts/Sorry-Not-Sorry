package models;

import java.awt.Color;
import models.Pawn;

public class Tile{

        private TType type;
        private TType firstFew; //to help ComputerPlayer
        private Tile prev;
        private Tile next;
        private Tile fork;       //fork at the endzone
        private Color color;        //for the endzone tiles 
        private Pawn pawnAt;
        private int [] coords;
        public static int count = 0;

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
            count++;
            coords = new int[2];
        }

        Tile(TType t, Tile prev, Tile next){
            this(t, prev, next, Color.WHITE);
        }

        public void setSecondType(TType t){
            firstFew = t;
        }

        public boolean empty(){
            return pawnAt == null;
        }

        public Pawn pawnAt(){
            return pawnAt;
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

        public void setPawnAt(Pawn p){
            pawnAt = p;
        }

        public void removePawn(){
            pawnAt = null;
        }

        public TType getType(){
            return type;
        }

        public TType getSecondType(){
            return firstFew;
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
            int height = prev.coords[0];
            System.out.println("Making HOME stretch for " + c);

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
                current.setCoords(9, 13);
            }

            if(c == Color.GREEN){    //green
                current.fork = new Tile(TType.ENDZONE_FIRST, current, null, c);
                current = current.fork;
                current.setCoords(13, 2);
                //make the endzone
                for(int i = 2; i <= 5; i++){
                    current.next = new Tile(TType.ENDZONE, current, null, c);
                    current = current.next;
                    current.setCoords(13, i);
                }
                
                //add the HOME tile
                current.next = new Tile(TType.HOME, current, null, c);
                current.setCoords(13,6);
            }

            if(height == 0){    //red
                current.fork = new Tile(TType.ENDZONE_FIRST, current, null, c);
                current = current.fork;
                current.setCoords(2, 2);
                //make the endzone
                for(int i = 2; i <= 5; i++){
                    current.next = new Tile(TType.ENDZONE, current, null, c);
                    current = current.next;
                    current.setCoords(i, 2);
                }
                
                //add the HOME tile
                current.next = new Tile(TType.HOME, current, null, c);
                current.setCoords(6, 2);
            }

            if(height == 2){    //blue
                current.fork = new Tile(TType.ENDZONE_FIRST, current, null, c);
                current = current.fork;
                current.setCoords(5, 14);
                //make the endzone
                for(int i = 13; i >= 10; i--){
                    current.next = new Tile(TType.ENDZONE, current, null, c);
                    current = current.next;
                    current.setCoords(3, i);
                }
                
                //add the HOME tile
                current.next = new Tile(TType.HOME, current, null, c);
                current.setCoords(3, 9);
            }
        }
    }