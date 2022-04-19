package plming.message.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import plming.user.entity.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Message implements Comparable<Message> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // PK

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;

    @Column(length = 500)
    private String content;

    @Column(name = "sender_delete_yn",columnDefinition = "enum")
    private char senderDeleteYn = '0';

    @Column(name = "receiver_delete_yn",columnDefinition = "enum")
    private char receiverDeleteYn = '0';

    @Column(name = "create_date",columnDefinition = "datetime")
    private LocalDateTime createDate = LocalDateTime.now();

    @Builder
    public Message(User sender, User receiver, String content){
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
    }

    @Override
    public int compareTo(Message message) {
        return createDate.compareTo(message.createDate);
    }

    @Override
    public String toString(){
        return id.toString() + " " + getSender().getId().toString() + " " + getReceiver().getId().toString()
                + " " + getCreateDate().toString();
    }

    public void setSenderDeleted() {
        this.senderDeleteYn = '1';
    }

    public void setReceiverDeleted() {
        this.receiverDeleteYn = '1';
    }

    public boolean hasToBeDeleted(){
        return senderDeleteYn == '1' && receiverDeleteYn == '1';
    }
}
