import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/*
 *
 * message publisher of type Subject and three subscribers of type Observer.
 * Publisher will publish the message periodically to all subscribed or attached observers and
 * they will print the updated message to console.
 */
public class ObserverPatternTest {

    public static void main(String[] args) {
        //declare subscribers
        MessageSubscriberOne s1 = new MessageSubscriberOne();
        MessageSubscriberTwo s2 = new MessageSubscriberTwo();
        MessageSubscriberThree s3 = new MessageSubscriberThree();

        //register publisher  
        MessagePublisher p = new MessagePublisher();
        //attach subscribers to  publisher  
        p.attach(s1,s2);

        //publisher notifies to all subscribers
        p.notifyUpdate(new Message("Something interesting happened"));   //s1 and s2 will receive the update

        p.attach(s3);
        p.notifyUpdate(new Message("send notification to all subscibers"));

        p.detach(s1);
        p.attach(s2,s3);

        p.notifyUpdate(new Message("Second Message"));
    }

}

class Message {
    final String messageContent;

    public Message(String m) {
        this.messageContent = m;
    }

    public String getMessageContent() {
        return messageContent;
    }
}

interface Observer {
    public void update(Message m);

    public void logFromAnotherDevice();
}

interface Subject {
    public void attach(Observer... o);

    public void detach(Observer o);

    public void notifyUpdate(Message m);
}

class MessagePublisher implements Subject {

    // subscribers of type Observer
    private List<Observer> observers = new ArrayList<>();

    @Override
    public void attach(Observer... o) {
        for (Observer o_ : o){
            if(!observers .contains(o_)) {
                observers.add(o_);
            }else{
                observers.add(o_);
                o_.logFromAnotherDevice();
            }
        }
    }

    @Override
    public void detach(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyUpdate(Message m) {
        List<Observer> UniqueObservers = observers.stream().distinct().toList();
        for (Observer o : UniqueObservers) {
            o.update(m);
        }
    }
}
// different implementation of subscribers type of observer 
class MessageSubscriberOne implements Observer {
    @Override
    public void update(Message m) {
        System.out.println("MessageSubscriberOne :: " + m.getMessageContent());
    }

    @Override
    public void logFromAnotherDevice() {
        System.out.println("MessageSubscriberOne :: Log in from another device");
    }
}

class MessageSubscriberTwo implements Observer {
    @Override
    public void update(Message m) {
        System.out.println("MessageSubscriberTwo :: " + m.getMessageContent());
    }

    @Override
    public void logFromAnotherDevice() {
        System.out.println("MessageSubscriberTwo :: Log in from another device");
    }
}

class MessageSubscriberThree implements Observer {
    @Override
    public void update(Message m) {
        System.out.println("MessageSubscriberThree :: " + m.getMessageContent());
    }

    @Override
    public void logFromAnotherDevice() {
        System.out.println("MessageSubscriberThree :: Log in from another device");
    }
}