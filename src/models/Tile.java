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
            System.out.println("Making HOME stretch for " + c);

            current.fork = new Tile(TType.ENDZONE_FIRST, current, null, c);
            current = current.fork;
            //make the endzone
            for(int i = 0; i < 4; i++){
                current.next = new Tile(TType.ENDZONE, current, null, c);
                current = current.next;
            }
            
            //add the HOME tile
            current.next = new Tile(TType.HOME, current, null, c);
        }
    }