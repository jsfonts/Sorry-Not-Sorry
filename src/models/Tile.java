package models;

import java.awt.Color;
import models.Pawn;

public class Tile{

        private TType type;
        private Tile prev;
        private Tile next;
        private Tile fork;       //fork at the endzone
        private Color color;        //for the endzone tiles 
        private Pawn pawn;
        private int [] coords;
        public static int count = 0;

        enum TType{
            START, HOME, NORMAL, ENDZONE, ENDZONE_FIRST, SLIDE_START, SLIDE_END;
        }

        enum Location{
            SOUTH, NORTH, EAST, WEST, CORNER;
        }

        Tile(TType t, Tile prev, Tile next, Color c){
            type = t;
            this.prev = prev;
            this.next = next;
            color = c;
            fork = null;
            count++;
        }

        Tile(TType t, Tile prev, Tile next){
            this(t, prev, next, Color.WHITE);
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
            coords[0] = x;
            coords[1] = y;

        }

        public int [] getCoords(){
            return coords;
        }

        public boolean placePawn(Pawn p){
            if(pawn != null)
                return false;
            else
                pawn = p;

            return true;
        }

        public void removePawn(){
            pawn = null;
        }

        public TType getType(){
            return type;
        }

        public void setNext(Tile t){
            this.next = t;
        }

        public static void makefork(Tile prev, Color c){
            Tile current = prev;
            int height = prev.coords[0];
            System.out.println("Making HOME stretch for " + c);

            if(height == 15){   //yellow 
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

            if(height == 13){    //green
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
                current.setCoords(13,6);
            }

            if(height == 0){    //red
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
                current.setCoords(6, 2);
            }

            if(height == 2){    //blue
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
                current.setCoords(2, 9);
            }
        }
    }