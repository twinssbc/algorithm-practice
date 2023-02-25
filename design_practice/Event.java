public class Event {
    private String eventKey;
    private int count;
    public Event(String eventKey, int count) {
        this.eventKey = eventKey;
        this.count = count;
    }

    public String getEventKey() {
        return this.eventKey;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        Event that = (Event)o;
        return this.eventKey.equals(that.eventKey) && this.count == that.count;
    }
}
