package com.pcg.roguelike.map;


import java.io.Serializable;

public class Cell implements Serializable {
    private int x, y;    
    private CellType type;
    private boolean passable;
    private char specifiedChar;
    private int discoverLevel;
    private boolean doorPassed;
    private int claimId;
    private boolean isTransparent;

    /**
     * @return the discovered
     */
    public boolean isDiscovered() {
        return this.discoverLevel != 0;
    }

    /**
     * @param discovered the discovered to set
     */
    public void setDiscoverLevel(int level) {
        this.discoverLevel = level;
    }

    /**
     * @return the doorPassed
     */
    public boolean isDoorPassed() {
        return doorPassed;
    }

    /**
     * @param doorPassed the doorPassed to set
     */
    public void setDoorPassed(boolean doorPassed) {
        this.doorPassed = doorPassed;
    }

    public int getDiscoverLevel() {
        return this.discoverLevel;
    }

    public int getClaimId() {
        return claimId;
    }

    public void setClaimId(int claimId) {
        this.claimId = claimId;
    }

    /**
     * @return the isTransparent
     */
    public boolean isTransparent() {
        return isTransparent;
    }

    /**
     * @param isTransparent the isTransparent to set
     */
    public void setTransparent(boolean isTransparent) {
        this.isTransparent = isTransparent;
    }

    public enum CellType {
        TYPE_FLOOR('.', true),
        TYPE_WALL('#', false),
        TYPE_WATER('~', true),        
        TYPE_CORRIDOR('.', true),
        TYPE_DOOR('/', true);

        CellType(char c, boolean passable) {
            this.c = c;
            this.passable = passable;
        }
        
        private char c;
        private boolean passable;
        
        private boolean isPassable() {
            return passable;
        }
        
        private char toChar() {
            return c;
        }
    }    
    
    public Cell(int x, int y, CellType type) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.passable = type.isPassable();
    }
    
    public Cell(int x, int y) {
        this(x, y, CellType.TYPE_FLOOR);
    }
    
    public Cell() {
        this(0, 0);
    }

    /**
     * @return the specifiedChar
     */
    public char getSpecifiedChar() {
        return specifiedChar;
    }

    /**
     * @param specifiedChar the specifiedChar to set
     */
    public void setSpecifiedChar(char specifiedChar) {
        this.specifiedChar = specifiedChar;
    }    
    
    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * @return the type
     */
    public CellType getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(CellType type) {
        this.type = type;
        this.passable = type.passable;
    }
        
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + this.x;
        hash = 47 * hash + this.y;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Cell other = (Cell) obj;
        if (this.x != other.x) {
            return false;
        }
        if (this.y != other.y) {
            return false;
        }
        return true;
    }    
    
    public char toChar() {
        return (this.getSpecifiedChar() == 0) ? this.type.toChar() : getSpecifiedChar();
    }
    
    @Override
    public String toString() {
        return String.valueOf(this.toChar());
    }
    
    /**
     * @return the passable
     */
    public boolean isPassable() {
        return passable;
    }

    /**
     * @param passable the passable to set
     */
    public void setPassable(boolean passable) {
        this.passable = passable;
    }    
}
