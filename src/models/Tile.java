package models;

import java.awt.Color;

public class Tile{
        enum TType{
            HOME, EMPTY, ENDZONE, SLIDE_START, SLIDE_END;
        }

        Tile(TType t, Tile prev, Tile next, Color c){
            type = t;
            this.prev = prev;
            this.next = next;
            color = c;
        }

        Tile(TType t, Tile prev, Tile next){
            this(t, prev, next, Color.WHITE);
        }

        private TType type;
        private Tile prev;
        private Tile next;
        private Color color;        //for the endzone tiles 

    }