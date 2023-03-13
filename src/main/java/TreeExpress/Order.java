package TreeExpress;

public class Order {
    private static int id_count;
    private int id;
    private int id_send;
    private int id_receiver;
    private double weight;
    private State state=State.Sending;

    public Order(int id_send, int id_receiver, double weight) {
        this.id=id_count++;
        this.id_send = id_send;
        this.id_receiver = id_receiver;
        this.weight = weight;
    }

    public int getId() {
        return id;
    }

    public int getId_send() {
        return id_send;
    }

    public void setId() {

    }

    public void setId_send(int id_send) {
        this.id_send = id_send;
    }

    public int getId_receiver() {
        return id_receiver;
    }

    public void setId_receiver(int id_receiver) {
        this.id_receiver = id_receiver;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", id_send=" + id_send +
                ", id_receiver=" + id_receiver +
                ", weight=" + weight +
                ", state=" + state +
                '}';
    }
}
