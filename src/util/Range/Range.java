package util.Range;

public class Range {
   private int start;
   private int end;

    public Range(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public boolean isInRange(Range otherRange){
        return (otherRange.getStart() >= this.getStart() && otherRange.getStart() <= this.getEnd()) ||
                (otherRange.getEnd() >= this.getStart() && otherRange.getEnd() <= this.getEnd()) ||
                (otherRange.getStart() <= this.getStart() && otherRange.getEnd() >= this.getEnd());
    }

    public boolean isInRangeCollision(Range pacmanRange,boolean movingRight,boolean movingLeft,boolean movingUp, boolean movingDown){

            return (pacmanRange.getStart() >= this.getStart() && pacmanRange.getStart()  <= this.getEnd()) ||
                    (pacmanRange.getEnd()  >= this.getStart() && pacmanRange.getEnd()  <= this.getEnd()) ||
                    (pacmanRange.getStart()  <= this.getStart() && pacmanRange.getEnd()  >= this.getEnd());

    }

   public boolean isInRangePoint(Range pacmanRange,boolean movingRight,boolean movingLeft,boolean movingUp, boolean movingDown) {
       int pacmanLength = pacmanRange.getEnd() - pacmanRange.getStart();
       int pacmanStart = pacmanRange.getStart();
       int pacmanEnd = pacmanRange.getEnd();
       int overlapStart = Math.max(this.getStart(), pacmanStart);
       int overlapEnd = Math.min(this.getEnd(), pacmanEnd);
       int overlapLength = overlapEnd - overlapStart;

           return overlapLength >= pacmanLength;


   }
}
